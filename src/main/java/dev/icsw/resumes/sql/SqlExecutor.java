package dev.icsw.resumes.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor<T> {
    T executeSql(PreparedStatement sqlStatement) throws SQLException;
}
