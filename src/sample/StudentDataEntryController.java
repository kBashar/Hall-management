/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laplab.hallmanagement.Config;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.DataInputer;
import laplab.lib.databasehelper.DataBaseHelper;
import laplab.student.StudentInfo;
import org.hsqldb.lib.FileUtil;

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
    public TextField browse_image;

    public Button image_browse;
    private StudentInfo studentInfo;
    File file_source;
    File file_distination;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    public void saveButttonClicked(ActionEvent actionEvent) throws IOException {
        studentInfo = new StudentInfo();

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
                    copyFile(file_source, file_distination);
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
        browse_image.clear();
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

    public void image_browseButtonClicked(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        file_source = fileChooser.showOpenDialog(null);
        System.out.println(file_source.toString());
            file_distination=new File("image");
            if(!file_distination.exists())
                new File("image").mkdir();
                file_distination = new File("image/" + id.getText() + ".jpg");
                browse_image.setText(file_source.toURI().toString());
                System.out.println(file_distination.toURI().toString());

    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new RandomAccessFile(sourceFile,"rw").getChannel();
            destination = new RandomAccessFile(destFile,"rw").getChannel();

            long position = 0;
            long count    = source.size();

            source.transferTo(position, count, destination);
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }
}
