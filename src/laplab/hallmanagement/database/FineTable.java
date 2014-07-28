package laplab.hallmanagement.database;

import laplab.lib.tablecreator.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: khyrul Bashar
 * Date: 7/2/14
 * Time: 1:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class FineTable {
    private static final String TABLE_NAME = DataBaseConstant.FINE_TABLE_NAME;

    public static final String MONTH_ID = "month_id";
    public static final String BATCH = "batch";
    public static final String DEPARTMENT = "dept";
    public static final String IS_FREE = "isFree";


    private Connection connection = null;

    FineTable(Connection connection) {
        this.connection = connection;
    }

    private String createTableName() {
        return FineTable.TABLE_NAME;
    }

    private ColumnList createColumns() {
        ColumnList columns = new ColumnList();

        columns.add(new Column(FineTable.MONTH_ID, Column.COLUMN_TYPE_INTEGER));
        columns.add(new Column(FineTable.BATCH, Column.COLUMN_TYPE_INTEGER));
        columns.add(new Column(FineTable.DEPARTMENT, Column.COLUMN_TYPE_INTEGER));
        columns.add(new Column(FineTable.IS_FREE, Column.COLUMN_TYPE_INTEGER, "-1"));
        return columns;
    }

    private PrimaryKey createPrimaryKey() {
        return new PrimaryKey(FineTable.MONTH_ID + "," +
                FineTable.BATCH + "," +
                FineTable.DEPARTMENT);
    }

    private ForeignKeyList createForeignKeys() {
        ForeignKeyList foreignKeyList = new ForeignKeyList();

        foreignKeyList.add(new ForeignKey(BATCH,
                DataBaseConstant.BATCH_TABLE_NAME,
                BatchTable.BATCH_ID_COLUMN));
        foreignKeyList.add(new ForeignKey(DEPARTMENT,
                DataBaseConstant.DEPARTMENT_TABLE_NAME,
                DepartmentTable.DEPARTMENT_ID_COLUMN));
        foreignKeyList.add(new ForeignKey(MONTH_ID,
                DataBaseConstant.MONTH_TABLE,
                MonthTable.MONTH_ID));

        return foreignKeyList;
    }

    public int create() {
        String createStatementQuery = new TableStatementCreator(createTableName(),
                createColumns(),
                createPrimaryKey(),
                createForeignKeys()).getStatement();
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(createStatementQuery);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return -1;
        }
    }

}
