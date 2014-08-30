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
import laplab.hallmanagement.DiningInfo.StudentDiningInfo;
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
public class AmountDetail {
    @FXML
    public TableView tableView;
    public TableColumn monthColumn;
    public TableColumn voucherColumn;
    public TableColumn amountColumn;
    public Label idLabel;
    public Label nameLabel;

    private Parent parent;
    private Scene scene;
    private Stage stage;

    public AmountDetail() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/amountclicked.fxml"));
        fxmlLoader.setController(this);

        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent);
        } catch (IOException e) {
            MakeLogger.printToLogger(getClass().toString(),e.toString());  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void show(String id, int startMonth, int startYear, int endMonth, int endYear) {
        idLabel.setText(id);
        ObservableList<Amount> amounts = getDataFromDatabase(id,
                String.valueOf(Month.getMonthID(startYear, startMonth)),
                String.valueOf(Month.getMonthID(endYear, endMonth)));
        if (amounts != null && amounts.size() > 0) {
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setItems(amounts);
            amountColumn.setCellValueFactory(new PropertyValueFactory<Amount, String>("amount"));
            monthColumn.setCellValueFactory(new PropertyValueFactory<Amount, String>("month"));
            voucherColumn.setCellValueFactory(new PropertyValueFactory<Amount, String>("voucher"));
        }
        stage = new Stage();
        stage.setTitle("Amount Detail Info");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    private ObservableList<Amount> getDataFromDatabase(String id, String startTime, String endTime) {
        String query = "select studentinfo.name as name, dining.amount, dining.voucher_ID,month.month,month.year from studentInfo\n" +
                "cross join month\n" +
                "left outer join dining\n" +
                "on dining.month_id=month.ID and dining.student_ID=studentinfo.id\n" +
                "where month.id between " + startTime + " and " + endTime + " and studentinfo.id=" + id;
        QueryHelper queryHelper = new QueryHelper(DataBaseConnection.getConnection());
        ResultSet resultSet = queryHelper.queryInDataBase(query);
        try {
            ObservableList<Amount> amounts = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Amount amount = new Amount();
                amount.setAmount(resultSet.getString("amount"));
                amount.setMonth(resultSet.getString("month"),resultSet.getString("year"));
                amount.setVoucher(resultSet.getString("voucher_ID"));
                amounts.add(amount);
            }
            return amounts;
        } catch (SQLException e) {
            MakeLogger.printToLogger(getClass().toString(),e.toString());  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public class Amount {
        String month;
        String amount;
        String voucher;

        public Amount() {

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

        public void setAmount(String amount) {
            if (amount == null) {
                this.amount = "0";
            } else {
                this.amount = amount;
            }
        }

        public String getVoucher() {
            return voucher;
        }

        public void setVoucher(String voucher) {
            if (voucher == null) {
                this.voucher = "Not available";
            } else {
                this.voucher = voucher;
            }
        }
    }
}
