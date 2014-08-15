package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import laplab.hallmanagement.Month;
import laplab.hallmanagement.database.DataBaseConnection;
import laplab.lib.databasehelper.QueryHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 8/10/14
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreditDetail {
    @FXML
    public TableView tableView;
    public TableColumn monthColumn;
    public TableColumn creditDayColumn;
    public TableColumn amountColumn;
    public Label idLabel;
    public Label nameLabel;

    private Parent parent;
    private Scene scene;
    private Stage stage;

    public CreditDetail() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/creditclicked.fxml"));
        fxmlLoader.setController(this);

        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void show(String id, int startMonth, int startYear, int endMonth, int endYear) {
        System.out.println(id);
        idLabel.setText(id);
        ObservableList<Credit> credits = getDataFromDatabase(id,
                String.valueOf(Month.getMonthID(startYear, startMonth)),
                String.valueOf(Month.getMonthID(endYear, endMonth)));
        if (credits != null && credits.size() > 0) {
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setItems(credits);
            amountColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("amount"));
            monthColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("month"));
            creditDayColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("day"));
        }
        stage = new Stage();
        stage.setTitle("Credit Detail Info");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    private ObservableList<Credit> getDataFromDatabase(String id, String startTime, String endTime) {
        String query = "select studentinfo.name as name, dining.credit_day,month.month,month.year from studentInfo\n" +
                "cross join month\n" +
                "left outer join dining\n" +
                "on dining.month_id=month.ID and dining.student_ID=studentinfo.id\n" +
                "where month.id between " + startTime + " and " + endTime + " and studentinfo.id=" + id;
        QueryHelper queryHelper = new QueryHelper(DataBaseConnection.getConnection());
        ResultSet resultSet = queryHelper.queryInDataBase(query);
        try {
            ObservableList<Credit> credits = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Credit credit = new Credit();
                credit.setMonth(resultSet.getString("month"),resultSet.getString("year"));
                credit.setDay(resultSet.getInt("credit_day"));
                credits.add(credit);
            }
            return credits;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public class Credit {
        String month;
        String amount;
        String day;

        public Credit() {

        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month, String year) {
            if (month == null) {
                this.month = "Not Available";
            } else {
                String monthName = Month.getMonthName(Integer.parseInt(month))
                        + "'" +
                        String.valueOf(Integer.parseInt(year) % 2000);
                System.out.println(monthName);
                this.month = monthName;
            }
        }

        public String getAmount() {
            return amount;
        }

        private void setAmount(int amount) {
            //TODO amount of credit per token can change so a feature for this should be added
            this.amount = String.valueOf(amount*40);
        }

        public String getDay() {
            return day;
        }

        public void setDay(int day) {
                setAmount(day);
                this.day = String.valueOf(day);
        }
    }
}
