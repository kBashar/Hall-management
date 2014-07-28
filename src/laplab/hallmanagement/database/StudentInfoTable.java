package laplab.hallmanagement.database;

import laplab.lib.tablecreator.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: kBashar
 * Date: 2/26/14
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentInfoTable {

    private static final String TABLE_NAME = DataBaseConstant.STUDENT_INFO_TABLE_NAME;

    public static final String ID_COLUMN = "ID";
    public static final String NAME_COLUMN = "NAME";
    public static final String ROOM_COLUMN = "ROOM";
    public static final String DEPARTMENT_COLUMN = "DEPARTMENT";
    public static final String BATCH_COLUMN = "BATCH";
    public static final String CONTACT_COLUMN = "CONTACT";
    public static final String PARENT_NAME_COLUMN = "PARENT_NAME";
    public static final String PARENT_CONTACT_COLUMN = "PARENT_CONTACT";
    public static final String BLOOD_GROUP_COLUMN = "BLOOD_GROUP";

    Connection connection;

    StudentInfoTable(Connection connection) {
        this.connection = connection;
    }

    private ColumnList createColumns() {
        ColumnList columns = new ColumnList();

        columns.add(new Column(StudentInfoTable.ID_COLUMN, Column.COLUMN_TYPE_INTEGER, false));
        columns.add(new Column(StudentInfoTable.NAME_COLUMN, Column.setStringType(50), false));
        columns.add(new Column(StudentInfoTable.ROOM_COLUMN, Column.COLUMN_TYPE_SMALL_INTEGER, false));
        columns.add(new Column(StudentInfoTable.DEPARTMENT_COLUMN, Column.COLUMN_TYPE_INTEGER, false));
        columns.add(new Column(StudentInfoTable.BATCH_COLUMN, Column.COLUMN_TYPE_INTEGER, false));
        columns.add(new Column(StudentInfoTable.CONTACT_COLUMN, Column.setStringType(15)));
        columns.add(new Column(StudentInfoTable.PARENT_NAME_COLUMN, Column.setStringType(50)));
        columns.add(new Column(StudentInfoTable.PARENT_CONTACT_COLUMN, Column.setStringType(15)));
        columns.add(new Column(StudentInfoTable.BLOOD_GROUP_COLUMN, Column.setStringType(5)));

        return columns;
    }

    private ForeignKeyList createForeignKey() {
        ForeignKeyList foreignKeyList = new ForeignKeyList();
        foreignKeyList.add(new ForeignKey(
                StudentInfoTable.DEPARTMENT_COLUMN,
                DataBaseConstant.DEPARTMENT_TABLE_NAME,
                DepartmentTable.DEPARTMENT_ID_COLUMN
        ));
        foreignKeyList.add(new ForeignKey(
                StudentInfoTable.BATCH_COLUMN,
                DataBaseConstant.BATCH_TABLE_NAME,
                BatchTable.BATCH_ID_COLUMN
        ));
        return foreignKeyList;
    }

    private PrimaryKey createPrimaryKey() {
        return new PrimaryKey(StudentInfoTable.ID_COLUMN);
    }

    public int create() {
        String statementQuery = new TableStatementCreator(StudentInfoTable.TABLE_NAME,
                createColumns(),
                createPrimaryKey(),
                createForeignKey()).getStatement();


        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(statementQuery);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return -1;
        }
    }

}
