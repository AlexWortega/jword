import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Try {

    private static ArrayList<String> txt2code(String filename) throws FileNotFoundException {
        ArrayList<String> code = new ArrayList<String>();
        FileReader reader = new FileReader(filename);
        Scanner scan1 = new Scanner(reader);
        int k = 0;
        while (scan1.hasNextLine()) {
            String codes = scan1.nextLine();
            code.add(codes);
            k++;

        }
        return code;
    }

    public static void main(String[] args) throws Exception {

        ArrayList<String> codes = txt2code("notes3.txt");

        Scanner scan = new Scanner(System.in);


        int y = 725;

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


        contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
        int x = 25;

        for (String code : codes) {
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(code);
            System.out.println(code);

            //contentStream.newLine();
            contentStream.endText();

            y=y+12;


        }



        System.out.println("Content added");
        contentStream.close();
        doc.save(new File("12345.pdf"));
        doc.close();
        System.out.println("createdocument.pdf written successully");


    }




}



