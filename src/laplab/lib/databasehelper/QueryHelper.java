package laplab.lib.databasehelper;

import laplab.lib.tablecreator.CommonCharacters;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/27/14
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryHelper {

    private static final String STATEMENT_START = "SELECT";
    private static final String STATEMENT_FROM = "from";
    private static final String STATEMENT_WHERE = "where";


    private StringBuilder stringBuilder;

    Connection connection;

    public QueryHelper(Connection connection) {
        this.connection = connection;
    }

    public ResultSet query(String table) {
        stringBuilder = new StringBuilder();

        ResultSet resultSet = null;
        addStatementStart();
        addColumns(null);
        addStatementTableName(table);
        //String queryString=stringBuilder.toString();
        //System.out.println(queryString);
        return queryInDataBase(stringBuilder.toString());
    }

    public ResultSet query(String table, String selection) {
        stringBuilder = new StringBuilder();

        ResultSet resultSet = null;
        addStatementStart();
        addColumns(null);
        addStatementTableName(table);
        stringBuilder.append(STATEMENT_WHERE);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(selection);
        //String queryString=stringBuilder.toString();
        System.out.println(stringBuilder.toString());
        return queryInDataBase(stringBuilder.toString());
    }

    public ResultSet query(String table, String[] columns) {
        stringBuilder = new StringBuilder();

        ResultSet resultSet = null;
        addStatementStart();
        addColumns(columns);
        addStatementTableName(table);
        //String queryString=stringBuilder.toString();
        //System.out.println(queryString);
        return queryInDataBase(stringBuilder.toString());
    }

    public ResultSet query(String table, String[] columns, String[] selection, String[] selectionArgs) {
        stringBuilder = new StringBuilder();
        ResultSet resultSet = null;

        addStatementStart();
        addColumns(columns);
        addStatementTableName(table);
        addSelections(selection, selectionArgs);
        //String queryString=stringBuilder.toString();
        //System.out.println(queryString);
        return queryInDataBase(stringBuilder.toString());
    }

    public ResultSet query(String table, String[] columns, String[] selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        stringBuilder = new StringBuilder();
        return null;
    }

    public ResultSet query(String table, String[] columns, String[] selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return null;
    }

    public ResultSet query(boolean distinct, String table, String[] columns, String[] selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return null;
    }

    public ResultSet rawQuery(String sql, String[] selectionArgs) {
        return null;
    }

    private void addColumns(String[] columns) {
        if (columns == null) {
            stringBuilder.append(CommonCharacters.ASTERISK);
            stringBuilder.append(CommonCharacters.SPACE);
        } else {
            for (String column : columns) {
                if (column != null || !column.isEmpty()) {
                    stringBuilder.append(CommonCharacters.SINGLE_COUAT);
                    stringBuilder.append(column);
                    stringBuilder.append(CommonCharacters.SINGLE_COUAT);
                    stringBuilder.append(CommonCharacters.COMMA);
                    stringBuilder.append(CommonCharacters.SPACE);
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(CommonCharacters.COMMA));

        }
    }

    private void addStatementStart() {
        stringBuilder.append(STATEMENT_START);
        stringBuilder.append(CommonCharacters.SPACE);
    }

    private void addStatementTableName(String table) {
        stringBuilder.append(STATEMENT_FROM);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(table);
        stringBuilder.append(CommonCharacters.SPACE);
    }

    private void addSelections(String[] selections, String[] selectionArgs) {
        if (selections == null || selectionArgs == null || selections.length != selectionArgs.length) {
            return;
        } else {
            boolean flag = false;
            stringBuilder.append(STATEMENT_WHERE);
            stringBuilder.append(CommonCharacters.SPACE);
            for (int i = 0; i < selections.length; i++) {
                if (selections[i] != null || !selections[i].isEmpty() || selectionArgs[i] != null || !selectionArgs[i].isEmpty()) {
                    stringBuilder.append(selections[i]);
                    stringBuilder.append(CommonCharacters.SPACE);
                    stringBuilder.append(CommonCharacters.EQUAL_SIGN);
                    stringBuilder.append(CommonCharacters.SINGLE_COUAT);
                    stringBuilder.append(selectionArgs[i]);
                    stringBuilder.append(CommonCharacters.SINGLE_COUAT);
                    stringBuilder.append(CommonCharacters.COMMA);
                    stringBuilder.append(CommonCharacters.SPACE);
                    flag = true;
                }
            }
            if (!flag) {
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(STATEMENT_WHERE));
            } else {
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(CommonCharacters.COMMA));
            }


        }
    }

    public ResultSet queryInDataBase(String queryString) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(queryString);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return resultSet;
    }
}
