package laplab.hallmanagement.database;

import laplab.hallmanagement.Month;
import laplab.student.StudentInfo;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 10:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataInputer {

    DataInputer() {

    }

    public static HashMap StudentInsert(StudentInfo studentInfo) {
        HashMap map = new HashMap();
        if (!studentInfo.isEmpty()) {


            map.put(StudentInfoTable.ID_COLUMN, studentInfo.getId());
            map.put(StudentInfoTable.NAME_COLUMN, studentInfo.getName());
            map.put(StudentInfoTable.ROOM_COLUMN, studentInfo.getRoom());
            map.put(StudentInfoTable.DEPARTMENT_COLUMN, studentInfo.getDept());
            map.put(StudentInfoTable.BATCH_COLUMN, studentInfo.getBatch());
            map.put(StudentInfoTable.CONTACT_COLUMN, studentInfo.getContact());

            if (studentInfo.getParent_name() != null) {
                map.put(StudentInfoTable.PARENT_NAME_COLUMN, studentInfo.getParent_name());
            }
            if (studentInfo.getParent_contact() != null) {
                map.put(StudentInfoTable.PARENT_CONTACT_COLUMN, studentInfo.getParent_contact());
            }
            if (studentInfo.getBlood_group() != null) {
                map.put(StudentInfoTable.BLOOD_GROUP_COLUMN, studentInfo.getBlood_group());
            }
        }
        return map;
    }

    public static HashMap BatchInsert(String batch_ID, String batch_name) {
        HashMap map = new HashMap();
        if (!batch_name.isEmpty() && !batch_ID.isEmpty()) {

            map.put(BatchTable.BATCH_ID_COLUMN, batch_ID);
            map.put(BatchTable.BATCH_NAME_COLUMN, batch_name);
            map.put(BatchTable.DEPRECATED_BATCH_COLUMN, false);
            map .put(BatchTable.FINE_FOR_BATCH,200);
            map.put(BatchTable.MIN_DAY_FOR_CREDIT,5);
            map.put(BatchTable.MAX_DAY_FOR_CREDIT,10);

        }
        return map;
    }

    public static HashMap DepartmentInsert(String dept_ID, String dept_name) {
        HashMap map = new HashMap();
        if (!dept_ID.isEmpty() && !dept_name.isEmpty()) {

            map.put(DepartmentTable.DEPARTMENT_ID_COLUMN, dept_ID);
            map.put(DepartmentTable.DEPARTMENT_NAME_COLUMN, dept_name);

        }
        return map;
    }

    public static HashMap DinningInfoInsert(String id, String voucher, String amount,int MonthID) {
        HashMap map = new HashMap();
        map.put(DiningTable.DINING_STUDENT_ID,id);
        map.put(DiningTable.VOUCHER_ID,voucher);
        map.put(DiningTable.AMOUNT,amount);
        map.put(DiningTable.MONTH_ID,MonthID);
        return map;
    }

    public static HashMap FineInfoInsert(String year, int month, String batch, int dept) {
        HashMap map = new HashMap();

            map.put(FineTable.MONTH_ID, Month.getMonthID(Integer.parseInt(year),month));
            map.put(FineTable.BATCH,batch);
            map.put(FineTable.DEPARTMENT,dept);
            map.put(FineTable.IS_FREE,1);

        return map;
    }

}
