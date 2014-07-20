package laplab.hallmanagement.DiningInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/3/14
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentDiningInfo {
    private int studentID;
    private int room;
    ArrayList<MonthlyInfo> monthlyInfos;
    private long totalAmount = 0;
    private long totalFine = 0;
    private long totalCredit = 0;

    public StudentDiningInfo() {
        monthlyInfos = new ArrayList<MonthlyInfo>();
    }

    public StudentDiningInfo(int id) {
        studentID = id;
        monthlyInfos = new ArrayList<MonthlyInfo>();
    }

    public void setStudentID(int id) {
        studentID = id;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setRoom(int room)    {
        this.room=room;
    }

    public void addMonthlyInfo(MonthlyInfo monthlyInfo) {
        if (monthlyInfo != null) {
            if (!monthlyInfos.contains(monthlyInfo)) {
                monthlyInfos.add(monthlyInfo);
            }
        }
    }

    public ArrayList<MonthlyInfo> getMonthlyInfos() {
        return monthlyInfos;
    }

    public void calculateTotal() {
        for (MonthlyInfo monthlyInfo : this.getMonthlyInfos()) {
            totalAmount += monthlyInfo.getAmount();
            totalFine += monthlyInfo.getFine();
            totalCredit += monthlyInfo.getCredit();
        }
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public long getTotalFine() {
        return totalFine;
    }

    public long getTotalCredit() {
        return totalCredit;
    }

    public int getRoom()    {
        return room;
    }

}
