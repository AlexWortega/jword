    import com.sun.deploy.net.URLEncoder;
    import com.sun.jndi.toolkit.url.UrlUtil;
    import org.apache.poi.xwpf.usermodel.*;

    import java.io.*;
    import java.net.URL;
    import java.net.URLDecoder;
    import java.nio.charset.StandardCharsets;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Scanner;

    public class docx {




        private static ArrayList<String> txt2code(Path filename) throws FileNotFoundException, UnsupportedEncodingException {

            ArrayList<String> code = new ArrayList<String>();


            FileReader reader = new FileReader(String.valueOf(filename));
            Scanner scan1 = new Scanner(reader);
            //System.setProperty("console.encoding","CP1251");

            int k = 0;

            while (scan1.hasNextLine()) {
                String codes = scan1.nextLine();
                String data2 = new String(codes.getBytes("windows-1251"), StandardCharsets.UTF_8);
                code.add(data2);
                k++;

            }
            return code;
        }

        public static void code_gener(HashMap<Integer, ArrayList<String>> items, XWPFDocument document,String name) {
            XWPFParagraph title2 = document.createParagraph();
            title2.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title2.createRun();


            titleRun.setText("Code");
            // titleRun.setColor("009933");
            titleRun.setBold(true);
            titleRun.setFontFamily("Times New Roman ");
            titleRun.setFontSize(10);


            XWPFParagraph para1 = document.createParagraph();
            para1.setSpacingAfter(200);
            para1.setSpacingBefore(200);
            para1.setAlignment(ParagraphAlignment.BOTH);
            XWPFRun para1Run = para1.createRun();
            para1Run.setText(name);
            para1Run.setTextPosition(20);
            para1Run.setBold(true);

            para1Run.addCarriageReturn();
            para1Run.setBold(false);
            for(int i = 0; i< items.size(); i++){
                String[] codes = items.get(i).toArray(new String[0]);
                for (String code : codes) {
                    para1Run.setTextPosition(20);
                    para1Run.setText(code);
                    para1Run.addCarriageReturn();
                    System.out.println(code);
                }

            }
        }


        private static ArrayList<String> get_goal(File dirPath) throws FileNotFoundException, UnsupportedEncodingException {
            File dir = new File(String.valueOf(dirPath));

            File[] fileList = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith("постановка") && name.endsWith(".txt");
                }
            });
            assert fileList != null;

            return  txt2code(fileList[0].toPath());
        }

        private static String get_java(File dirPath) throws FileNotFoundException {
            File dir = new File(String.valueOf(dirPath));

            File[] fileList = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return  name.endsWith(".java");
                }
            });
            assert fileList != null;
            return String.valueOf((fileList[0].toPath()));
        }

        private static ArrayList<String> get_ext(File dirPath) throws FileNotFoundException, UnsupportedEncodingException {
            File dir = new File(String.valueOf(dirPath));

            File[] fileList = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith("вывод") && name.endsWith(".txt");
                }
            });
            assert fileList != null;
            return  txt2code(fileList[0].toPath());
        }



        private static ArrayList<String> get_task(File dirPath) throws FileNotFoundException, UnsupportedEncodingException {
            File dir = new File(String.valueOf(dirPath));

            File[] fileList = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith("задача") && name.endsWith(".txt");
                }
            });
            assert fileList != null;
            return  txt2code(fileList[0].toPath());
        }



        public static HashMap<Integer, ArrayList<String>> getIntegerArrayListHashMap(String txtPath) throws FileNotFoundException, UnsupportedEncodingException {
            File f = new File(txtPath);
            FilenameFilter textFilter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".java");
                }
            };

            HashMap<Integer, ArrayList<String>> items = new HashMap<>();

            File[] files = f.listFiles(textFilter);
            int g=0;
            assert files != null;
            for (File file : files) {
                items.put(g, txt2code(file.toPath()));
                g++;
            }
            return items;
        }



        public static void gener_title(ArrayList<String> exts, XWPFDocument document, String name) {
            XWPFParagraph title3 = document.createParagraph();
            title3.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titletask3 = title3.createRun();


            titletask3.setText(name);
            // titletask3.setColor("black");
            titletask3.setBold(true);
            titletask3.setFontFamily("Times New Roman ");
            titletask3.setFontSize(10);
            XWPFParagraph para3 = document.createParagraph();
            para3.setSpacingAfter(200);
            para3.setSpacingBefore(200);
            para3.setAlignment(ParagraphAlignment.BOTH);
            XWPFRun para3Run = para3.createRun();

            for (String code : exts) {
                para3Run.setTextPosition(10);
                para3Run.setText(code);
                para3Run.addCarriageReturn();
                System.out.println(code);
            }
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
            System.setProperty("console.encoding","UTF-8");

            System.out.println("Фамилия.И.О.");
            String sureName_b = scan.nextLine();//инициалы и фамилия
            String sureName = new String(sureName_b.getBytes("windows-1251"), "CP866");
            System.out.println("Группа");
            String group_b = scan.nextLine();
            String group = new String(group_b.getBytes("windows-1251"), "CP866");
            System.out.println("Фамилия.И.О. преподавателя");
            String teach_srnm_b = scan.nextLine();
            String teach_srnm = new String(teach_srnm_b.getBytes("windows-1251"), "CP866");



            System.out.println("Введите путь к .тхт в формате C://Users//alexd//Desktop//test_apache");
            String txtPath = scan.nextLine();


            InputStream inputStream = docx.class.getResourceAsStream("/Prak1_Nikolich_AD_Inbo-06-19.docx");
            XWPFDocument dc = new XWPFDocument(inputStream);


            //URL pth = ((docx.class.getResource("/Prak1_Nikolich_AD_Inbo-06-19.docx")));
          //  System.out.println(pth);
          //  Path msWordPath = (Path) docx.class.getClassLoader().getResource("Prak1_Nikolich_AD_Inbo-06-19.docx")
            //("src/main/resources/Prak1_Nikolich_AD_Inbo-06-19.docx");


            XWPFDocument document = getXwpfDocument(sureName, group, teach_srnm, txtPath, dc);


            Path path = Paths.get(txtPath);
            File[] files = path.toFile().listFiles();
            ArrayList<String> ext = new ArrayList<String>();
            ArrayList<String> goals = new ArrayList<String>();
            ArrayList<String> tasks = new ArrayList<String>();
            HashMap<Integer, ArrayList<String>> cds = new HashMap<Integer, ArrayList<String>>();
            assert files != null;
            int iter =0;
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    ext = get_ext(file);
                    goals = get_goal(file);
                    tasks = get_task(file);
                    cds = getIntegerArrayListHashMap(file.getPath());
                    displayDirectoryContents(file);
                    iter++;
                    prac_gener(goals,tasks,ext,document,cds,get_java(file),iter);

                }
            }

            //inputStream.close();
            //String userHomeFolder = System.getProperty("user.home");



            System.out.println(" В этой папке лежит отчет " + pathToPortableString(Paths.get(txtPath)));
            FileOutputStream outputStream = new FileOutputStream(pathToPortableString(Paths.get(txtPath))+"/"+sureName+".docx");

            document.write(outputStream);
            outputStream.close();



        }

        static public String pathToPortableString(Path p)
        {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            Path root = p.getRoot();
            if (root != null)
            {
                sb.append(root.toString().replace('\\','/'));

            }
            for (Path element : p)
            {
                if (first)
                    first = false;
                else
                    sb.append("/");
                sb.append(element.toString());
            }
            return sb.toString();
        }

        public static XWPFDocument getXwpfDocument(String sureName, String group, String teach_srnm, String txtPath, XWPFDocument msWordPath
        ) throws IOException {


            changename(msWordPath,"Surname", sureName +" "+ group);//перестановка имен, нейминг говно надо что то придумать
            changename(msWordPath,"teacher", teach_srnm);
            HashMap<Integer, ArrayList<String>> items = getIntegerArrayListHashMap(txtPath);

          //  prac_gener(tsks, cntxts, exts, document, items);
            return msWordPath;
        }

        public static void prac_gener(ArrayList<String> tsks, ArrayList<String> cntxts, ArrayList<String> exts, XWPFDocument document, HashMap<Integer,
                ArrayList<String>> items, String code_name, int num) {

            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setPageBreak(true);
            XWPFParagraph title3 = document.createParagraph();
            title3.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titletask3 = title3.createRun();

            titletask3.setFontSize(16);
            titletask3.setFontFamily("Times New Roman ");
            titletask3.setText("Практическая работа №: "+num);


            // titletask3.setColor("black");
            titletask3.setBold(true);



            gener_title(tsks, document,"Цель");


            gener_title(cntxts, document, "Постановка");


            code_gener(items, document, code_name);


            gener_title(exts, document, "Вывод");
        }

        public static void displayDirectoryContents(File dir) {
            try {
                File[] files = dir.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        System.out.println("directory:" + file.getCanonicalPath());
                        displayDirectoryContents(file);
                    } else {
                        System.out.println("     file:" + file.getCanonicalPath());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


