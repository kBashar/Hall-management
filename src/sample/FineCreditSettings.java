package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import laplab.hallmanagement.Month;
import laplab.hallmanagement.database.*;
import laplab.lib.databasehelper.DataBaseHelper;
import laplab.student.StudentInfo;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/2/14
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class FineCreditSettings implements Initializable {
    public ComboBox ffBatch;
    public ComboBox ffDept;
    public ComboBox ffMonth;
    public ComboBox frBatch;
    public ComboBox crBatch;

    public TextField frTaka;
    public TextField crMini;
    public TextField crMax;

    public Button ffUpdate;
    public Button frUpdate;
    public Button crUpdate;

    public CheckBox frCheckbox;
    public CheckBox crCheckbox;
    public TextField ffYear;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> deptList = BatchDeptList.getDepts();
        ObservableList<String> batchList = BatchDeptList.getBatches();
        ObservableList<String> monthList = Month.getMonthList();
        ffDept.setEditable(false);
        ffDept.autosize();
        ffDept.setItems(deptList);
        ffYear.setText(String.valueOf(Month.getCurrentYear()));

        ffBatch.setItems(batchList);
        frBatch.setItems(batchList);
        crBatch.setItems(batchList);

        ffMonth.setItems(monthList);
        ffMonth.setValue(monthList.get(Month.getCurrentMonth()));
    }

    public void loadBatches(ActionEvent actionEvent) {

    }

    public void loadDept(ActionEvent actionEvent) {

    }

    public void loadMonth(ActionEvent actionEvent) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void fineFreeUpdate(ActionEvent actionEvent) {
        String batch = (String) ffBatch.getValue();
        String dept = (String) ffDept.getValue();
        String month = (String) ffMonth.getValue();
        String year = ffYear.getText();

        if (batch != null && dept != null && month != null && year != null) {
            if (year.isEmpty() || batch.isEmpty() || dept.isEmpty() || month.isEmpty()) {
                System.out.println("Please fill ALL field");
                return;
            }
            HashMap map = DataInputer.FineInfoInsert(year,Month.getMonthIndex(month),batch, StudentInfo.getDepartmentID(dept));
            int check =  new DataBaseHelper(new DataBaseConnection().getConnection()).insertIntoDataBase(
                    DataBaseConstant.FINE_TABLE_NAME,
                    map);
            if (check>0)    {
                System.out.println("Fine info updated");
            }
            } else {
                System.out.println("please Specify everything");
                return;
            }
        }

    public void fineRuleUpdate(ActionEvent actionEvent) {
        String batch = (String) frBatch.getValue();
        String taka = frTaka.getText();
        if (batch != null && taka != null) {
            if (batch.isEmpty()) {
                System.out.println("Please Select Batch");
                return;
            }
            if (taka.isEmpty()) {
                System.out.println("Please Fill the money Field");
                return;
            }
            HashMap updatecolumns = new HashMap();
            updatecolumns.put(BatchTable.FINE_FOR_BATCH, taka);

            if (frCheckbox.isSelected()) {
                String tryStr = "where " + BatchTable.BATCH_ID_COLUMN + " >=" + " " + batch;
                int check = new DataBaseHelper(new DataBaseConnection().getConnection()).UpdateDataBase(
                        DataBaseConstant.BATCH_TABLE_NAME,
                        updatecolumns,
                        tryStr
                );
                if (check > 0) {
                    System.out.println("Fine Updated All " + batch);
                }
            } else {
                HashMap selectionargs = new HashMap();
                selectionargs.put(BatchTable.BATCH_NAME_COLUMN, batch);

                int check = new DataBaseHelper(new DataBaseConnection().getConnection()).UpdateDataBase(
                        DataBaseConstant.BATCH_TABLE_NAME,
                        updatecolumns,
                        selectionargs
                );
                if (check > 0) {
                    System.out.println("Fine Updated " + taka);
                }
            }
            ResetEveryThingFR();
        }

    }

    public void creditRulwUpdate(ActionEvent actionEvent) {
        String batch = (String) crBatch.getValue();
        String minDay = crMini.getText();
        String maxDay = crMax.getText();
        if (batch != null && minDay != null && maxDay != null) {
            if (batch.isEmpty()) {
                System.out.println("Please Select Batch");
                return;
            }
            if (minDay.isEmpty()) {
                System.out.println("Please Fill the Minimum Day Field");
                return;
            }
            if (maxDay.isEmpty()) {
                System.out.println("Please Fill the Maximum Day Field");
                return;
            }
            HashMap updatecolumns = new HashMap();
            updatecolumns.put(BatchTable.MIN_DAY_FOR_CREDIT, minDay);
            updatecolumns.put(BatchTable.MAX_DAY_FOR_CREDIT, maxDay);
            if (crCheckbox.isSelected()) {
                String tryStr = "where " + BatchTable.BATCH_ID_COLUMN + " >=" + " " + batch;
                int check = new DataBaseHelper(new DataBaseConnection().getConnection()).UpdateDataBase(
                        DataBaseConstant.BATCH_TABLE_NAME,
                        updatecolumns,
                        tryStr
                );
                if (check > 0) {
                    System.out.println("Credit Updated All " + minDay + " " + maxDay);
                }
            } else {
                HashMap tryStr = new HashMap();
                tryStr.put(BatchTable.BATCH_NAME_COLUMN, batch);
                int check = new DataBaseHelper(new DataBaseConnection().getConnection()).UpdateDataBase(
                        DataBaseConstant.BATCH_TABLE_NAME,
                        updatecolumns,
                        tryStr
                );
                if (check > 0) {
                    System.out.println("Credit Updated " + minDay + " " + maxDay);
                }

            }
            ResetEveryThingCR();

        }
    }

    private void ResetEveryThingFR() {
        frBatch.setValue(null);
        frCheckbox.setSelected(false);
        frTaka.clear();
    }

    private void ResetEveryThingCR() {
        crBatch.setValue(null);
        crCheckbox.setSelected(false);
        crMini.clear();
        crMax.clear();
    }
}
