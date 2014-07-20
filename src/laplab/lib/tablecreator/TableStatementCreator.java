package laplab.lib.tablecreator;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class TableStatementCreator {

    private static final String STATEMENT_START = "CREATE TABLE";

    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private String tableName;

    private String statement = null;
    private ColumnList columns = null;
    private PrimaryKey primaryKey = null;
    private ForeignKeyList foreignKeys = null;

    public TableStatementCreator() {

    }

    public TableStatementCreator(String tableName, ColumnList columns, PrimaryKey primaryKey, ForeignKeyList foreignKey)

    {
        this.tableName = tableName;
        this.columns = columns;
        this.primaryKey = primaryKey;
        this.foreignKeys = foreignKey;
    }

    public TableStatementCreator(String tableName, ColumnList columns, PrimaryKey primaryKey)

    {
        this.tableName = tableName;
        this.columns = columns;
        this.primaryKey = primaryKey;
    }

    public TableStatementCreator(String tableName, ColumnList columns)

    {
        this.tableName = tableName;
        this.columns = columns;
    }

    public TableStatementCreator(String tableName)

    {
        this.tableName = tableName;
        columns = new ColumnList();
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setColumns(ColumnList columnList) {
        columns = columnList;
    }

    public void setPrimaryKey(PrimaryKey primaryKey1) {
        primaryKey = primaryKey1;
    }

    public void setForeignKey(ForeignKeyList foreignKey1) {
        foreignKeys = foreignKey1;
    }

    private void makeStatement() {
        if (tableName == null || tableName.isEmpty()) {
            System.out.println("Enter a Valid Table Name");
            return;
        }
        if (columns.isEmpty()) {
            System.out.println("Enter Table Columns");
            return;
        }
        if (primaryKey == null) {
            System.out.println("Enter Primary Key");
            return;
        }


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(STATEMENT_START);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(tableName);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(CommonCharacters.FIRSTBRACES);
        stringBuilder.append(CommonCharacters.SPACE);

        for (Column column : columns) {
            stringBuilder.append(column.getColumnStatement());
            stringBuilder.append(CommonCharacters.COMMA);
        }
        stringBuilder.append(primaryKey.getStatement());
        stringBuilder.append(CommonCharacters.COMMA);
        if (foreignKeys != null && !foreignKeys.isEmpty()) {
            for (ForeignKey foreignKey : foreignKeys) {
                if (foreignKey != null) {
                    stringBuilder.append(foreignKey.getStatement());
                    stringBuilder.append(CommonCharacters.COMMA);
                }

            }
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append(CommonCharacters.SECONDBRACES);
        statement = stringBuilder.toString();
    }

    public String getStatement() {
        if (statement == null) {
            makeStatement();
        }
        return statement;
    }

    public String makeStatement(String tableName, List<Column> columns, PrimaryKey primaryKey, ForeignKey foreignKey) {
        if (tableName == null || tableName.isEmpty()) {
            System.out.println("Enter a Valid Table Name");
            return null;
        }
        if (columns.isEmpty()) {
            System.out.println("Enter a Valid Table Columns");
            return null;
        }
        if (primaryKey == null) {
            System.out.println("Enter Primary Key");
            return null;
        }


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(STATEMENT_START);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(tableName);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(CommonCharacters.FIRSTBRACES);
        stringBuilder.append(CommonCharacters.SPACE);

        for (Column column : columns) {
            stringBuilder.append(column.getColumnStatement());
            stringBuilder.append(CommonCharacters.COMMA);
        }

        if (foreignKey == null) {
            stringBuilder.append(primaryKey.getStatement());
            stringBuilder.append(CommonCharacters.SECONDBRACES);
        } else {
            stringBuilder.append(primaryKey.getStatement());
            stringBuilder.append(CommonCharacters.COMMA);
            stringBuilder.append(foreignKey.getStatement());
            stringBuilder.append(CommonCharacters.SECONDBRACES);
        }

        return stringBuilder.toString();
    }
}
