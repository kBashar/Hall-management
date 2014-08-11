package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.StudentInfoTable;
import laplab.lib.databasehelper.QueryHelper;
import laplab.student.StudentInfo;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 8/10/14
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class DescriptionController implements Initializable {
    public TextField id;
    public TextField name;
    public TextField room;
    public TextField contact;
    public TextField parent_contact;
    public TextField blood_group;
    public TextField imageUrl;
    public TextField studentParent;

    private Parent parent;
    private Scene scene;
    private Stage stage;

    StudentInfo student;

    public DescriptionController() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/description.fxml"));
        fxmlLoader.setController(this);

        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void show(StudentInfo _student) {
        student = _student;
        id.setText(String.valueOf(student.getId()));
        room.setText(String.valueOf(student.getRoom()));
        name.setText(student.getName());
        setText(student.getContact(), contact);

        stage = new Stage();
        stage.setTitle("Student Detail Info");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

        QueryHelper queryHelper = new QueryHelper(DataBaseConnection.getConnection());
        ResultSet resultSet = queryHelper.queryInDataBase("select PARENT_NAME,PARENT_CONTACT,BLOOD_GROUP from studentinfo where id =" + student.getId());
        try {
            while (resultSet.next()) {
                String _parentContact = resultSet.getString(StudentInfoTable.PARENT_CONTACT_COLUMN);
                setText(_parentContact, parent_contact);
                String _bloodGroup = resultSet.getString(StudentInfoTable.BLOOD_GROUP_COLUMN);
                setText(_bloodGroup, blood_group);
                String _parentName = resultSet.getString(StudentInfoTable.PARENT_NAME_COLUMN);
                setText(_parentName, studentParent);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void saveButttonClicked(ActionEvent actionEvent) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void setText(String text, TextField textField) {
        if (text != null) {
            if (!text.isEmpty()) {
                textField.setText(text);
            } else {
                textField.setText("Not Available");
            }
        } else {
            textField.setText("Not Available");
        }
    }
}
