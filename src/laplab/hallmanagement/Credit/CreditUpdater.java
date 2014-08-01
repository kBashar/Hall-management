package laplab.hallmanagement.Credit;

import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.lib.databasehelper.DataBaseHelper;
import laplab.lib.databasehelper.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kBashar
 * Date: 7/21/14
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreditUpdater {


    public static int updateCreditDayCount(String id, String monthId,int credit) {
        if (checkCredit(id, monthId) > 0) {
            int previousCreditDay= getPrevious(id, monthId);
            credit+=previousCreditDay; //total credit day
            HashMap map=new HashMap();
            map.put("credit_day",credit);
            int check =  new DataBaseHelper(
                    DataBaseConnection.getConnection()).UpdateDataBase(
                    DataBaseConstant.DINING_INFO_TABLE_NAME,
                    map,
                    "where student_id = " + id + " and month_id = " + monthId);
            if (check>0)    {
                return 1;
            }  else {
                return -1;
            }
        }   else {
            return -2;
        }
    }

    private static int getPrevious(String id, String monthID) {
        String query = "SELECT credit_day from dining where student_id = " + id + " and month_id = " + monthID;
        ResultSet creditDayCount = new QueryHelper(DataBaseConnection.getConnection()).queryInDataBase(query);
        try {
            while (creditDayCount.next()) {
                int count = creditDayCount.getInt("credit_day");
                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return -1;
    }

    private static int checkCredit(String id, String monthID) {
        String query = "SELECT count(STUDENT_ID) from dining where student_id = " + id + " and month_id = " + monthID;
        ResultSet rowCount = new QueryHelper(DataBaseConnection.getConnection()).queryInDataBase(query);
        try {
            while (rowCount.next()) {
                int count = rowCount.getInt(1);
                if (count == 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return -1;
    }
}
