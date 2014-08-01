package laplab.hallmanagement.database;


import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by kBashar on 2/24/14.
 */
public class DataBaseConnection {

    Connection connection;

    public DataBaseConnection() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:file:database/Hall");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (connection == null) {
            System.out.println("Database not Created");
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static Connection getConnection() {
        try {
            // connection = DriverManager.getConnection("jdbc:hsqldb:file:database/Hall");
            return DriverManager.getConnection("jdbc:hsqldb:file:database/Hall");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ;
        return null;
    }

    public boolean checkForDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:file:database/Hall");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        DataBaseMetaData metaData = new DataBaseMetaData(connection);

        if (metaData.isThisTableExists(MonthTable.TABLE_NAME)) {
            System.out.println(MonthTable.TABLE_NAME + " EXISTS");
        } else {
            if (new MonthTable(connection).create() > -1) {
                System.out.println("Month Table Created");
            }
        }
        if (metaData.isThisTableExists(DataBaseConstant.BATCH_TABLE_NAME)) {
            System.out.println(DataBaseConstant.BATCH_TABLE_NAME + " EXISTS");
        } else {
            if (new BatchTable(connection).create() > -1) {
                System.out.println("Batch Table Created");
            }
        }
        if (metaData.isThisTableExists(DataBaseConstant.DEPARTMENT_TABLE_NAME)) {
            System.out.println(DataBaseConstant.DEPARTMENT_TABLE_NAME + " EXISTS");
        } else {
            if (new DepartmentTable(connection).create() > -1) {
                System.out.println("Department Table Created");
            }
        }
        if (metaData.isThisTableExists(DataBaseConstant.STUDENT_INFO_TABLE_NAME)) {
            System.out.println(DataBaseConstant.STUDENT_INFO_TABLE_NAME + " EXISTS");
        } else {
            if (new StudentInfoTable(connection).create() > -1) {
                System.out.println("Student info Table Created");
            }
        }
        if (metaData.isThisTableExists(DataBaseConstant.DINING_INFO_TABLE_NAME)) {
            System.out.println(DataBaseConstant.DINING_INFO_TABLE_NAME + " EXISTS");
        } else {
            if (new DiningTable(connection).create() > -1) {
                System.out.println("Dinning info Table Created");
            }
        }
        if (metaData.isThisTableExists(DataBaseConstant.FINE_TABLE_NAME)) {
            System.out.println(DataBaseConstant.FINE_TABLE_NAME + " EXISTS");
        } else {
            if (new FineTable(connection).create() > -1) {
                System.out.println("FINE info Table Created");
            }
        }
        return true;
    }
}
