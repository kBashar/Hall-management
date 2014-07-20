package laplab.lib.tablecreator;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForeignKey {
    private String ColumnName = null;
    private String ReferencedTable = null;
    private String ReferencedColumn = null;

    public String statement = null;

    public ForeignKey() {

    }

    public ForeignKey(String columnname, String referencedtable, String referencedcolumn) {
        ColumnName = columnname;
        ReferencedTable = referencedtable;
        ReferencedColumn = referencedcolumn;
        makeStatement();
    }

    public void setColumnName(String columnName) {
        ColumnName = columnName;
    }

    public void setReferencedTable(String table) {
        ReferencedTable = table;
    }

    public void setReferencedColumn(String columnName) {
        ReferencedColumn = columnName;
    }

    public void setColumnName(Column column) {
        ColumnName = column.getColumnName();
    }

    public void setReferencedColumn(Column column) {
        ReferencedColumn = column.getColumnName();
    }

    private void makeStatement() {
        if (ColumnName == null || ReferencedColumn == null || ReferencedTable == null) {
            System.out.println("Enter valid data");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Foreign Key ");
        stringBuilder.append("(" + ColumnName + ") ");
        stringBuilder.append("References ");
        stringBuilder.append(ReferencedTable);
        stringBuilder.append("(" + ReferencedColumn + ")");

        statement = stringBuilder.toString();

    }

    public String makeStatement(String col, String table, String col2) {
        if (col == null || table == null || col2 == null) {
            System.out.println("Enter valid data");
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Foreign Key ");
        stringBuilder.append("(" + ColumnName + ") ");
        stringBuilder.append("References ");
        stringBuilder.append(ReferencedTable);
        stringBuilder.append("(" + ReferencedColumn + ")");

        return stringBuilder.toString();

    }

    public String getStatement() {
        if (statement == null) {
            makeStatement();
        }
        return statement;
    }
}
