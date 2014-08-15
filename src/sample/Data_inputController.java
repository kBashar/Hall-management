/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laplab.hallmanagement.Config;
import laplab.hallmanagement.Credit.CreditUpdater;
import laplab.hallmanagement.Month;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.DataInputer;
import laplab.lib.databasehelper.DataBaseHelper;

/**
 * FXML Controller class
 *
 * @author Khyrul Bashar
 */
public class Data_inputController implements Initializable {

    public ComboBox dinningBillMonth;
    public TextField dinningBillStudentID;
    public TextField dinningBillVoucher;
    public TextField dinningBillAmount;
    public TextField dinningBillYear;
    public TextField creditStudentID;
    public TextField creditDay;
    public TextField creditYear;
    public ComboBox creditMonth;
    ObservableList<String> monthList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthList = Month.getMonthList();
        dinningBillMonth.setItems(monthList);
        dinningBillMonth.setValue(monthList.get(Month.getCurrentMonth()));
        dinningBillYear.setText(String.valueOf(Month.getCurrentYear()));
        creditMonth.setItems(monthList);
        creditMonth.setValue(monthList.get(Month.getCurrentMonth()));
        creditYear.setText(String.valueOf(Month.getCurrentYear()));

    }

    public void inputButtonClicked(ActionEvent actionEvent) {
        String studentIDs = dinningBillStudentID.getText();
        String vouchers = dinningBillVoucher.getText();
        String amounts = dinningBillAmount.getText();
        String year = dinningBillYear.getText();
        String month = (String) dinningBillMonth.getValue();

        if (studentIDs != null && vouchers != null &&
                amounts != null && year != null && month != null) {
            if (!studentIDs.isEmpty() && !vouchers.isEmpty() &&
                    !amounts.isEmpty() && !year.isEmpty() && !month.isEmpty()) {
                String[] studentIdList = studentIDs.split(",");
                String[] voucherList = vouchers.split(",");
                String[] amountList = amounts.split(",");


                if (studentIdList.length == voucherList.length &&
                        (studentIdList.length == amountList.length || amountList.length == 1)) {
                    if (amountList.length == 1) {
                        String amount = amountList[0];
                        amountList = new String[studentIdList.length];
                        Arrays.fill(amountList, amount);
                    }
                    DataBaseHelper helper = new DataBaseHelper(DataBaseConnection.getConnection());
                    HashMap map;
                    for (int i = 0; i < studentIdList.length; i++) {
                        map = DataInputer.DinningInfoInsert(
                                studentIdList[i],
                                voucherList[i],
                                amountList[i],
                                Month.getMonthID(Integer.parseInt(year),
                                        Month.getMonthIndex(month)));
                        int check = helper.insertIntoDataBase(DataBaseConstant.DINING_INFO_TABLE_NAME, map);
                        if (check > 0) {
                            Dialogs.showInformationDialog(
                                    new Stage(),
                                    "Dining info input done",
                                    Config.SUCCESS_CONFIRMATION,
                                    Config.APP_NAME
                            );
                            resetDinningFields();
                        }
                    }

                } else {
                    Dialogs.showErrorDialog(
                            new Stage(),
                            "You should Enter Voucher_ID and Amount Per Student",
                            Config.INPUT_WRONG,
                            Config.APP_NAME
                    );
                    return;
                }

            } else {
                Dialogs.showErrorDialog(
                        new Stage(),
                        "Please fill all the field",
                        Config.INPUT_WRONG,
                        Config.APP_NAME
                );
                return;
            }

        } else {

        }
    }

    private void resetDinningFields() {
        dinningBillMonth.setValue(monthList.get(Month.getCurrentMonth()));
        dinningBillYear.setText(String.valueOf(Month.getCurrentYear()));
        dinningBillStudentID.clear();
        dinningBillVoucher.clear();
        dinningBillAmount.clear();
    }

    public void creditInputButtonClicked() {
        String studentId = creditStudentID.getText();
        String creditday = creditDay.getText();
        String year = creditYear.getText();
        String month = (String) creditMonth.getValue();

        if (studentId != null && creditday != null && year != null && month != null) {
            int check = CreditUpdater.updateCreditDayCount(studentId,
                    String.valueOf(Month.getMonthID(Integer.parseInt(year), Month.getMonthIndex(month))),
                    Integer.parseInt(creditday));
            if (check > 0) {
                Dialogs.showInformationDialog(
                        new Stage(),
                        "Credit info input done",
                        Config.SUCCESS_CONFIRMATION,
                        Config.APP_NAME
                );
                resetCreditFields();
            } else if (check == -2) {
                Dialogs.showErrorDialog(
                        new Stage(),
                        "this Student yet didn't pay for Dining",
                        Config.INPUT_WRONG,
                        Config.APP_NAME
                );
            } else {
                Dialogs.showErrorDialog(
                        new Stage(),
                        "Something went wrong\n Report Developer",
                        Config.UNKNOWN_WRONG,
                        Config.APP_NAME
                );
            }

        }
    }

    private void resetCreditFields() {
        creditStudentID.clear();
        creditDay.clear();
        creditMonth.setValue(monthList.get(Month.getCurrentMonth()));
        creditYear.setText(String.valueOf(Month.getCurrentYear()));
    }

}
