package laplab.lib.tablecreator;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrimaryKey {
    private String column = null;

    public String statement = null;

    public PrimaryKey() {

    }

    public PrimaryKey(String col) {
        column = col;
        makeStatement();
    }

    public PrimaryKey(Column column) {
        this.column = column.getColumnName();
        makeStatement();
    }

    public void setColumn(Column column) {
        this.column = column.getColumnName();
    }

    private void makeStatement() {
        if (column == null || column.isEmpty()) {
            System.out.println("Enter Valid Column Name");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Primary Key ");
        stringBuilder.append("(" + column + ")");

        statement = stringBuilder.toString();
    }

    public String makeStatement(String column) {
        if (column == null || column.isEmpty()) {
            System.out.println("Enter Valid Column Name");
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Primary Key ");
        stringBuilder.append("(" + column + ")");

        return stringBuilder.toString();
    }

    public String getStatement() {
        if (statement == null) {
            makeStatement();
        }
        return statement;
    }
}
