package dev.icsw.resumes.storage;

import dev.icsw.resumes.exception.ItemNotPresentException;
import dev.icsw.resumes.model.AbstractSection;
import dev.icsw.resumes.model.ContactType;
import dev.icsw.resumes.model.Resume;
import dev.icsw.resumes.model.SectionType;
import dev.icsw.resumes.sql.SqlHelper;
import dev.icsw.resumes.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return sqlHelper.executeSql("SELECT count(*) FROM resume", st -> {
            ResultSet queryResult = st.executeQuery();
            return queryResult.next() ? queryResult.getInt(1) : 0;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeTransaction(dbConn -> {
            Resume resume;
            try (PreparedStatement preparedSql = dbConn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                preparedSql.setString(1, uuid);
                ResultSet queryResult = preparedSql.executeQuery();
                if (!queryResult.next()) {
                    throw new ItemNotPresentException(uuid);
                }
                resume = new Resume(uuid, queryResult.getString("full_name"));
            }

            try (PreparedStatement preparedSql = dbConn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                preparedSql.setString(1, uuid);
                ResultSet queryResult = preparedSql.executeQuery();
                while (queryResult.next()) {
                    addContact(queryResult, resume);
                }
            }

            try (PreparedStatement preparedSql = dbConn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                preparedSql.setString(1, uuid);
                ResultSet queryResult = preparedSql.executeQuery();
                while (queryResult.next()) {
                    addSection(queryResult, resume);
                }
            }

            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeTransaction(dbConn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();

            try (PreparedStatement preparedSql = dbConn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet queryResult = preparedSql.executeQuery();
                while (queryResult.next()) {
                    String uuid = queryResult.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, queryResult.getString("full_name")));
                }
            }

            try (PreparedStatement preparedSql = dbConn.prepareStatement("SELECT * FROM contact")) {
                ResultSet queryResult = preparedSql.executeQuery();
                while (queryResult.next()) {
                    Resume resume = resumes.get(queryResult.getString("resume_uuid"));
                    addContact(queryResult, resume);
                }
            }

            try (PreparedStatement preparedSql = dbConn.prepareStatement("SELECT * FROM section")) {
                ResultSet queryResult = preparedSql.executeQuery();
                while (queryResult.next()) {
                    Resume resume = resumes.get(queryResult.getString("resume_uuid"));
                    addSection(queryResult, resume);
                }
            }

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executeTransaction(dbConn -> {
                    try (PreparedStatement preparedSql = dbConn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        preparedSql.setString(1, resume.getUuid());
                        preparedSql.setString(2, resume.getFullName());
                        preparedSql.execute();
                    }
                    insertContacts(dbConn, resume);
                    insertSections(dbConn, resume);
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeTransaction(dbConn -> {
            try (PreparedStatement preparedSql = dbConn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedSql.setString(1, resume.getFullName());
                preparedSql.setString(2, resume.getUuid());
                if (preparedSql.executeUpdate() != 1) {
                    throw new ItemNotPresentException(resume.getUuid());
                }
            }
            deleteContacts(dbConn, resume);
            deleteSections(dbConn, resume);
            insertContacts(dbConn, resume);
            insertSections(dbConn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeSql("DELETE FROM resume WHERE uuid=?", preparedSql -> {
            preparedSql.setString(1, uuid);
            if (preparedSql.executeUpdate() == 0) {
                throw new ItemNotPresentException(uuid);
            }
            return null;
        });
    }

    @Override
    public void clear() {
        sqlHelper.executeSql("DELETE FROM resume");
    }

    private void insertContacts(Connection dbConn, Resume resume) throws SQLException {
        try (PreparedStatement preparedSql = dbConn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                preparedSql.setString(1, resume.getUuid());
                preparedSql.setString(2, e.getKey().name());
                preparedSql.setString(3, e.getValue());
                preparedSql.addBatch();
            }
            preparedSql.executeBatch();
        }
    }

    private void insertSections(Connection dbConn, Resume resume) throws SQLException {
        try (PreparedStatement preparedSql = dbConn.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                preparedSql.setString(1, resume.getUuid());
                preparedSql.setString(2, e.getKey().name());
                AbstractSection section = e.getValue();
                preparedSql.setString(3, JsonParser.write(section, AbstractSection.class));
                preparedSql.addBatch();
            }
            preparedSql.executeBatch();
        }
    }

    private void deleteContacts(Connection dbConn, Resume resume) throws SQLException {
        deleteAttributes(dbConn, resume, "DELETE  FROM contact WHERE resume_uuid=?");
    }

    private void deleteSections(Connection dbConn, Resume resume) throws SQLException {
        deleteAttributes(dbConn, resume, "DELETE  FROM section WHERE resume_uuid=?");
    }

    private void deleteAttributes(Connection dbConn, Resume resume, String sqlText) throws SQLException {
        try (PreparedStatement preparedSql = dbConn.prepareStatement(sqlText)) {
            preparedSql.setString(1, resume.getUuid());
            preparedSql.execute();
        }
    }

    private void addContact(ResultSet queryResult, Resume resume) throws SQLException {
        String value = queryResult.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(queryResult.getString("type")), value);
        }
    }

    private void addSection(ResultSet queryResult, Resume resume) throws SQLException {
        String content = queryResult.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(queryResult.getString("type"));
            resume.addSection(type, JsonParser.read(content, AbstractSection.class));
        }
    }
}