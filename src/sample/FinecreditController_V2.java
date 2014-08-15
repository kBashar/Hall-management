package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 8/8/14
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class FinecreditController_V2 implements Initializable {
    public ComboBox monthComboboxStart;
    public TextField yearFieldStart;
    public TextField batchField;
    public TextField deptField;
    public TextField yearFieldEnd;
    public ComboBox monthComboboxEnd;
    public Button print_temp;
    public TableView amountTable;
    public TableView fineTable;
    public TableView creditTable;
    public TextField studentID;

    private int startMonth;
    private int endMonth;
    private int startYear;
    private int endYear;


    ObservableList<StudentDiningInfo> list;
    public static final String FINE = "fine";
    public static final String CREDIT = "credit";
    public static final String AMOUNT = "amount";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> monthList = Month.getMonthList();

        monthComboboxEnd.setItems(monthList);
        monthComboboxStart.setItems(monthList);
        monthComboboxStart.setValue(monthList.get(0));
        monthComboboxEnd.setValue(monthList.get(Month.getCurrentMonth()));
        setInitialMonthYearValues();

        DiningDatafromDatabase diningDatafromDatabase = new DiningDatafromDatabase();
        list = diningDatafromDatabase.getAllDiningData(startMonth,startYear,endMonth,endYear);
        if (list.size() == 0)   {
            Dialogs.showInformationDialog(
                    new Stage(),
                    "No data Found \n you might check your query",
                    "Warning",
                    "Hall Management");
            return;
        }
        populateDataInTable(list, amountTable, AMOUNT);
        populateDataInTable(list, fineTable, FINE);
        populateDataInTable(list, creditTable, CREDIT);
    }

    private void setupTableRowMenus(TableView tableView, final String what_to_show) {
        tableView.setRowFactory(
                new Callback<TableView<StudentDiningInfo>, TableRow<StudentDiningInfo>>() {
                    @Override
                    public TableRow<StudentDiningInfo> call(TableView<StudentDiningInfo> tableView) {
                        final TableRow<StudentDiningInfo> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detailAndEdit = new MenuItem("Detail");
                        detailAndEdit.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                StudentDiningInfo studentDiningInfo = row.getItem();
                                if (what_to_show.equals(AMOUNT)) {
                                    AmountDetail amountDetail = new AmountDetail();
                                    amountDetail.show(
                                            String.valueOf(studentDiningInfo.getStudentID()),
                                            startMonth, startYear,
                                            endMonth,endYear
                                            );
                                } else if (what_to_show.equals(CREDIT)) {
                                    CreditDetail creditDetail = new CreditDetail();
                                    creditDetail.show(String.valueOf(studentDiningInfo.getStudentID()),
                                            startMonth, startYear,
                                            endMonth,endYear
                                    );
                                } else {
                                    //TODO fine table should be implemented.
                                    AmountDetail amountDetail = new AmountDetail();
                                    amountDetail.show(String.valueOf(studentDiningInfo.getStudentID()),
                                            startMonth, startYear,
                                            endMonth,endYear
                                    );
                                }

                            }
                        });
                        rowMenu.getItems().add(detailAndEdit);
                        row.contextMenuProperty().bind(
                                Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                        .then(rowMenu)
                                        .otherwise((ContextMenu) null));
                        return row;
                    }
                }
        );
    }

    private void populateDataInTable(ObservableList<StudentDiningInfo> studentDiningInfos,
                                     TableView tableView, String what_to_show) {
        if (studentDiningInfos.size() == 0) {
            return;
        }
        StudentDiningInfo sample = studentDiningInfos.get(0);
        ArrayList<MonthlyInfo> monthlyInfos = sample.getMonthlyInfos();
        tableView.getColumns().clear();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        int i = 0;
        tableView.setItems(studentDiningInfos);
        addIDColumn(tableView);
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
        addTotalColumn(what_to_show, tableView);
        setupTableRowMenus(tableView, what_to_show);
    }

    private void addIDColumn(TableView tableView) {
        TableColumn tableColumn = new TableColumn("STUDENT ID");
        tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentDiningInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentDiningInfo, String> cellDataFeatures) {
                return new SimpleStringProperty(String.valueOf(cellDataFeatures.getValue().getStudentID()));  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        tableView.getColumns().add(tableColumn);
    }

    private void addTotalColumn(final String type, TableView tableView) {
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

    private static void showYearMonthErrorDialog() {
        Dialogs.showErrorDialog(
                new Stage(),
                "Error in Query \n you should provide both End and Start Month",
                "Error",
                "Hall Management");
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
                        this.startMonth =  diningDatafromDatabase.startMonth;
                        this.startYear = diningDatafromDatabase.startYear;
                        this.endMonth = diningDatafromDatabase.endMonth;
                        this.endYear = diningDatafromDatabase.endYear;
                        String ids = studentID.getText();
                        if (ids != null) {
                            if (!ids.isEmpty()) {
                                String[] _ids = ids.split(CommonCharacters.COMMA);
                                if (_ids.length == 0) {
                                    diningDatafromDatabase.ids.add(ids);
                                } else {
                                    for (int i = 0; i < _ids.length; i++) {
                                        diningDatafromDatabase.ids.add(_ids[i]);
                                    }
                                }
                            }
                        }
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
                                    if (_deptID == -1) {
                                        Dialogs.showErrorDialog(
                                                new Stage(),
                                                "Please Check Department Names \n\n" +
                                                        "\'" + dept + "\' is not a valid Department Name",
                                                "Error in Query",
                                                "Hall Management"
                                        );
                                        return;
                                    }
                                    diningDatafromDatabase.batches.add(String.valueOf(_deptID));
                                } else {
                                    for (int i = 0; i < _departments.length; i++) {
                                        _deptID = DepartmentTable.getDeptID(_departments[i]);
                                        if (_deptID == -1) {
                                            Dialogs.showErrorDialog(
                                                    new Stage(),
                                                    "Please Check Department Names \n\n" +
                                                            "\'" + _departments[i] + "\' is not a valid  Name",
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
        if (list.size() == 0)   {
            Dialogs.showInformationDialog(
                    new Stage(),
                    "No data Found \n you might check your query",
                    "Warning",
                    "Hall Management");
            return;
        }
        populateDataInTable(list, amountTable, AMOUNT);
        populateDataInTable(list, fineTable, FINE);
        populateDataInTable(list, creditTable, CREDIT);
    }

    private void setInitialMonthYearValues()    {
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
    }
}
