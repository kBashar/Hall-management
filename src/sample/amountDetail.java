package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 8/10/14
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class AmountDetail {
    @FXML
    public TableColumn monthColumn;
    public TableColumn voucherColumn;
    public TableColumn amountColumn;
    public Label idlabel;
    public Label nameLabel;

    private Parent parent;
    private Scene scene;
    private Stage stage;

    public AmountDetail()   {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/amountclicked.fxml"));
        fxmlLoader.setController(this);

        try {
            parent = (Parent) fxmlLoader.load();
            scene  = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void show(String id)  {
        idlabel.setText(id);
        stage = new Stage();
        stage.setTitle("Amount Detail Info");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
