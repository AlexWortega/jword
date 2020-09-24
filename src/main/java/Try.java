import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Try {


    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        String sureName = scan.nextLine();
        String group = scan.nextLine();
        String doxsPath = scan.nextLine();
        //Blank Document
        //
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        PDDocument doc = PDDocument.load(new File(doxsPath));
        document.write(out);
        out.close();
        System.out.println("createdocument.docx written successully");
    }
}
