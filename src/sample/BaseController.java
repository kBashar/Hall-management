package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 8/8/14
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseController implements Initializable {

    public Button studentButton;
    public Button finecreditsButton;
    public Button dataInputButton;
    @FXML
    private AnchorPane anchorPan;
    private AnchorPane a;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Image studentImage = new Image(getClass().getResourceAsStream("/sample/start_students.png"));
        Image dataInputImage = new Image(getClass().getResourceAsStream("/sample/dinput.png"));
        Image amountImage = new Image(getClass().getResourceAsStream("/sample/money-logo.jpg"));
        studentButton.setGraphic(new ImageView(studentImage));
        finecreditsButton.setGraphic(new ImageView(amountImage));
        dataInputButton.setGraphic(new ImageView(dataInputImage));*/
        studentButton.setTooltip(new Tooltip("STUDENT INFORMATION"));
        finecreditsButton.setTooltip(new Tooltip("FINE & CREDIT"));
        dataInputButton.setTooltip(new Tooltip("INPUT AREA"));
        Node r = null;
        try {
            r = (Node) FXMLLoader.load(getClass().getResource("welcome_screen.fxml"));
        } catch (IOException e) {
            MakeLogger.printToLogger(getClass().toString(),e.toString());  //To change body of catch statement use File | Settings | File Templates.
        }
        a = (AnchorPane) r;
        a.autosize();
        //a.resize(1000,1000);
        //a.autosize();
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        anchorPan.getChildren().clear();
        anchorPan.getChildren().add(a);
    }


    public void addNewbatchMenuClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddNewBatch.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add New batch");
        stage.show();
    }

    public void addNewDeptMenuClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddNewDept.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add New Department");
        stage.show();
    }

    public void studentButtonClicked(ActionEvent actionEvent) {
        Node r = null;
        try {
            r = (Node) FXMLLoader.load(getClass().getResource("/sample/students.fxml"));
        } catch (IOException e) {
            MakeLogger.printToLogger(getClass().toString(),e.toString());  //To change body of catch statement use File | Settings | File Templates.
        }
        a = (AnchorPane) r;
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        anchorPan.getChildren().clear();
        anchorPan.getChildren().add(a);
    }

    public void finecreditsButtonClicked(ActionEvent actionEvent) {
        Node r = null;
        try {
            r = (Node) FXMLLoader.load(getClass().getResource("fine_credits.fxml"));
        } catch (IOException e) {
            MakeLogger.printToLogger(getClass().toString(),e.toString());  //To change body of catch statement use File | Settings | File Templates.
        }
        a = (AnchorPane) r;
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        anchorPan.getChildren().clear();
        anchorPan.getChildren().add(a);
    }

    public void dataInputButtonClicked(ActionEvent actionEvent) {
        String e = actionEvent.toString();

        System.out.println(e);
        Node r = null;
        try {
            r = (Node) FXMLLoader.load(getClass().getResource("data_input_v2.fxml"));
        } catch (IOException e1) {
            MakeLogger.printToLogger(getClass().toString(),e1.toString());  //To change body of catch statement use File | Settings | File Templates.
        }
        a = (AnchorPane) r;
        //a.resize(1000,1000);
        //a.autosize();
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        anchorPan.getChildren().clear();
        anchorPan.getChildren().add(a);
    }

    public void addNewStudentCliked(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/StudentDataEntry.fxml"));
        } catch (IOException e) {
            MakeLogger.printToLogger(getClass().toString(),e.toString());  //To change body of catch statement use File | Settings | File Templates.
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("ADD NEW");
        stage.show();
    }

    public void editClicked(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Edit.fxml"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("EDIT");
        stage.show();
    }

    public void deleteClicked(ActionEvent actionEvent) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void finecreditSettingsClicked(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("FineCreditSettings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Fine Credit Settings");
        stage.show();
    }

    public void aboutClicked(ActionEvent actionEvent) {
        new About().show();
    }
}
