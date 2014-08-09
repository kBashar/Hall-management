/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import laplab.hallmanagement.DiningInfo.DiningDatafromDatabase;
import laplab.hallmanagement.DiningInfo.MonthlyInfo;
import laplab.hallmanagement.DiningInfo.StudentDiningInfo;
import laplab.hallmanagement.Month;
import laplab.hallmanagement.database.DepartmentTable;
import laplab.hallmanagement.pdf.PDFMaker;
import laplab.lib.tablecreator.CommonCharacters;
import laplab.student.StudentInfo;


/**
 * FXML Controller class
 *
 * @author Khyrul Bashar
 */
public class Fine_creditController implements Initializable {
    private static final Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
                                String[] _batches = batch.split(CommonCharacters.COMMA);
                                if (_batches.length == 0) {
                                    diningDatafromDatabase.batches.add(batch);
                                } else {
                                    for (int i = 0; i < _batches.length; i++) {
                                        diningDatafromDatabase.batches.add(_batches[i]);
                                    }
                                }
                            }
                        }
                        String dept = deptField.getText();
                        if (dept != null) {
                            if (!dept.isEmpty()) {
                                String[] _departments = dept.split(CommonCharacters.COMMA);
                                int _deptID;
                                if (_departments.length == 0) {
                                    _deptID = DepartmentTable.getDeptID(dept);
                                    if (_deptID == -1)  {
                                        Dialogs.showErrorDialog(
                                                new Stage(),
                                                "Please Check Department Names \n\n" +
                                                        "\'"+dept+"\' is not a valid Department Name",
                                                "Error in Query",
                                                "Hall Management"
                                        );
                                        return;
                                    }
                                    diningDatafromDatabase.batches.add(String.valueOf(_deptID));
                                } else {
                                    for (int i = 0; i < _departments.length; i++) {
                                        _deptID = DepartmentTable.getDeptID(_departments[i]);
                                        if (_deptID == -1)  {
                                            Dialogs.showErrorDialog(
                                                    new Stage(),
                                                    "Please Check Department Names \n\n" +
                                                            "\'"+_departments[i]+"\' is not a valid  Name",
                                                    "Error in Query",
                                                    "Hall Management"
                                            );
                                            return;
                                        }
                                        diningDatafromDatabase.departments.add(String.valueOf(_deptID));
                                    }
                                }
                            }
                        }
                    } else {
                        showYearMonthErrorDialog();
                        return;
                    }

                } else {
                    showYearMonthErrorDialog();
                    return;
                }
            } else {
                showYearMonthErrorDialog();
                return;
            }
        } else {
            showYearMonthErrorDialog();
            return;
        }
        list = diningDatafromDatabase.getCustomizedDiningData();
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
        } else {
            amountCheckBox.setSelected(true);
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
        if (studentDiningInfos.size() == 0) {
            Dialogs.showInformationDialog(
                    new Stage(),
                    "No data Found \n you might check your query",
                    "Warning",
                    "Hall Management");
            return;
        }
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
            PDFMaker pdfMaker = new PDFMaker(studentDiningInfos, Fine_creditController.FINE);
            pdfMaker.make();
        }
    }

    public void print_Amount() {

        if (list != null) {
            ObservableList<StudentDiningInfo> studentDiningInfos = FXCollections.observableArrayList(list);
            sort(studentDiningInfos);
            PDFMaker pdfMaker = new PDFMaker(studentDiningInfos, Fine_creditController.AMOUNT);
            pdfMaker.make();
        }
    }

    public void print_Credit() {

        if (list != null) {
            ObservableList<StudentDiningInfo> studentDiningInfos = FXCollections.observableArrayList(list);
            sort(studentDiningInfos);
            PDFMaker pdfMaker = new PDFMaker(studentDiningInfos, Fine_creditController.CREDIT);
            pdfMaker.make();
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

    private static void showYearMonthErrorDialog()  {
        Dialogs.showErrorDialog(
                new Stage(),
                "Error in Query \n you should provide both End and Start Month",
                "Error",
                "Hall Management");
    }

}
