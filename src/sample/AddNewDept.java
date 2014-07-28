package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
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
                System.out.println("Department Added " + newDptName);
            }
            resetEveryThing();
        } else {
            System.out.println("Please Fill All Fields");
        }
    }

    public void resetEveryThing() {
        deptID.clear();
        deptName.clear();
    }
}
