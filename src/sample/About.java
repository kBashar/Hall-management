package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 8/21/14
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class About {
    private Parent parent;
    private Scene scene;
    private Stage stage;

    public About()  {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/about.fxml"));
        fxmlLoader.setController(this);

        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void show()  {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("About");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
