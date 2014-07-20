package laplab.hallmanagement.DiningInfo;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/3/14
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonthlyInfo {
    private int month;
    private int amount;
    private int year;
    private int fine;
    private int credit;
    private int isFree;

    public MonthlyInfo(int month, int amount, int year, int fine, int credit, int isFree) {
        this.month = month;
        this.amount = amount;
        this.year = year;
        this.credit = credit;
        this.isFree = isFree;

        if (this.amount != 0 && this.amount >= 600) {
            this.fine = 0;
        } else {
            if (isFree >= 1) {
                this.fine = 0;
            } else {
                this.fine = fine;
            }
        }

    }

    public int getAmount() {
        return amount;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getFine() {
        return fine;
    }

    public int getCredit() {
        return credit;
    }
}
