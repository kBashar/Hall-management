package sample;

import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.StudentInfoTable;
import laplab.lib.databasehelper.QueryHelper;
import laplab.student.StudentInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/27/14
 * Time: 8:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetDataFromDatabase {

    GetDataFromDatabase() {

    }

    public ObservableList<StudentInfo> printData() {
        QueryHelper queryHelper = new QueryHelper(DataBaseConnection.getConnection());

        ResultSet resultSet = queryHelper.query(DataBaseConstant.STUDENT_INFO_TABLE_NAME);
        ObservableList<StudentInfo> studentInfoObservableList = FXCollections.observableArrayList();
        try {

            while (resultSet.next()) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(resultSet.getInt(StudentInfoTable.ID_COLUMN));
                studentInfo.setName(resultSet.getString(StudentInfoTable.NAME_COLUMN));
                studentInfo.setContact(resultSet.getString(StudentInfoTable.CONTACT_COLUMN));
                studentInfo.setDept(resultSet.getInt(StudentInfoTable.DEPARTMENT_COLUMN));
                studentInfo.setBatch(resultSet.getInt(StudentInfoTable.BATCH_COLUMN));
                studentInfo.setRoom(resultSet.getInt(StudentInfoTable.ROOM_COLUMN));
                studentInfoObservableList.add(studentInfo);
                resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return studentInfoObservableList;
    }

    public ObservableList<StudentInfo> printData(ResultSet resultSet) {
        ObservableList<StudentInfo> studentInfoObservableList = FXCollections.observableArrayList();
        try {

            int count = 0;
            while (resultSet.next()) {
                count++;
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(resultSet.getInt(StudentInfoTable.ID_COLUMN));
                studentInfo.setName(resultSet.getString(StudentInfoTable.NAME_COLUMN));
                studentInfo.setContact(resultSet.getString(StudentInfoTable.CONTACT_COLUMN));
                studentInfo.setDept(resultSet.getInt(StudentInfoTable.DEPARTMENT_COLUMN));
                studentInfo.setBatch(resultSet.getInt(StudentInfoTable.BATCH_COLUMN));
                studentInfo.setRoom(resultSet.getInt(StudentInfoTable.ROOM_COLUMN));
                studentInfoObservableList.add(studentInfo);
            }
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return studentInfoObservableList;
    }

}

