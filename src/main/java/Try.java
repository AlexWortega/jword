import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Scanner;

public class Try {


    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        FileReader reader = new FileReader("notes3.txt");
        Scanner scan1 = new Scanner(reader);
        String code = "";

        int i = 725;
        String name = scan.nextLine();
        String sureName = scan.nextLine();
        String group = scan.nextLine();
        String doxsPath = scan.nextLine();
        //Blank Document
        //
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        PDDocument doc = PDDocument.load(new File(doxsPath));
        PDPage page = doc.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);

        while (scan1.hasNextLine()) {

            code = scan1.nextLine();
            contentStream.showText(code);
            contentStream.newLineAtOffset(25,i);
            i++;
            contentStream.newLine();


        }

        contentStream.endText();
        System.out.println("Content added");
        contentStream.close();
        doc.save(new File("12345.pdf"));
        doc.close();
        System.out.println("createdocument.pdf written successully");
    }
}
