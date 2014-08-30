package laplab.hallmanagement.database;

import sample.MakeLogger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by khyrul bashar on 2/26/14.
 */
public class DataBaseMetaData {

    Connection connection;
    DatabaseMetaData metaData;

    public DataBaseMetaData(Connection conn) {
        connection = conn;

        try {
            metaData = connection.getMetaData();
        } catch (SQLException e) {
            MakeLogger.printToLogger(getClass().toString(), e.toString());  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public boolean isThisTableExists(String tableName) {
        boolean exists = false;
        try {
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            exists = resultSet.next();
        } catch (SQLException e) {
            MakeLogger.printToLogger(getClass().toString(),e.toString());  //To change body of catch statement use File | Settings | File Templates.

        }
        return exists;
    }

    public boolean areTablesCreated() {
        String tableName = DataBaseConstant.STUDENT_INFO_TABLE_NAME;
        boolean exists = false;
        try {
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            exists = resultSet.next();
        } catch (SQLException e) {
            MakeLogger.printToLogger(getClass().toString(),e.toString());  //To change body of catch statement use File | Settings | File Templates.

        }
        return exists;
    }
}
