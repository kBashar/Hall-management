package laplab.hallmanagement.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import sun.font.FontFamily;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 7/21/14
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageProperties extends PdfPageEventHelper {
    int pagenumber;

    public void onStartPage(PdfWriter pdfWriter, Document document) {
        pagenumber++;
    }

    public void onEndPage(PdfWriter writer, Document pdfDocument) {
        Rectangle rect = writer.getBoxSize("art");
        System.out.println("Width " + rect.getWidth() +
                "\nheight " + rect.getHeight() +
                "\nleft "+ rect.getLeft()+
                "\nright "+ rect.getRight()+
                "\nbottom"+ rect.getBottom()
        );
        Phrase pageNumberPhrase=new Phrase(String.format("page %d", pagenumber));
        pageNumberPhrase.setFont(new Font(Font.FontFamily.HELVETICA,6,Font.BOLDITALIC));
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, pageNumberPhrase,
                rect.getRight()-65, rect.getBottom()-25, 0);
        drawLine(writer.getDirectContent(),
                pdfDocument.left(), pdfDocument.right(),
                rect.getBottom() - 30);
        Phrase developerCourtesy=new Phrase("Automated Document produced with Hall Management Application, Developed By METROBITS");
        pageNumberPhrase.setFont(new Font(Font.FontFamily.HELVETICA,3,Font.ITALIC));
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_JUSTIFIED_ALL, developerCourtesy,
                pdfDocument.left(), rect.getBottom()-42, 0);
    }

    public void drawLine(PdfContentByte cb,
                         float x1, float x2, float y) {
        cb.moveTo(x1, y);
        cb.lineTo(x2, y);
        cb.stroke();
    }
}
