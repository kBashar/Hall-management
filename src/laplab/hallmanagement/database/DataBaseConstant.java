package laplab.hallmanagement.database;

/**
 * Created with IntelliJ IDEA.
 * User: khyrul Bashar
 * Date: 2/26/14
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataBaseConstant {
    public static final String STUDENT_INFO_TABLE_NAME = "STUDENTINFO";
    public static final String DINING_INFO_TABLE_NAME = "DINING";
    public static final String BATCH_TABLE_NAME = "BATCH";
    public static final String DEPARTMENT_TABLE_NAME = "DEPARTMENT";
    public static final String FINE_TABLE_NAME = "FINE_CREDIT";
    public static final String MONTH_TABLE = "MONTH";


    public static String getDingingTableNameForYear(int year) {
        String yearS = String.valueOf(year % 2000);
        System.out.println(yearS);
        return DINING_INFO_TABLE_NAME + yearS;
    }

}
