package dev.icsw.resumes.sql;

import dev.icsw.resumes.exception.StorageException;
import dev.icsw.resumes.util.UtilExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executeSql(String sqlText) {
        this.executeSql(sqlText, PreparedStatement::execute);
    }

    public <T> T executeSql(String sqlText, SqlExecutor<T> sqlExecutor) {
        try (Connection dbConn = connectionFactory.getConnection();
             PreparedStatement preparedSqlStatement = dbConn.prepareStatement(sqlText)) {
            return sqlExecutor.executeSql(preparedSqlStatement);
        } catch (SQLException e) {
            throw UtilExceptions.getStorageException(e);
        }
    }

    public <T> T executeTransaction(SqlTransaction<T> transactionExecutor) {
        try (Connection dbConn = connectionFactory.getConnection()) {
            try {
                dbConn.setAutoCommit(false);
                T res = transactionExecutor.executeTransaction(dbConn);
                dbConn.commit();
                return res;
            } catch (SQLException e) {
                dbConn.rollback();
                throw UtilExceptions.getStorageException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
