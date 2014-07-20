package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import laplab.hallmanagement.DiningInfo.StudentDiningInfo;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/4/14
 * Time: 3:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomizedCallback implements Callback<TableColumn.CellDataFeatures<StudentDiningInfo, String>, ObservableValue<String>> {
    int month = -1;
    String what_to_show="amount";

    public CustomizedCallback(int i) {
        month=i;
    }

    public CustomizedCallback(int i, String what_to_show) {
        month=i;
        this.what_to_show=what_to_show;
    }

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentDiningInfo, String> cellDataFeatures) {
        int i=0;
        if (what_to_show.equals("amount"))  {
            i= cellDataFeatures.getValue().getMonthlyInfos().get(month).getAmount();
        }  else if (what_to_show.equals("credit"))  {
            i= cellDataFeatures.getValue().getMonthlyInfos().get(month).getCredit();
        }   else if (what_to_show.equals("fine"))   {
            i= cellDataFeatures.getValue().getMonthlyInfos().get(month).getFine();
        }

        return new SimpleStringProperty(String.valueOf(i));  //To change body of implemented methods use File | Settings | File Templates.
    }
}
