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
import laplab.hallmanagement.database.MonthTable;
import laplab.hallmanagement.dataimport.StudentDataImport;
import laplab.lib.databasehelper.DataBaseHelper;
import laplab.student.StudentInfo;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class Main extends Application {
    private static final Logger logger= Logger.getLogger(Main.class.getName());
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("check.fxml"));
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

    public static void main(String[] args) {
        setUpLogger();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        if (!dataBaseConnection.checkForDatabase()) {
            System.out.println("Can't Find or create Database");
            logger.severe("Can't Found or create Database");
            return;
        }
        // to update database to include new month. if a new month starts it'll
        // update the database
        MonthTable.updateNewMonth();
        logger.log(Level.INFO,"Here in brasil");
        launch(args);
    }

    /*
    * it's temporary method for Data entry from excel file. in final implementation
    * it must be deleted
    */
    private static void TempoDataEntry() {
        laplab.student.StudentInfoList studentInfoList = new StudentDataImport(new File("E:\\Hall Management\\seatallotment.xlsx")).startImporting();
        if (!studentInfoList.isEmpty()) {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(DataBaseConnection.getConnection());
            for (StudentInfo studentInfo : studentInfoList) {
                dataBaseHelper.insertIntoDataBase(DataBaseConstant.STUDENT_INFO_TABLE_NAME, DataInputer.StudentInsert(studentInfo));
            }
        }
    }

    private static void setUpLogger() {
        Logger logger = Logger.getLogger("");

        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        try {
            Handler handler = new FileHandler("try-logger.%u.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}