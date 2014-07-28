package laplab.hallmanagement.DiningInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laplab.hallmanagement.Month;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.lib.databasehelper.QueryHelper;
import laplab.lib.tablecreator.CommonCharacters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/3/14
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class DiningDatafromDatabase {
    private static final Logger log= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static String mainQueryString = "select studentinfo.id as Student_ID,studentinfo.ROOM as Room," +
            "dining.amount,dining.credit_day,month.month,month.year,batch.fine" +
            ",FINE_CREDIT.ISFREE from studentInfo,batch\n" +
            "cross join month\n" +
            "left outer join dining\n" +
            "on dining.month_id=month.ID and dining.student_ID=studentinfo.id \n" +
            "left outer join fine_credit\n" +
            "on fine_credit.month_id=month.id and  fine_credit.batch=studentinfo.batch and fine_credit.dept=studentinfo.department\n" +
            "where  studentinfo.batch=batch.id";
    public int startYear = -1;
    public int startMonth = -1;
    public int endYear = -1;
    public int endMonth = -1;
    public int batch = -1;
    public int department = -1;
    private int totalMonth = -1;

    public DiningDatafromDatabase() {

    }


    public ObservableList<StudentDiningInfo> getAllDiningData() {
        int nowMonth = Month.getCurrentMonth();
        int nowYear = Month.getCurrentYear();
        int monthCount = nowMonth + 1;
        if (monthCount < 6) {
            startMonth = 12 - (6 - monthCount);
            startYear = nowYear - 1;
        } else {
            startMonth = nowMonth - 6;
            startYear = nowYear;
        }

        endMonth = nowMonth;
        endYear = nowYear;
        totalMonth = 6;
        return getCustomizedDiningData();
    }

    public ObservableList<StudentDiningInfo> getCustomizedDiningData() {
        StringBuilder stringBuilder = new StringBuilder();
        String queryString = "";
        int count = 0;
        if (endMonth != -1 && endYear != -1 && startMonth != -1 && startYear != -1) {
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append("month.id" + CommonCharacters.SPACE);
            stringBuilder.append("between" + CommonCharacters.SPACE);
            stringBuilder.append(Month.getMonthID(startYear, startMonth));
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append(CommonCharacters.AND + CommonCharacters.SPACE);
            stringBuilder.append(Month.getMonthID(endYear, endMonth));
            stringBuilder.append(CommonCharacters.SPACE);
            count++;
        }

        if (batchAppending() != null) {
            if (count > 0) {
                stringBuilder.append(CommonCharacters.SPACE);
                stringBuilder.append(CommonCharacters.AND);
                stringBuilder.append(CommonCharacters.SPACE);
            }
            stringBuilder.append(batchAppending());
            count++;
        }
        if (departmentAppending() != null) {

            if (count > 0) {
                stringBuilder.append(CommonCharacters.SPACE);
                stringBuilder.append(CommonCharacters.AND);
                stringBuilder.append(CommonCharacters.SPACE);
            }
            stringBuilder.append(departmentAppending());
        }
        String st = stringBuilder.toString();
        if (!st.isEmpty()) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(mainQueryString);
            stringBuilder1.append(CommonCharacters.SPACE);
            stringBuilder1.append("and");
            stringBuilder1.append(CommonCharacters.SPACE);
            stringBuilder1.append(st);

            queryString = stringBuilder1.toString();
            System.out.println(queryString);
            log.warning(queryString);
        } else {

            System.out.println(mainQueryString);
            log.warning(mainQueryString);
            queryString = mainQueryString;
        }
        return getStudentsDiningInfoList(
                new QueryHelper(DataBaseConnection.getConnection()
                ).queryInDataBase(queryString),
                Month.countMonth(startYear, startMonth, endYear, endMonth));
    }

    private String batchAppending() {
        if (batch != -1) {
            String st = "studentInfo.batch=" + String.valueOf(batch);

            return st;
        } else return null;
    }

    private String departmentAppending() {
        if (department != -1) {
            String st = "studentInfo.department=" + String.valueOf(department);
            return st;
        } else return null;
    }

    private ObservableList<StudentDiningInfo> getStudentsDiningInfoList(ResultSet resultSet, int monthCount) {
        ObservableList<StudentDiningInfo> studentDiningInfos = FXCollections.observableArrayList();

        int count = 0;
        StudentDiningInfo studentDiningInfo = new StudentDiningInfo();
        try {
            while (resultSet.next()) {

                if (count == monthCount) {
                    studentDiningInfo.calculateTotal();
                    studentDiningInfos.add(studentDiningInfo);
                    studentDiningInfo = new StudentDiningInfo();
                    count = 0;
                }

                int student_id = resultSet.getInt("Student_ID");
                int room = resultSet.getInt("Room");
                if (count == 0) {
                    studentDiningInfo.setStudentID(student_id);
                    studentDiningInfo.setRoom(room);
                }
                int month = resultSet.getInt("Month");
                int year = resultSet.getInt("Year");
                int amount = resultSet.getInt("amount");
                int fine = resultSet.getInt("fine");//check
                int isFineFree = resultSet.getInt("isfree");
                int credit = resultSet.getInt("credit_day");
                MonthlyInfo monthlyInfo = new MonthlyInfo(month, amount, year, fine, credit, isFineFree);
                studentDiningInfo.addMonthlyInfo(monthlyInfo);
                count++;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

        }

        return studentDiningInfos;
    }
}
