package laplab.lib.databasehelper;

import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.StudentInfoTable;
import laplab.lib.tablecreator.CommonCharacters;
import org.hsqldb.HsqlException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 11:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataBaseHelper {

    private static final String INSERT = "INSERT INTO";
    private static final String UPDATE = "UPDATE";
    private static final String VALUES = "values";
    private static final String SET = "set";
    private static final String DELETE= "DELETE FROM";
    private static final String WHERE= "WHERE";
    private Connection connection;
    Statement statement = null;

    public DataBaseHelper(Connection connection) {
        this.connection = connection;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public int insertIntoDataBase(String tableName, HashMap map) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(INSERT);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(tableName);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(CommonCharacters.FIRSTBRACES);
        stringBuilder.append(CommonCharacters.SPACE);

        Set entrySet = map.entrySet();
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            stringBuilder.append(entry.getKey());
            stringBuilder.append(CommonCharacters.COMMA);
            stringBuilder.append(CommonCharacters.SPACE);
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append(CommonCharacters.SECONDBRACES);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(VALUES);
        stringBuilder.append(CommonCharacters.FIRSTBRACES);
        stringBuilder.append(CommonCharacters.SPACE);
        iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            stringBuilder.append(CommonCharacters.SINGLE_COUAT);
            stringBuilder.append(entry.getValue());
            stringBuilder.append(CommonCharacters.SINGLE_COUAT);
            stringBuilder.append(CommonCharacters.COMMA);
            stringBuilder.append(CommonCharacters.SPACE);
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append(CommonCharacters.SECONDBRACES);
        System.out.println(stringBuilder.toString());
        return execute(stringBuilder.toString());

    }

    public int deleteFromDatabase(int id){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(DELETE);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(DataBaseConstant.STUDENT_INFO_TABLE_NAME);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(WHERE);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(StudentInfoTable.ID_COLUMN);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(CommonCharacters.EQUAL_SIGN);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(id);
        return execute(stringBuilder.toString());
      /* NEED TO EDIT FOR DINNING TABLE
        System.out.println(stringBuilder.toString());
        stringBuilder=new StringBuilder();
        stringBuilder.append(DELETE);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(DataBaseConstant.DINING_INFO_TABLE_NAME);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(WHERE);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(StudentInfoTable.ID_COLUMN);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(CommonCharacters.EQUAL_SIGN);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(id);
        execute(stringBuilder.toString());*/
    }

    public int UpdateDataBase(String tableName, HashMap map, HashMap selectionArgs) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UPDATE);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(tableName);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(SET);
        stringBuilder.append(CommonCharacters.SPACE);

        Set entrySet = map.entrySet();
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            stringBuilder.append(entry.getKey());
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append(CommonCharacters.EQUAL_SIGN);
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append(entry.getValue());
            stringBuilder.append(CommonCharacters.COMMA);
            stringBuilder.append(CommonCharacters.SPACE);
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append("where");
        stringBuilder.append(CommonCharacters.SPACE);

        entrySet = selectionArgs.entrySet();
        iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            stringBuilder.append(entry.getKey());
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append(CommonCharacters.EQUAL_SIGN);
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append(entry.getValue());
            stringBuilder.append(CommonCharacters.COMMA);
            stringBuilder.append(CommonCharacters.SPACE);
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));

        System.out.println(stringBuilder.toString());
        return execute(stringBuilder.toString());
    }


    private int execute(String querryString) {

        try {
            return statement.executeUpdate(querryString);
        } catch (SQLIntegrityConstraintViolationException e) {
            String[] localizedErrorMessages = e.getLocalizedMessage().split(";");
            String errorMessage = localizedErrorMessages[0];
            if (errorMessage.equals("integrity constraint violation: unique constraint or index violation")) {
                System.out.println("This batch or dept already Exists");
                return -1;
            }  else if (errorMessage.equals("integrity constraint violation: foreign key no parent"))  {
                System.out.println("This Students batch or dept doesn't Exist");
                return -2;
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return 0;
    }

    public int UpdateDataBase(String tableName, HashMap map, String tryStr) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UPDATE);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(tableName);
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(SET);
        stringBuilder.append(CommonCharacters.SPACE);

        Set entrySet = map.entrySet();
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            stringBuilder.append(entry.getKey());
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append(CommonCharacters.EQUAL_SIGN);
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append(entry.getValue());
            stringBuilder.append(CommonCharacters.COMMA);
            stringBuilder.append(CommonCharacters.SPACE);
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append(CommonCharacters.SPACE);
        stringBuilder.append(tryStr);

        System.out.println(stringBuilder.toString());
        return execute(stringBuilder.toString());
    }
}
