/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.StudentInfoTable;
import laplab.lib.databasehelper.QueryHelper;
import laplab.lib.tablecreator.CommonCharacters;
import laplab.student.StudentInfo;

public class FirstPageController implements Initializable {

    private static final Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public TextField batchField;
    public TextField deptField;
    public TextField roomField;
    public TextField studentField;


    public TableColumn roomColumn;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn fineColumn;
    public TableColumn creditColumn;


    private static ObservableList<StudentInfo> studentInfoObservableList;
    public TableView tabelView;
    public TextField nameLabel;
    public TextField idLabel;
    public TextField batchLabel;
    public TextField deptLabel;
    public TextField roomLabel;
    public TableColumn contactColumn;


    public void findClicked(ActionEvent actionEvent) {


        StringBuilder stringBuilder = new StringBuilder();
        String querryItem = new String();
        querryItem = deptField.getText();
        boolean flag = false;
        if (!querryItem.isEmpty()) {
            String[] departments = querryItem.split(",");
            if (departments.length == 0) {
                departments[0] = querryItem;
            }

            stringBuilder.append(buildWhereClause(StudentInfoTable.DEPARTMENT_COLUMN, departments));
            flag = true;
        }
        querryItem = batchField.getText();
        if (!querryItem.isEmpty()) {
            String[] batches = querryItem.split(",");
            if (batches.length == 0) {
                batches[0] = querryItem;
            }
            if (flag) {
                stringBuilder.append(CommonCharacters.SPACE);
                stringBuilder.append(CommonCharacters.AND);
                stringBuilder.append(CommonCharacters.SPACE);
            }

            stringBuilder.append(buildWhereClause(StudentInfoTable.BATCH_COLUMN, batches));
            flag = true;
        }

        querryItem = roomField.getText();
        if (!querryItem.isEmpty()) {
            String[] rooms = querryItem.split(",");
            if (rooms.length == 0) {
                rooms[0] = querryItem;
            }
            if (flag) {
                stringBuilder.append(CommonCharacters.SPACE);
                stringBuilder.append(CommonCharacters.AND);
                stringBuilder.append(CommonCharacters.SPACE);
            }
            stringBuilder.append(buildWhereClause(StudentInfoTable.ROOM_COLUMN, rooms));
            flag = true;
        }

        querryItem = studentField.getText();
        if (!querryItem.isEmpty()) {
            String[] ids = querryItem.split(",");
            if (ids.length == 0) {
                ids[0] = querryItem;
            }
            if (flag) {
                stringBuilder.append(CommonCharacters.SPACE);
                stringBuilder.append(CommonCharacters.AND);
                stringBuilder.append(CommonCharacters.SPACE);
            }

            stringBuilder.append(buildWhereClause(StudentInfoTable.ID_COLUMN, ids));
            flag = true;
        }

        QueryHelper queryHelper = new QueryHelper(DataBaseConnection.getConnection());
        if (!flag) {
            studentInfoObservableList = new GetDataFromDatabase().printData(queryHelper.query(DataBaseConstant.STUDENT_INFO_TABLE_NAME));

        } else {
            studentInfoObservableList = new GetDataFromDatabase().printData(queryHelper.query(DataBaseConstant.STUDENT_INFO_TABLE_NAME, stringBuilder.toString()));
        }
        tabelView.setItems(studentInfoObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentInfoObservableList = new GetDataFromDatabase().printData();

        setupTable();
        tabelView.setItems(studentInfoObservableList);
        tabelView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {

                StudentInfo studentInfo = (StudentInfo) o2;
                nameLabel.setText(studentInfo.getName());
                if (studentInfo.getBatch() < 10) {
                    idLabel.setText("0" + String.valueOf(studentInfo.getId()));
                    batchLabel.setText("0" + String.valueOf(studentInfo.getBatch()));
                } else {
                    idLabel.setText(String.valueOf(studentInfo.getId()));

                    batchLabel.setText(String.valueOf(studentInfo.getBatch()));
                }

                roomLabel.setText(String.valueOf(studentInfo.getRoom()));

                deptLabel.setText(String.valueOf(studentInfo.getDepartment()));

                nameLabel.setEditable(false);
                idLabel.setEditable(false);
                roomLabel.setEditable(false);
            }
        });
    }

    public void setupTable() {
        tabelView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        idColumn.setCellValueFactory(new PropertyValueFactory<StudentInfo, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        String st = (String) contactColumn.getCellData(4);
        if (st == null) ;
        contactColumn.setUserData("Not Available");
    }

    private String buildWhereClause(String columnName, String[] selectionargs) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CommonCharacters.FIRSTBRACES);
        stringBuilder.append(CommonCharacters.SPACE);
        for (String s : selectionargs) {
            stringBuilder.append(columnName);
            stringBuilder.append(CommonCharacters.EQUAL_SIGN);
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append(CommonCharacters.SINGLE_COUAT);
            stringBuilder.append(s);
            stringBuilder.append(CommonCharacters.SINGLE_COUAT);
            stringBuilder.append(CommonCharacters.SPACE);
            stringBuilder.append(CommonCharacters.OR_SIGN);
            stringBuilder.append(CommonCharacters.SPACE);
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("r"));
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("o"));
        stringBuilder.append(CommonCharacters.SECONDBRACES);
        stringBuilder.append(CommonCharacters.SPACE);
        return stringBuilder.toString();
    }


    public void editButtonClicked(ActionEvent actionEvent) {
        nameLabel.setEditable(true);
        idLabel.setEditable(true);
        roomLabel.setEditable(true);
    }
}
