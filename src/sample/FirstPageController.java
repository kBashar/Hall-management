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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.hallmanagement.database.DataBaseConstant;
import laplab.hallmanagement.database.DepartmentTable;
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
    public TextField nameLabel;
    public TextField idLabel;
    public TextField batchLabel;
    public TextField deptLabel;
    public TextField roomLabel;
    public TableColumn contactColumn;
    public TableView tableView;


    public void findClicked(ActionEvent actionEvent) {


        StringBuilder stringBuilder = new StringBuilder();
        String querryItem = new String();
        String deptFieldText = deptField.getText();
        boolean flag = false;
        if (deptFieldText != null) {
            if (!deptFieldText.isEmpty()) {
                // in STUDENTINFO Table department is saved as ID, But to provide
                // better user-experience we are parsing dept. name to produce dept.
                // id for user
                String[] _departments = deptFieldText.split(",");
                // if there is only one department then that is added in array
                if (_departments.length == 0) {
                    int _depID = DepartmentTable.getDeptID(querryItem);
                    if (_depID == -1) {
                        Dialogs.showErrorDialog(
                                new Stage(),
                                "Please Check Department Names \n\n" +
                                        "\'" + deptFieldText + "\' is not a valid Department Name",
                                "Error in Query",
                                "Hall Management"
                        );
                        return;
                    }
                    _departments[0] = String.valueOf(_depID);
                } else {
                    for (int i = 0; i < _departments.length; i++) {
                        int _deptID = DepartmentTable.getDeptID(_departments[i]);
                        if (_deptID == -1) {
                            Dialogs.showErrorDialog(
                                    new Stage(),
                                    "Please Check Department Names \n\n" +
                                            "\'" + _departments[i] + "\' is not a valid Department Name",
                                    "Error in Query",
                                    "Hall Management"
                            );
                            return;
                        }
                        _departments[i] = String.valueOf(_deptID);
                    }
                    stringBuilder.append(buildWhereClause(StudentInfoTable.DEPARTMENT_COLUMN, _departments));
                    flag = true;
                }
            }
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
        tableView.setItems(studentInfoObservableList);
        if (studentInfoObservableList.size() == 0)  {
            Dialogs.showInformationDialog(
                    new Stage(),
                    "Please Check Query",
                    "No Student Found",
                    "Hall Management");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentInfoObservableList = new GetDataFromDatabase().printData();

        setupTable();
        tableView.setItems(studentInfoObservableList);
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
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
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

    public void findButtonClicked(ActionEvent actionEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        String querryItem = new String();
        String deptFieldText = deptField.getText();
        boolean flag = false;
        if (deptFieldText != null) {
            if (!deptFieldText.isEmpty()) {
                // in STUDENTINFO Table department is saved as ID, But to provide
                // better user-experience we are parsing dept. name to produce dept.
                // id for user
                String[] _departments = deptFieldText.split(",");
                // if there is only one department then that is added in array
                if (_departments.length == 0) {
                    int _depID = DepartmentTable.getDeptID(querryItem);
                    if (_depID == -1) {
                        Dialogs.showErrorDialog(
                                new Stage(),
                                "Please Check Department Names \n\n" +
                                        "\'" + deptFieldText + "\' is not a valid Department Name",
                                "Error in Query",
                                "Hall Management"
                        );
                        return;
                    }
                    _departments[0] = String.valueOf(_depID);
                } else {
                    for (int i = 0; i < _departments.length; i++) {
                        int _deptID = DepartmentTable.getDeptID(_departments[i]);
                        if (_deptID == -1) {
                            Dialogs.showErrorDialog(
                                    new Stage(),
                                    "Please Check Department Names \n\n" +
                                            "\'" + _departments[i] + "\' is not a valid Department Name",
                                    "Error in Query",
                                    "Hall Management"
                            );
                            return;
                        }
                        _departments[i] = String.valueOf(_deptID);
                    }
                    stringBuilder.append(buildWhereClause(StudentInfoTable.DEPARTMENT_COLUMN, _departments));
                    flag = true;
                }
            }
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
        tableView.setItems(studentInfoObservableList);
        if (studentInfoObservableList.size() == 0)  {
            Dialogs.showInformationDialog(
                    new Stage(),
                    "Please Check Query",
                    "No Student Found",
                    "Hall Management");
        }
    }
}
