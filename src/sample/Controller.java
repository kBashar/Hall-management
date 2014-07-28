package sample;

import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.StudentInfoTable;
import laplab.lib.databasehelper.QueryHelper;
import laplab.lib.tablecreator.CommonCharacters;
import laplab.student.StudentInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public TextField batchField;
    public TextField deptField;
    public TextField roomField;
    public TextField studentField;
    public TableView table;


    public TableColumn roomColumn;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn fineColumn;
    public TableColumn creditColumn;


    private static ObservableList<StudentInfo> studentInfoObservableList;
    public TableView tableView;

    public void handleButtonAction(ActionEvent actionEvent) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void checkButtonClicked(ActionEvent actionEvent) {

        StringBuilder stringBuilder = new StringBuilder();
        String querryItem = new String();
        querryItem = deptField.getText();
        boolean flag = false;
        if (querryItem == null || !querryItem.isEmpty()) {
            String[] departments = querryItem.split(",");
            if (departments.length == 0) {
                departments[0] = querryItem;
            }

            stringBuilder.append(buildWhereClause(StudentInfoTable.DEPARTMENT_COLUMN, departments));
            flag = true;
        }
        querryItem = batchField.getText();
        if (querryItem == null || !querryItem.isEmpty()) {
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

        }

        querryItem = roomField.getText();
        if (querryItem == null || !querryItem.isEmpty()) {
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

        }

        querryItem = studentField.getText();
        if (querryItem == null || !querryItem.isEmpty()) {
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

        }

        QueryHelper queryHelper = new QueryHelper(DataBaseConnection.getConnection());

        studentInfoObservableList = new GetDataFromDatabase().printData(queryHelper.query(DataBaseConstant.STUDENT_INFO_TABLE_NAME, stringBuilder.toString()));
        tableView.setItems(studentInfoObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentInfoObservableList = new GetDataFromDatabase().printData();

        setupTable();
        tableView.setItems(studentInfoObservableList);
    }

    public void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<StudentInfo, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
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
}
