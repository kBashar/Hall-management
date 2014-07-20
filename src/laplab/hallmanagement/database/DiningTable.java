package laplab.hallmanagement.database;

import laplab.lib.tablecreator.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class DiningTable {
    private static final String TABLE_NAME = DataBaseConstant.DINING_INFO_TABLE_NAME;

    public static final String DINING_STUDENT_ID = "STUDENT_ID";
    public static final String MONTH_ID="month_id";
    public static final String AMOUNT="amount";
    public static final String CREDIT_DAY="credit_day";
    public static final String VOUCHER_ID="voucher_ID";


    private Connection connection = null;

    DiningTable(Connection connection) {
        this.connection = connection;
    }

    private String createTableName() {
        return DiningTable.TABLE_NAME;
    }

    private ColumnList createColumns() {
        ColumnList columns = new ColumnList();

        columns.add(new Column(DiningTable.DINING_STUDENT_ID, Column.COLUMN_TYPE_INTEGER));
        columns.add(new Column(DiningTable.MONTH_ID, Column.COLUMN_TYPE_INTEGER));
        columns.add(new Column(DiningTable.AMOUNT, Column.COLUMN_TYPE_INTEGER));
        columns.add(new Column(DiningTable.CREDIT_DAY, Column.COLUMN_TYPE_INTEGER,"0"));
        columns.add(new Column(DiningTable.VOUCHER_ID,Column.setStringType(10)));
        return columns;
    }

    private PrimaryKey createPrimaryKey() {
        return new PrimaryKey(DiningTable.DINING_STUDENT_ID+","+
                DiningTable.MONTH_ID);
    }

    private ForeignKeyList createForeignKeys() {
        ForeignKeyList foreignKeyList = new ForeignKeyList();

        foreignKeyList.add(new ForeignKey(DINING_STUDENT_ID,
                DataBaseConstant.STUDENT_INFO_TABLE_NAME,
                StudentInfoTable.ID_COLUMN));
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
