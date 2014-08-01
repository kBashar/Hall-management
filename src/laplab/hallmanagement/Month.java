package laplab.hallmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/2/14
 * Time: 12:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class Month {

    public static ObservableList<String> getMonthList() {
        ObservableList<String> monthList = FXCollections.observableArrayList();
        monthList.addAll(new String[]{
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"});
        return monthList;
    }

    public static int getCurrentMonth() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        return month;
    }

    public static int getCurrentYear() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.YEAR);

        return month;
    }


    public static int getMonthIndex(String month) {
        ObservableList<String> monthList = FXCollections.observableArrayList();
        monthList.addAll(new String[]{
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"});
        return monthList.indexOf(month);
    }

    public static String getMonthName(int month) {
        String[] months = new String[]{
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"};

        return months[month];
    }

    public static int getMonthID(int year, int month) {
        year = year * 100;
        return year + month;
    }

    public static int countMonth(int startYear, int startMonth, int endYear, int endMonth) {
        int yearDifference = startYear - endYear - 1;
        int monthDifference = (13 - (startMonth + 1) + (endMonth + 1));
        System.out.println(yearDifference * 12 + monthDifference);
        return yearDifference * 12 + monthDifference;
    }
}