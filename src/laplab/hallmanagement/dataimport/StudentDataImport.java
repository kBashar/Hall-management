package laplab.hallmanagement.dataimport;

import laplab.student.StudentInfo;
import laplab.student.StudentInfoList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentDataImport {

    File file;

    public StudentDataImport(File _file) {
        this.file = _file;
    }

    public StudentInfoList startImporting() {
        StudentInfoList studentInfoList = new StudentInfoList();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();
            int room = 101;

            while (rowIterator.hasNext()) {
                StudentInfo studentInfo = new StudentInfo();
                Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
                int cellnumber = 1;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:

                            Double doubl = cell.getNumericCellValue();
                            int i = doubl.intValue();
                            if (i > 100 && i < 428) {
                                room = i;
                            }

                            switch (cellnumber) {
                                case 1:
                                    studentInfo.setRoom(room);
                                    cellnumber++;
                                    break;
                                case 3:
                                    studentInfo.setId(i);
                                    cellnumber++;
                                    break;
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            switch (cellnumber) {
                                case 2:
                                    studentInfo.setName(cell.getStringCellValue());
                                    cellnumber++;
                                    break;
                                case 4:
                                    studentInfo.setDepartment(cell.getStringCellValue());
                                    cellnumber++;
                                    break;
                                case 5:
                                    studentInfo.setContact(cell.getStringCellValue());
                                    cellnumber++;
                                    break;
                            }
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            if (cellnumber == 1) {
                                studentInfo.setRoom(room);
                                cellnumber++;
                            }
                            break;

                    }
                }
                if (studentInfo.finalizeObject()) {
                    studentInfoList.add(studentInfo);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return studentInfoList;
    }
}
