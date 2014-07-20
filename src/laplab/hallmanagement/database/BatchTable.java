package laplab.hallmanagement.database;

import laplab.lib.tablecreator.Column;
import laplab.lib.tablecreator.ColumnList;
import laplab.lib.tablecreator.PrimaryKey;
import laplab.lib.tablecreator.TableStatementCreator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/2/14
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchTable {
    private static final String TABLE_NAME = DataBaseConstant.BATCH_TABLE_NAME;

    public static final String BATCH_ID_COLUMN = "ID";
    public static final String BATCH_NAME_COLUMN = "BATCH_NAME";
    public static final String DEPRECATED_BATCH_COLUMN = "BATCH_DEPRECATED";

    public static final String FINE_FOR_BATCH = "FINE";
    public static final String MIN_DAY_FOR_CREDIT = "MINDAY";
    public static final String MAX_DAY_FOR_CREDIT = "MAXDAY";

    Connection connection;

    BatchTable(Connection connection) {
        this.connection = connection;
    }

    private ColumnList createColumns() {
        ColumnList columns = new ColumnList();

        columns.add(new Column(BatchTable.BATCH_ID_COLUMN, Column.COLUMN_TYPE_INTEGER, false));
        columns.add(new Column(BatchTable.BATCH_NAME_COLUMN, Column.setStringType(50), false));
        columns.add(new Column(BatchTable.DEPRECATED_BATCH_COLUMN, Column.COLUMN_TYPE_BOOLEAN, false));
        columns.add(new Column(BatchTable.FINE_FOR_BATCH, Column.COLUMN_TYPE_INTEGER, false));
        columns.add(new Column(BatchTable.MIN_DAY_FOR_CREDIT, Column.COLUMN_TYPE_INTEGER, false));
        columns.add(new Column(BatchTable.MAX_DAY_FOR_CREDIT, Column.COLUMN_TYPE_INTEGER, false));
        return columns;
    }


    public int create() {
        String statementQuery = new TableStatementCreator(BatchTable.TABLE_NAME,
                createColumns(),
                new PrimaryKey(BatchTable.BATCH_ID_COLUMN)).getStatement();

        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(statementQuery);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return -1;
        }
    }

}
