package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
 * Time: 7:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddNewBatch {
    public TextField newBatchName;
    public TextField newBatchID;
    public Button addNewBatchButton;

    public void addNewBatchButtonClicked(ActionEvent actionEvent) {

        String batchName = newBatchName.getText();
        String batchID = newBatchID.getText();
        if (!batchName.isEmpty() && !batchID.isEmpty()) {
            int check = new DataBaseHelper(DataBaseConnection.getConnection()).insertIntoDataBase(
                    DataBaseConstant.BATCH_TABLE_NAME,
                    DataInputer.BatchInsert(batchID, batchName)
            );
            if (check > 0) {
                System.out.println("Batch Added " + batchID);
                Dialogs.showInformationDialog(
                        new Stage(),
                        batchName + "Has been added",
                        Config.SUCCESS_CONFIRMATION,
                        Config.APP_NAME
                );
                resetEverything();
            }  else if (check == -1)    {
                Dialogs.showErrorDialog(
                        new Stage(),
                        batchName + "Already Exists",
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

    public void resetEverything() {
        newBatchID.clear();
        newBatchName.clear();
    }
}
