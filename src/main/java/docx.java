import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class docx {

    private static ArrayList<String> txt2code(Path filename) throws FileNotFoundException {

        ArrayList<String> code = new ArrayList<String>();


        FileReader reader = new FileReader(String.valueOf(filename));
        Scanner scan1 = new Scanner(reader);

        int k = 0;

        while (scan1.hasNextLine()) {
            String codes = scan1.nextLine();
            code.add(codes);
            k++;

        }
        return code;
    }

    public static void changename(XWPFDocument document, String replaced, String repl){
        for (XWPFTable tbl : document.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);

                            if (text != null && text.contains(replaced)) {
                                text = text.replace(replaced, repl);
                                r.setText(text,0);
                            }
                        }
                    }
                }
            }
        }

    }


    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Фамилия.И.О.");
        String sureName = scan.nextLine();//инициалы и фамилия

        System.out.println("Группа");
        String group = scan.nextLine();

        System.out.println("Фамилия.И.О. преподавателя");
        String teach_srnm = scan.nextLine();

        System.out.println("файл в .тхт с целью задачи   в таком формате C://Users//alexd//Desktop//test_apache//goal.txt");
        Path task = Paths.get(scan.nextLine());
        ArrayList<String> tsks = txt2code(task);

        System.out.println("файл в .тхт с постановкой задачи  в таком формате C://Users//alexd//Desktop//test_apache//постанова.txt");
        Path context = Paths.get(scan.nextLine());
        ArrayList<String> cntxts = txt2code(context);


        System.out.println("файл в .тхт с выводом  в таком формате C://Users//alexd//Desktop//test_apache//вывод.txt");
        Path exit = Paths.get(scan.nextLine());
        ArrayList<String> exts =  txt2code(exit);


        System.out.println("Введите путь к .тхт в формате C://Users//alexd//Desktop//test_apache");
        String txtPath = scan.nextLine();



        //Тут лежит код  в формате - массив строк программы, номер в хэшмапе

        HashMap<Integer, ArrayList<String>> items = new HashMap<>();


        File f = new File(txtPath);
        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".java");
            }
        };



        File[] files = f.listFiles(textFilter);
        int g=0;
        assert files != null;
        for (File file : files) {
            items.put(g, txt2code(file.toPath()));
            g++;
        }


        //codes = txt2code(txt);
        Path msWordPath = Paths.get("Prak1_Nikolich_AD_Inbo-06-19.docx");


        /*
        TODO
        из тхт
        Практика 1
         цель задания,
          Задача,
           вывод
х

        программа в .java

        */
        XWPFDocument document = new XWPFDocument(Files.newInputStream(msWordPath));




        changename(document,"Surname",sureName+" "+group);//перестановка имен, нейминг говно надо что то придумать
        changename(document,"teacher",teach_srnm);



        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titletask = title.createRun();

        titletask.setText("Цель");
        titletask.setColor("009933");
        titletask.setBold(true);
        titletask.setFontFamily("Courier");
        titletask.setFontSize(20);
        XWPFParagraph para0 = document.createParagraph();
        para0.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun para0Run = para0.createRun();
        for (String code : tsks) {
            para0Run.setTextPosition(20);
            para0Run.setText(code);
            para0Run.addCarriageReturn();
            System.out.println(code);
        }



////////////////////////////////
        XWPFParagraph title1 = document.createParagraph();
        title1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titletask1 = title1.createRun();

        titletask1.setText("Постановка");
        titletask1.setColor("009933");
        titletask1.setBold(true);
        titletask1.setFontFamily("Courier");
        titletask1.setFontSize(20);


        XWPFParagraph para2 = document.createParagraph();
        para0.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun para2Run = para2.createRun();

        for (String code : cntxts) {
            para2Run.setTextPosition(20);
            para2Run.setText(code);
            para2Run.addCarriageReturn();
            System.out.println(code);
        }

///////////////////////////////////


        XWPFParagraph title2 = document.createParagraph();
        title2.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title2.createRun();



        titleRun.setText("Code");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);


        XWPFParagraph para1 = document.createParagraph();
        para1.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun para1Run = para1.createRun();


        for(int i=0; i< items.size();i++){
            String[] codes = items.get(i).toArray(new String[0]);
            for (String code : codes) {
                para1Run.setTextPosition(20);
                para1Run.setText(code);
                para1Run.addCarriageReturn();
                System.out.println(code);
            }

        }

/////////////////////////////////////////////

        XWPFParagraph title3 = document.createParagraph();
        title3.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titletask3 = title3.createRun();


        titletask3.setText("Вывод");
        titletask3.setColor("009933");
        titletask3.setBold(true);
        titletask3.setFontFamily("Courier");
        titletask3.setFontSize(20);
        XWPFParagraph para3 = document.createParagraph();
        para3.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun para3Run = para3.createRun();

        for (String code : exts) {
            para3Run.setTextPosition(20);
            para3Run.setText(code);
            para3Run.addCarriageReturn();
            System.out.println(code);
        }


///////////////////////////////////////
        // сохраняем модель docx документа в файл
        FileOutputStream outputStream = new FileOutputStream("D:/Apache POI Word Test.docx");
        System.out.println("D:/Apache POI Word Test.docx"+" возможно где то тут оно лежит. или не лежит");
        document.write(outputStream);
        outputStream.close();



    }
}


