package laplab.lib.tablecreator;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class Column {
    public static final String COLUMN_TYPE_INTEGER = "INTEGER";
    public static final String COLUMN_TYPE_SMALL_INTEGER = "SMALLINT";
    public static final String COLUMN_TYPE_STRING = "VARCHAR";
    public static final String COLUMN_TYPE_REAL = "";
    public static final String COLUMN_TYPE_BOOLEAN = "BOOLEAN";
    public static final String COLUMN_TYPE_BOLB = "BOLB";
    public static final String COLUMN_TYPE_DATE = "DATE";
    public static final String COLUMN_TYPE_TIME = "TIME";


    private String columnStatement = null;
    private String columnName;
    private String columnType;
    private boolean isNull = true;
    private String defaultValue;

    public Column() {

    }

    public Column(String columnName, String columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.isNull = true;
        setColumnStatement();
    }

    public Column(String columnName, String columnType, boolean notNull) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.isNull = notNull;
        setColumnStatement();
    }

    public Column(String columnName, String columnType, boolean notNull, String defaultValue) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.isNull = notNull;
        this.defaultValue = defaultValue;
        setColumnStatement();
    }

    public Column(String columnName, String columnType, String defaultValue) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.defaultValue = defaultValue;
        this.isNull = true;
        setColumnStatement();
    }

    public Column(String isFree, String columnTypeInteger, int i) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public static String setStringType(int length) {
        return COLUMN_TYPE_STRING + "(" + String.valueOf(length) + ")";
    }

    public String getColumnStatement() {
        if (columnStatement == null) {
            setColumnStatement();
        }
        return columnStatement;
    }

    private void setColumnStatement() {
        if (columnName == null) {
            System.out.println("Please give column Name");
            return;
        }
        if (columnType == null) {
            System.out.println("Please give column Type");
            return;
        }
        columnStatement = columnName + " " + columnType;
        if (!isNull) {
            columnStatement = columnStatement + " not null";
        }
        if (defaultValue!=null) {
            columnStatement =  columnStatement + " DEFAULT "+defaultValue;
        }
    }

    public void setColumnName(String name) {
        columnName = name;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnType(String type) {
        columnType = type;
    }
}
