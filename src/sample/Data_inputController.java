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
import javafx.scene.control.TextField;
import laplab.hallmanagement.Month;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.DataInputer;
import laplab.lib.databasehelper.DataBaseHelper;

/**
 * FXML Controller class
 *
 * @author AURANGO SABBIR
 */
public class Data_inputController implements Initializable {

    public ComboBox dinningBillMonth;
    public TextField dinningBillStudentID;
    public TextField dinningBillVoucher;
    public TextField dinningBillAmount;
    public TextField dinningBillYear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> monthList = Month.getMonthList();
        dinningBillMonth.setItems(monthList);
        dinningBillMonth.setValue(monthList.get(Month.getCurrentMonth()));
        dinningBillYear.setText(String.valueOf(Month.getCurrentYear()));

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
                    DataBaseHelper helper = new DataBaseHelper(new DataBaseConnection().getConnection());
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
                            System.out.println("Dining info input done");
                        }
                    }

                } else {
                    System.out.println("You should Enter Voucher_ID and Amount Per Student");
                }

            } else {
                System.out.println("Please fill all the Field");
                return;
            }

        } else {

        }
    }
}
