/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.DataInputer;
import laplab.hallmanagement.dataimport.StudentDataImport;
import laplab.lib.databasehelper.DataBaseHelper;
import laplab.student.StudentInfo;

import java.io.File;

/**
 * @author AURANGO SABBIR
 */
public class NorthHall extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(0);
        stage.setY(0);
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //NorthHall.TempoDataEntry();
        launch(args);
    }

    private static void TempoDataEntry()    {
        laplab.student.StudentInfoList studentInfoList=new StudentDataImport(new File("E:\\Hall Management\\seatallotment.xlsx")).startImporting();
        if (!studentInfoList.isEmpty()){
            DataBaseHelper dataBaseHelper=new DataBaseHelper(new DataBaseConnection().getConnection());
            for (StudentInfo studentInfo:studentInfoList)   {
                 dataBaseHelper.insertIntoDataBase(DataBaseConstant.STUDENT_INFO_TABLE_NAME, DataInputer.StudentInsert(studentInfo));
            }
        }
    }
}