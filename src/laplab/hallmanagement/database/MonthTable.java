package laplab.hallmanagement.database;

import laplab.hallmanagement.Month;
import laplab.lib.databasehelper.DataBaseHelper;
import laplab.lib.databasehelper.QueryHelper;
import laplab.lib.tablecreator.*;
import sample.MakeLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: khyrul Bashar
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
            MakeLogger.printToLogger(getClass().toString(), e.toString());  //To change body of catch statement use File | Settings | File Templates.
            return -1;
        }
    }

    public static void updateNewMonth()  {

        int id = (Month.getCurrentYear()*100)+Month.getCurrentMonth();
        String query = "select count(ID) as monthcount from Month where ID = "+String.valueOf(id);
        System.out.println(query);

        QueryHelper queryHelper = new QueryHelper(DataBaseConnection.getConnection());
        ResultSet resultSet = queryHelper.queryInDataBase(query);
        int count = -1;
        try {
            while (resultSet.next())    {
                count = resultSet.getInt("monthcount");
            }
        } catch (SQLException e) {
            MakeLogger.printToLogger(MonthTable.class.toString());  //To change body of catch statement use File | Settings | File Templates.
        }
        if (count == 0)  {
            HashMap map = new HashMap();
            map.put("year",Month.getCurrentYear());
            map.put("month",Month.getCurrentMonth());
            map.put("ID",id);

            new DataBaseHelper(DataBaseConnection.getConnection()).insertIntoDataBase(
                    DataBaseConstant.MONTH_TABLE,
                    map
            );
        }
    }
}
