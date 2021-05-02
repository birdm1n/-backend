package com.daema.rest.common.util;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ExcelUtil {

    //download
    private final String XML_ENCODING = "UTF-8";

    private String[][][] mHeader;
    private List<LinkedHashMap<String, Object>> mData;

    public ExcelUtil(String[][][] header, List<LinkedHashMap<String, Object>> data) {
        mHeader = header;
        mData = data;
    }

    public byte[] makeExcel(String templateFile) throws Exception {

        byte[] bytes = new byte[0];

        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            // Step 1. Create a template file. Setup sheets and workbook-level
            // objects such as
            // cell styles, number formats, etc.

            XSSFSheet sheet = wb.createSheet("sheet1");

            Map<String, XSSFCellStyle> styles = createStyles(wb);
            // name of the zip entry holding sheet data, e.g.
            // /xl/worksheets/sheet1.xml
            String sheetRef = sheet.getPackagePart().getPartName().getName();

            // save the template
            FileOutputStream os = new FileOutputStream(templateFile);
            wb.write(os);
            os.close();

            // Step 2. Generate XML file.
            File tmp = File.createTempFile("sheet", ".xml");
            Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
            generate(fw, styles);
            fw.close();

            // Step 3. Substitute the template entry with the generated data
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            substitute(new File(templateFile), tmp, sheetRef.substring(1), out);
            bytes = out.toByteArray();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }

    /**
     * Create a library of cell styles.
     */
    private Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
        XSSFDataFormat fmt = wb.createDataFormat();

        XSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.RIGHT);
        style1.setDataFormat(fmt.getFormat("0.0%"));
        styles.put("percent", style1);

        XSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setDataFormat(fmt.getFormat("0.0X"));
        styles.put("coeff", style2);

        XSSFCellStyle style3 = wb.createCellStyle();
        style3.setAlignment(HorizontalAlignment.RIGHT);
        style3.setDataFormat(fmt.getFormat("$#,##0.00"));
        styles.put("currency", style3);

        XSSFCellStyle style4 = wb.createCellStyle();
        style4.setAlignment(HorizontalAlignment.RIGHT);
        style4.setDataFormat(fmt.getFormat("mmm dd"));
        styles.put("date", style4);

        XSSFCellStyle style5 = wb.createCellStyle();
        XSSFFont headerFont = wb.createFont();
        headerFont.setBold(true);
        style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style5.setFont(headerFont);
        styles.put("header", style5);

        return styles;
    }

    private void generate(Writer out, Map<String, XSSFCellStyle> styles) throws Exception {

        SpreadsheetWriter sw = new SpreadsheetWriter(out);
        sw.beginSheet();

        // write header row
        sw.insertRow(0);
        int styleIndex = styles.get("header").getIndex();

        for (int i = 0; i < mHeader.length; i++) {
            sw.createCell(i, mHeader[i][0][0], styleIndex);
        }
        sw.endRow();

        // write data rows
        for (int rownum = 1; rownum < mData.size() + 1; rownum++) {
            sw.insertRow(rownum);

            for (int i = 0; i < mHeader.length; i++) {

                if("class java.time.LocalDate".equals(mHeader[i][1][0])){

                    String[] tmp = String.valueOf(mData.get(rownum - 1).get(mHeader[i][0][1])).split(",");

                    sw.createCell(i,
                            tmp.length > 1 ?
                                tmp[0].split("=")[1]
                                .concat(CommonUtil.appendLeft(tmp[3].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmp[2].split("=")[1], "0", 2))
                                    : null
                    );
                }else if("class java.time.LocalDateTime".equals(mHeader[i][1][0])){

                    String[] tmp = String.valueOf(mData.get(rownum - 1).get(mHeader[i][0][1])).split(",");

                    sw.createCell(i,
                            tmp.length > 1 ?
                                tmp[1].split("=")[1]
                                .concat(CommonUtil.appendLeft(tmp[5].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmp[2].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmp[3].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmp[4].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmp[7].split("=")[1], "0", 2))
                                    : null
                    );
                }else {
                    sw.createCell(i, String.valueOf(mData.get(rownum - 1).get(mHeader[i][0][1])));
                }
            }

            sw.endRow();
        }
        sw.endSheet();
    }

    /**
     * @param zipfile the template file
     * @param tmpfile the XML file with the sheet data
     * @param entry   the name of the sheet entry to substitute, e.g.
 *                xl/worksheets/sheet1.xml
     * @param out     the stream to write the result to
     */
    private void substitute(File zipfile, File tmpfile, String entry, OutputStream out) throws IOException {
        ZipFile zip = new ZipFile(zipfile);

        ZipOutputStream zos = new ZipOutputStream(out);

        Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
        while (en.hasMoreElements()) {
            ZipEntry ze = en.nextElement();
            if (!ze.getName().equals(entry)) {
                zos.putNextEntry(new ZipEntry(ze.getName()));
                InputStream is = zip.getInputStream(ze);
                copyStream(is, zos);
                is.close();
            }
        }
        zos.putNextEntry(new ZipEntry(entry));
        InputStream is = new FileInputStream(tmpfile);
        copyStream(is, zos);
        is.close();

        zos.close();

    }

    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }

    /**
     * Writes spreadsheet data in a Writer. (YK: in future it may evolve in a
     * full-featured API for streaming data in Excel)
     */
    public class SpreadsheetWriter {
        private final Writer _out;
        private int _rownum;

        public SpreadsheetWriter(Writer out) {
            _out = out;
        }

        public void beginSheet() throws IOException {
            _out.write("<?xml version=\"1.0\" encoding=\"" + XML_ENCODING + "\"?>"
                    + "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
            _out.write("<sheetData>\n");
        }

        public void endSheet() throws IOException {
            _out.write("</sheetData>");
            _out.write("</worksheet>");
        }

        /**
         * Insert a new row
         *
         * @param rownum 0-based row number
         */
        public void insertRow(int rownum) throws IOException {
            _out.write("<row r=\"" + (rownum + 1) + "\">\n");
            this._rownum = rownum;
        }

        /**
         * Insert row end marker
         */
        public void endRow() throws IOException {
            _out.write("</row>\n");
        }

        public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("<c r=\"" + ref + "\" t=\"inlineStr\"");
            if (styleIndex != -1)
                _out.write(" s=\"" + styleIndex + "\"");
            _out.write(">");
            _out.write("<is><t>" + value + "</t></is>");
            _out.write("</c>");
        }

        public void createCell(int columnIndex, String value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("<c r=\"" + ref + "\" t=\"n\"");
            if (styleIndex != -1)
                _out.write(" s=\"" + styleIndex + "\"");
            _out.write(">");
            _out.write("<v>" + value + "</v>");
            _out.write("</c>");
        }

        public void createCell(int columnIndex, double value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {
            createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
        }
    }


    //read
    private List<LinkedHashMap<String, String>> excelDataList = new ArrayList<>();

    public ExcelUtil() {

    }

    public List<LinkedHashMap<String, String>> readXlsSheet(String filename, int column_cnt) {

        try {
            /** Parsing 설정 부분 **/
            //bulkInsertFile = File 객체 or FileInputStream (ex : new File(파일경로) 등으로 넣을 수 있음)
            //OPCPackage 파일을 읽거나 쓸수있는 상태의 컨테이너를 생성함
            OPCPackage opc = OPCPackage.open(filename);
            //opc 컨테이너 XSSF형식으로 읽어옴. 이 Reader는 적은 메모리로 sax parsing
            XSSFReader xssfReader = new XSSFReader(opc);
            //XSSFReader 에서 sheet 별 collection으로 분할해서 가져옴.
            XSSFReader.SheetIterator itr = (XSSFReader.SheetIterator) xssfReader.getSheetsData();

            //통합문서 내의 모든 Sheet에서 공유되는 스타일 테이블
            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);

            //데이터를 파싱 List 객체...
            List<String[]> dataList = new ArrayList<String[]>();

            // Sheet 수 만큼 Loop
            while (itr.hasNext()) {
                InputStream sheetStream = itr.next();
                InputSource sheetSource = new InputSource(sheetStream);

                //엑셀 data를 가져와서 SheetContentsHandler(Interface) 재정의
                Sheet2ListHandler sheet2ListHandler = new Sheet2ListHandler(dataList, column_cnt);

                //new XSSFSheetXMLHandler(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheet2ListHandler, boolean formulasNotResults)
                //Sheet의 행(row) 및 Cell 이벤트 생성.
                ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, sheet2ListHandler, false);

                //sax parser를 생성
                SAXParserFactory saxFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxFactory.newSAXParser();
                //sax parser 방식 xmlReader 생성
                XMLReader sheetParser = saxParser.getXMLReader();
                //xml reader에 row와 cell 이벤트를 생성하는 핸들러를 설정
                sheetParser.setContentHandler(handler);
                //위에서 Sheet 별로 생성한 inputSource를 parsing
                //이 과정에서 handler는 row와 cell 이벤트를 생성하고 생성된 이벤트는 sheet2ListHandler 가 받아서 처리
                sheetParser.parse(sheetSource);

                sheetStream.close();
            }

            opc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return excelDataList;
    }

    public class Sheet2ListHandler implements SheetContentsHandler {

        //collection 객체
        private List<String[]> rows;
        //collection 에 추가될 객체 startRow에서 초기화
        private String[] row;
        //collection 내 객체를 String[] 로 잡았기 때문에 배열의 길이를 생성시 받도록 설계
        private int columnCnt;
        //cell 이벤트 처리 시 해당 cell의 데이터가 배열 어디에 저장되야 할지 가리키는 pointer
        private int currColNum = 0;

        //외부 collection 과 배열 size를 받기 위해 추가
        public Sheet2ListHandler(
                List<String[]> rows
                , int columnsCnt
        ) {
            this.rows = rows;
            this.columnCnt = columnsCnt;
        }

        //Row의 시작 부분에서 발생하는 이벤트를 처리하는 method
        public void startRow(int rowNum) {
            this.row = new String[columnCnt];
            currColNum = 0;
        }

        @Override
        public void endRow(int rowNum) {
            //cell 이벤트에서 담아놓은 row String[]를 collection에 추가
            //데이터가 없는 row는 collection에 추가하지 않도록 조건 추가
            boolean addFlag = false;
            for (String data : row) {
                if (!"".equals(data))
                    addFlag = true;
            }
            if (addFlag) rows.add(row);
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            //cell 이벤트 발생 시 해당 cell의 주소와 값을 받아옴.
            row[currColNum++] = formattedValue == null ? "" : formattedValue;

            rowMap.put(String.valueOf(currColNum), formattedValue);

            if (currColNum == columnCnt) {
                excelDataList.add(rowMap);

                rowMap = new LinkedHashMap<>();
            }
        }

        private LinkedHashMap<String, String> rowMap = new LinkedHashMap<>();

        public void headerFooter(String paramString1, boolean paramBoolean,
                                 String paramString2) {
            //sheet의 첫 row와 마지막 row를 처리하는 method
        }
    }
}