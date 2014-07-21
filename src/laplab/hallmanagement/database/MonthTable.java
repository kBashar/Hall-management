package laplab.hallmanagement.database;

import laplab.lib.tablecreator.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/3/14
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonthTable {
    public static String TABLE_NAME = DataBaseConstant.MONTH_TABLE;

    public static String MONTH_ID = "ID";
    public static String MONTH = "month";
    public static String YEAR = "year";


    private Connection connection = null;

    MonthTable(Connection connection) {
        this.connection = connection;
    }

    private String createTableName() {
        return MonthTable.TABLE_NAME;
    }

    private ColumnList createColumns() {
        ColumnList columns = new ColumnList();

        columns.add(new Column(MonthTable.YEAR, Column.COLUMN_TYPE_INTEGER));
        columns.add(new Column(MonthTable.MONTH, Column.COLUMN_TYPE_INTEGER));
        columns.add(new Column(MonthTable.MONTH_ID, Column.COLUMN_TYPE_INTEGER));
        return columns;
    }

    private PrimaryKey createPrimaryKey() {
        return new PrimaryKey(MonthTable.MONTH_ID);
    }


    public int create() {
        String createStatementQuery = new TableStatementCreator(createTableName(),
                createColumns(),
                createPrimaryKey()).getStatement();
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(createStatementQuery);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return -1;
        }
    }
}
