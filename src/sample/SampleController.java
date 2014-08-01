/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Khyrul Bashar
 */
public class SampleController implements Initializable {

    @FXML
    private AnchorPane acc;

    @FXML
    private Button b;
    private AnchorPane a;

    @FXML
    public void handleButtonActionStudent(ActionEvent event) throws IOException {
        String e = event.toString();

        System.out.println(e);
        Node r = (Node) FXMLLoader.load(getClass().getResource("First_page.fxml"));
        a = (AnchorPane) r;
        //a.resize(1000,1000);
        //a.autosize();
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        acc.getChildren().clear();
        acc.getChildren().add(a);
    }

    @FXML
    public void handleButtonActionFine(ActionEvent event) throws IOException {
        String e = event.toString();

        System.out.println(e);
        Node r = (Node) FXMLLoader.load(getClass().getResource("Fine_credit.fxml"));
        a = (AnchorPane) r;
        //a.resize(1000,1000);
        //a.autosize();
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        acc.getChildren().clear();
        acc.getChildren().add(a);
    }

    @FXML
    public void handleButtonActionDinningDataEntry(ActionEvent event) throws IOException {
        String e = event.toString();

        System.out.println(e);
        Node r = (Node) FXMLLoader.load(getClass().getResource("Data_input.fxml"));
        a = (AnchorPane) r;
        //a.resize(1000,1000);
        //a.autosize();
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        acc.getChildren().clear();
        acc.getChildren().add(a);
    }

    @FXML
    public void handelMenuActionAddnew(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StudentDataEntry.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("ADD NEW");
        stage.show();
    }

    @FXML
    public void handelMenuActionEdit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Edit.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("EDIT");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Node r = null;
        try {
            r = (Node) FXMLLoader.load(getClass().getResource("First_page.fxml"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        a = (AnchorPane) r;
        //a.resize(1000,1000);
        //a.autosize();
        AnchorPane.setTopAnchor(a, 0.0);
        AnchorPane.setBottomAnchor(a, 0.0);
        AnchorPane.setLeftAnchor(a, 0.0);
        AnchorPane.setRightAnchor(a, 0.0);
        acc.getChildren().clear();
        acc.getChildren().add(a);
    }

    public void fineCrediteSettingClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FineCreditSettings.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Fine Credit Settings");
        stage.show();
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
}