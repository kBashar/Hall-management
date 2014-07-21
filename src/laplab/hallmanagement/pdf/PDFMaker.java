package laplab.hallmanagement.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import laplab.hallmanagement.DiningInfo.StudentDiningInfo;
import sample.Fine_creditController;

import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/20/14
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class PDFMaker {
    ObservableList<StudentDiningInfo> diningInfoObservableList;
    String whatToAdd;

    public PDFMaker(ObservableList<StudentDiningInfo> list, String str) {
        this.diningInfoObservableList = list;
        this.whatToAdd = str;
    }

    public void make() {
        if (diningInfoObservableList != null) {
            Document document = new Document(PageSize.A4);

            try {
                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream();
                PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
                pdfWriter.setBoxSize("art", new Rectangle(36, 54, 595, 788));
                pdfWriter.setPageEvent(new PageProperties());
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(70);
                table.setHeaderRows(1);
                table.setSplitLate(false);
                table.setSplitRows(false);
                table.addCell("Room");
                table.addCell("ID");
                table.addCell("Taka");

                if (whatToAdd.equals(Fine_creditController.FINE)) {
                    addFineDataInTable(table);
                } else if (whatToAdd.equals(Fine_creditController.CREDIT)) {
                    addCreditDataInTable(table);
                } else if (whatToAdd.equals(Fine_creditController.AMOUNT)) {
                    addAmountDataInTable(table);
                }
                document.open();
                document.add(table);
                document.close();
                FileOutputStream fileOutputStream = new FileOutputStream(getSaveFile());
                fileOutputStream.write(outputStream.toByteArray());
                fileOutputStream.close();
            } catch (DocumentException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        } else {
            return;
        }
    }

    private void addSameRoomStudents(int currentRoom, ArrayList<PdfPCell> cells, PdfPTable table) {
        PdfPCell roomCell = new PdfPCell(new Phrase(String.valueOf(currentRoom)));
        roomCell.setRowspan(cells.size() / 2);
        table.addCell(roomCell);
        for (PdfPCell cell : cells) {
            table.addCell(cell);
        }
    }

    private void addFineDataInTable(PdfPTable table) {
        ArrayList<PdfPCell> cells = new ArrayList<>();
        int currentRoom = diningInfoObservableList.get(0).getRoom();
        for (StudentDiningInfo studentDiningInfo : diningInfoObservableList) {
            if (currentRoom == studentDiningInfo.getRoom()) {
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getStudentID()))));
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getTotalFine()))));
            } else {
                addSameRoomStudents(currentRoom, cells, table);
                cells.clear();
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getStudentID()))));
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getTotalFine()))));
                currentRoom = studentDiningInfo.getRoom();
            }
        }
    }

    private void addCreditDataInTable(PdfPTable table) {
        ArrayList<PdfPCell> cells = new ArrayList<>();
        int currentRoom = diningInfoObservableList.get(0).getRoom();
        for (StudentDiningInfo studentDiningInfo : diningInfoObservableList) {
            if (currentRoom == studentDiningInfo.getRoom()) {
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getStudentID()))));
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getTotalCredit()))));
            } else {
                addSameRoomStudents(currentRoom, cells, table);
                cells.clear();
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getStudentID()))));
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getTotalCredit()))));
                currentRoom = studentDiningInfo.getRoom();
            }
        }
    }

    private void addAmountDataInTable(PdfPTable table) {
        ArrayList<PdfPCell> cells = new ArrayList<>();
        int currentRoom = diningInfoObservableList.get(0).getRoom();
        for (StudentDiningInfo studentDiningInfo : diningInfoObservableList) {
            if (currentRoom == studentDiningInfo.getRoom()) {
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getStudentID()))));
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getTotalAmount()))));
            } else {
                addSameRoomStudents(currentRoom, cells, table);
                cells.clear();
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getStudentID()))));
                cells.add(new PdfPCell(new Phrase(String.valueOf(studentDiningInfo.getTotalAmount()))));
                currentRoom = studentDiningInfo.getRoom();
            }
        }
    }

    private File getSaveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Query in pdf format");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            return file;
        } else {
            return new File("try.pdf");
        }
    }
}
