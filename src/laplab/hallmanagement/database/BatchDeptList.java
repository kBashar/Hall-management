package laplab.hallmanagement.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laplab.lib.databasehelper.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kBashar
 * Date: 7/3/14
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchDeptList {

    public static ObservableList<String> getBatches() {
        ObservableList<String> list = FXCollections.observableArrayList();
        ResultSet resultSet = new QueryHelper(DataBaseConnection.getConnection())
                .query(DataBaseConstant.BATCH_TABLE_NAME);
        try {
            while (resultSet.next()) {
                String str = resultSet.getString(BatchTable.BATCH_NAME_COLUMN);
                list.add(str);
                System.out.println(str);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }

    public static ObservableList<String> getDepts() {
        ObservableList<String> list = FXCollections.observableArrayList();
        ResultSet resultSet = new QueryHelper(DataBaseConnection.getConnection())
                .query(DataBaseConstant.DEPARTMENT_TABLE_NAME);
        try {
            while (resultSet.next()) {
                String str = resultSet.getString(DepartmentTable.DEPARTMENT_NAME_COLUMN);
                list.add(str);
                System.out.println(str);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }
}
