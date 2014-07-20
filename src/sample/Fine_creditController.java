/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import laplab.hallmanagement.DiningInfo.DiningDatafromDatabase;
import laplab.hallmanagement.DiningInfo.MonthlyInfo;
import laplab.hallmanagement.DiningInfo.StudentDiningInfo;
import laplab.hallmanagement.Month;
import laplab.hallmanagement.pdf.PDFMaker;
import laplab.student.StudentInfo;


/**
 * FXML Controller class
 *
 * @author AURANGO SABBIR
 */
public class Fine_creditController implements Initializable {

    public ComboBox monthComboboxEnd;
    public TextField yearFieldEnd;
    public TextField yearFieldStart;
    public ComboBox monthComboboxStart;
    public TextField batchField;
    public TextField deptField;

    public CheckBox amountCheckBox;
    public CheckBox fineCheckBox;
    public CheckBox creditCheckBox;
    public TableView tableView;

    public static final String FINE = "fine";
    public static final String CREDIT = "credit";
    public static final String AMOUNT = "amount";

    ObservableList<StudentDiningInfo> list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> monthList = Month.getMonthList();

        monthComboboxEnd.setItems(monthList);
        monthComboboxStart.setItems(monthList);

        monthComboboxStart.setValue(monthList.get(0));
        monthComboboxEnd.setValue(monthList.get(Month.getCurrentMonth()));
        DiningDatafromDatabase diningDatafromDatabase = new DiningDatafromDatabase();
        list = diningDatafromDatabase.getAllDiningData();

       /* for (StudentDiningInfo studentDiningInfo : list) {
            System.out.print(studentDiningInfo.getStudentID());
            ArrayList<MonthlyInfo> monthlyInfos = studentDiningInfo.getMonthlyInfos();
            for (MonthlyInfo monthlyInfo : monthlyInfos) {
                System.out.print(" ");
                System.out.print(monthlyInfo.getYear() + " " + monthlyInfo.getMonth()
                        + " " + monthlyInfo.getAmount() + " " + monthlyInfo.getFine() +
                        monthlyInfo.getCredit() + " " + monthlyInfo.isFineFree());
            }
            System.out.println();
        }  */
        populateDataInTable(list, "amount");
    }

    public void fineCheckButtonClicked(ActionEvent actionEvent) {
        DiningDatafromDatabase diningDatafromDatabase = new DiningDatafromDatabase();
        String monthStart = (String) monthComboboxStart.getValue();
        String yearStart = yearFieldStart.getText();
        if (monthStart != null && yearStart != null) {
            if (!monthStart.isEmpty() && !yearStart.isEmpty()) {
                String monthEnd = (String) monthComboboxEnd.getValue();
                String yearEnd = yearFieldEnd.getText();
                if (monthEnd != null && yearEnd != null) {
                    if (!monthEnd.isEmpty() && !yearEnd.isEmpty()) {
                        diningDatafromDatabase.startMonth = Month.getMonthIndex(monthStart);
                        diningDatafromDatabase.startYear = Integer.parseInt(yearStart);
                        diningDatafromDatabase.endMonth = Month.getMonthIndex(monthEnd);
                        diningDatafromDatabase.endYear = Integer.parseInt(yearEnd);

                        String batch = batchField.getText();
                        if (batch != null) {
                            if (!batch.isEmpty()) {
                                diningDatafromDatabase.batch = Integer.parseInt(batch);
                            }
                        }
                        String dept = deptField.getText();
                        if (dept != null) {
                            if (!dept.isEmpty()) {
                                diningDatafromDatabase.department = StudentInfo.getDepartmentID(dept);
                            }
                        }
                    } else {
                        System.out.println("Enter Month and year end");
                    }

                } else {
                    System.out.println("Enter Month and year end");
                }
            } else {
                System.out.println("Enter Month and year start");
            }
        } else {
            System.out.println("Enter Month and year start");
        }

        list = diningDatafromDatabase.getCustomizedDiningData();

        /* for (StudentDiningInfo studentDiningInfo : list) {
            System.out.print(studentDiningInfo.getStudentID());
            ArrayList<MonthlyInfo> monthlyInfos = studentDiningInfo.getMonthlyInfos();
            for (MonthlyInfo monthlyInfo : monthlyInfos) {
                System.out.print(" ");
                System.out.print(monthlyInfo.getYear() + " " + monthlyInfo.getMonth() + " " + monthlyInfo.getAmount());
            }
            System.out.println();
        } */

        if (creditCheckBox.isSelected()) {
            populateDataInTable(list, CREDIT);
        } else if (fineCheckBox.isSelected()) {
            populateDataInTable(list, FINE);
        } else if (amountCheckBox.isSelected()) {
            populateDataInTable(list, AMOUNT);
        }


    }

    public void creditCheckBoxClicked(ActionEvent actionEvent) {

        if (amountCheckBox.isSelected()) {
            amountCheckBox.setSelected(false);
        }

        if (fineCheckBox.isSelected()) {
            fineCheckBox.setSelected(false);
        }

        if (creditCheckBox.isSelected()) {
            if (list != null) {
                populateDataInTable(list, CREDIT);
            }
        }
    }

    public void fineCheckBoxClicked(ActionEvent actionEvent) {
        if (fineCheckBox.isSelected()) {

            if (creditCheckBox.isSelected()) {
                creditCheckBox.setSelected(false);
            }

            if (amountCheckBox.isSelected()) {
                amountCheckBox.setSelected(false);
            }

            if (list != null) {
                populateDataInTable(list, FINE);
            }
        }
    }

    public void amountCheckBoxClicked(ActionEvent actionEvent) {
        if (amountCheckBox.isSelected()) {

            if (fineCheckBox.isSelected()) {
                fineCheckBox.setSelected(false);
            }

            if (creditCheckBox.isSelected()) {
                creditCheckBox.setSelected(false);
            }


            if (list != null) {
                populateDataInTable(list, AMOUNT);
            }
        }
    }


    private void populateDataInTable(ObservableList<StudentDiningInfo> studentDiningInfos, String what_to_show) {
        StudentDiningInfo sample = studentDiningInfos.get(0);
        ArrayList<MonthlyInfo> monthlyInfos = sample.getMonthlyInfos();
        tableView.getColumns().clear();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        int i = 0;
        tableView.setItems(studentDiningInfos);
        addIDColumn();
        for (MonthlyInfo monthlyInfo : monthlyInfos) {
            String columnName = Month.getMonthName(monthlyInfo.getMonth())
                    + "'" +
                    String.valueOf(monthlyInfo.getYear() % 2000);
            System.out.println(columnName);
            TableColumn tableColumn = new TableColumn(columnName);
            tableColumn.setCellValueFactory(new CustomizedCallback(i, what_to_show));
            tableView.getColumns().add(tableColumn);
            i++;
        }
        addTotalColumn(what_to_show);

    }

    private void addIDColumn() {
        TableColumn tableColumn = new TableColumn("STUDENT ID");
        tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentDiningInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentDiningInfo, String> cellDataFeatures) {
                return new SimpleStringProperty(String.valueOf(cellDataFeatures.getValue().getStudentID()));  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        tableView.getColumns().add(tableColumn);
    }

    private void addTotalColumn(final String type) {
        TableColumn tableColumn = new TableColumn("TOTAL");
        tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentDiningInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentDiningInfo, String> cellDataFeatures) {
                if (type.equals(AMOUNT)) {
                    return new SimpleStringProperty(String.valueOf(cellDataFeatures.getValue().getTotalAmount()));
                } else if (type.equals(FINE)) {
                    return new SimpleStringProperty(String.valueOf(cellDataFeatures.getValue().getTotalFine()));
                } else if (type.equals(CREDIT)) {
                    return new SimpleStringProperty(String.valueOf(cellDataFeatures.getValue().getTotalCredit()));
                } else {
                    return new SimpleStringProperty(String.valueOf(0));
                }
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        tableView.getColumns().add(tableColumn);
    }

    public void print_tempClicked(ActionEvent actionEvent) {

        if (fineCheckBox.isSelected()) {
            print_Fine();
        } else if (creditCheckBox.isSelected()) {
            print_Credit();
        } else if (amountCheckBox.isSelected()) {
            print_Amount();
        }
    }

    public void print_Fine() {

        if (list != null) {
            ObservableList<StudentDiningInfo> studentDiningInfos = FXCollections.observableList(list);
            sort(studentDiningInfos);
            PDFMaker pdfMaker=new PDFMaker(studentDiningInfos,Fine_creditController.FINE);
            pdfMaker.make();
            /*for (StudentDiningInfo studentDiningInfo : studentDiningInfos) {
                if (studentDiningInfo.getTotalFine() > 0) {

                    System.out.println(
                            studentDiningInfo.getStudentID() + "\t" +
                                    studentDiningInfo.getRoom() + "\t" +
                                    studentDiningInfo.getTotalFine()
                    );
                }
            } */
        }
    }

    public void print_Amount() {

        if (list != null) {
            ObservableList<StudentDiningInfo> studentDiningInfos = FXCollections.observableArrayList(list);
            sort(studentDiningInfos);
            PDFMaker pdfMaker=new PDFMaker(studentDiningInfos,Fine_creditController.AMOUNT);
            pdfMaker.make();
            /* for (StudentDiningInfo studentDiningInfo : studentDiningInfos) {
                if (studentDiningInfo.getTotalAmount() > 0) {

                    System.out.println(
                            studentDiningInfo.getStudentID() + "\t" +
                                    studentDiningInfo.getRoom() + "\t" +
                                    studentDiningInfo.getTotalAmount()
                    );
                }
            }     */
        }
    }

    public void print_Credit() {

        if (list != null) {
            ObservableList<StudentDiningInfo> studentDiningInfos = FXCollections.observableArrayList(list);
            sort(studentDiningInfos);
            PDFMaker pdfMaker=new PDFMaker(studentDiningInfos,Fine_creditController.CREDIT);
            pdfMaker.make();
            /* for (StudentDiningInfo studentDiningInfo : studentDiningInfos) {
                if (studentDiningInfo.getTotalCredit() > 0) {
                    System.out.println(
                            studentDiningInfo.getStudentID() + "\t" +
                                    studentDiningInfo.getRoom() + "\t" +
                                    studentDiningInfo.getTotalCredit()
                    );
                }
            }      */
        }
    }

    private static ObservableList<StudentDiningInfo> sort(ObservableList<StudentDiningInfo> studentDinningList) {
        FXCollections.sort(studentDinningList, new Comparator<StudentDiningInfo>() {
            @Override
            public int compare(StudentDiningInfo o1, StudentDiningInfo o2) {
                return o1.getRoom() - o2.getRoom();  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        return studentDinningList;
    }
}
