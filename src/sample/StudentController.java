package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import laplab.hallmanagement.database.*;
import laplab.lib.databasehelper.DataBaseHelper;
import laplab.lib.databasehelper.QueryHelper;
import laplab.lib.tablecreator.CommonCharacters;
import laplab.student.StudentInfo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 8/8/14
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentController implements Initializable {

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
    public TableColumn contactColumn;
    public TableView tableView;

    private static ObservableList<StudentInfo> studentInfoObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentInfoObservableList = new GetDataFromDatabase().printData();

        setupTable();
        tableView.setItems(studentInfoObservableList);
    }

    public void setupTable() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tableView.setRowFactory(
                new Callback<TableView<StudentInfo>, TableRow<StudentInfo>>() {
                    @Override
                    public TableRow<StudentInfo> call(TableView<StudentInfo> tableView) {
                        final TableRow<StudentInfo> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        StudentInfo studentInfo;
                        MenuItem detailAndEdit = new MenuItem("Detail");
                        detailAndEdit.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                StudentInfo studentInfo = row.getItem();
                                DescriptionController descriptionController =
                                        new DescriptionController();
                                descriptionController.show(studentInfo);
                            }
                        });
                        MenuItem delete = new MenuItem("Delete");
                        delete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                //To change body of implemented methods use File | Settings | File Templates.
                                StudentInfo studentInfo =row.getItem();
                                DataBaseHelper dataBaseHelper=new DataBaseHelper(DataBaseConnection.getConnection());
                                dataBaseHelper.deleteFromDatabase(studentInfo.getId());
                                studentInfoObservableList.removeAll(studentInfo);
                                File file=new File("image/"+studentInfo.getId()+".jpg");
                                if(file.exists())
                                    file.delete();
                            }
                        });
                        rowMenu.getItems().addAll(delete,detailAndEdit);
                        row.contextMenuProperty().bind(
                                Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu)null));
                        return row;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                }
        );
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
            studentInfoObservableList = new GetDataFromDatabase().printData(
                    queryHelper.query(DataBaseConstant.STUDENT_INFO_TABLE_NAME));
        } else {
            studentInfoObservableList = new GetDataFromDatabase().printData(
                    queryHelper.query(DataBaseConstant.STUDENT_INFO_TABLE_NAME, stringBuilder.toString()));
        }
        tableView.setItems(studentInfoObservableList);
        if (studentInfoObservableList.size() == 0) {
            Dialogs.showInformationDialog(
                    new Stage(),
                    "Please Check Query",
                    "No Student Found",
                    "Hall Management");
        }
    }
}
