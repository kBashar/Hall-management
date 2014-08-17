package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laplab.hallmanagement.Config;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.DataInputer;
import laplab.lib.databasehelper.DataBaseHelper;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/2/14
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddNewDept {
    public TextField deptName;
    public TextField deptID;

    public void addnewDeptButtonClicked(ActionEvent actionEvent) {
        String newDptName = deptName.getText();
        String newDptID = deptID.getText();
        if (!newDptName.isEmpty() && !newDptID.isEmpty()) {
            int check = new DataBaseHelper(DataBaseConnection.getConnection()).insertIntoDataBase(
                    DataBaseConstant.DEPARTMENT_TABLE_NAME,
                    DataInputer.DepartmentInsert(newDptID, newDptName)
            );
            if (check > 0) {
                Dialogs.showInformationDialog(
                        new Stage(),
                        deptName + "Has been added",
                        Config.SUCCESS_CONFIRMATION,
                        Config.APP_NAME
                );
                resetEveryThing();
            }  else if (check == -1)    {
                Dialogs.showErrorDialog(
                        new Stage(),
                        deptName + "Already Exists",
                        Config.INPUT_WRONG,
                        Config.APP_NAME
                );
            }
        } else {
            Dialogs.showWarningDialog(
                    new Stage(),
                    "Please fill all the field",
                    Config.INPUT_WRONG,
                    Config.APP_NAME
            );
        }
    }

    public void resetEveryThing() {
        deptID.clear();
        deptName.clear();
    }
}
