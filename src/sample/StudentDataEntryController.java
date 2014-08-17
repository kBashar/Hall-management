/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.net.URL;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laplab.hallmanagement.Config;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.DataInputer;
import laplab.lib.databasehelper.DataBaseHelper;
import laplab.student.StudentInfo;

/**
 * FXML Controller class
 *
 * @author AURANGO SABBIR
 */
public class StudentDataEntryController implements Initializable {

    public TextField id;
    public TextField name;
    public TextField room;
    public TextField contact;
    public TextField parent;
    public TextField parent_contact;
    public TextField blood_group;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    public void saveButttonClicked(ActionEvent actionEvent) {
        StudentInfo studentInfo = new StudentInfo();

        String data = id.getText();
        if (data == null || data.isEmpty()) {
            askForInput();
            return;
        }
        studentInfo.setId(Integer.parseInt(data));
        data = name.getText();
        if (data == null || data.isEmpty()) {
            askForInput();
            return;
        }
        studentInfo.setName(data);

        data = room.getText();
        if (data == null || data.isEmpty()) {
            askForInput();
            return;
        }
        studentInfo.setRoom(Integer.parseInt(data));

        data = contact.getText();
        if (data == null || data.isEmpty()) {
            askForInput();
            return;
        }
        studentInfo.setContact(data);

        studentInfo.setParent_name(parent.getText());
        studentInfo.setParent_contact(parent_contact.getText());
        studentInfo.setBlood_group(blood_group.getText());

        if (studentInfo.finalizeObject()) {


            int check = new DataBaseHelper(DataBaseConnection.getConnection()).insertIntoDataBase(
                    DataBaseConstant.STUDENT_INFO_TABLE_NAME,
                    DataInputer.StudentInsert(studentInfo)
            );
            if (check > 0) {
                Dialogs.showInformationDialog(
                        new Stage(),
                        id.getText() + " Has been added",
                        Config.SUCCESS_CONFIRMATION,
                        Config.APP_NAME
                );
                resetEverything();
            } else if (check == -1) {
                Dialogs.showErrorDialog(
                        new Stage(),
                        id.getText() + " Already Exists\n",
                        Config.INPUT_WRONG,
                        Config.APP_NAME
                );
            } else if (check == -2) {
                Dialogs.showErrorDialog(
                        new Stage(),
                        "Batch and/or Department doesn't Exist of this Student\n" +
                                "Please add batch,department first",
                        Config.INPUT_WRONG,
                        Config.APP_NAME
                );
            }

        }
    }

    private void resetEverything() {
        id.clear();
        name.clear();
        room.clear();
        contact.clear();
        parent.clear();
        parent_contact.clear();
        blood_group.clear();
    }

    private void askForInput() {
        Dialogs.showWarningDialog(
                new Stage(),
                "ID,Name,Room,Contact must be filled",
                Config.INPUT_WRONG,
                Config.APP_NAME
        );
    }

    public void exitButtonClicked(ActionEvent actionEvent) {


    }
}
