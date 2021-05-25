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
import org.apache.xmlbeans.XmlOptionsBean;
import org.apache.xmlbeans.impl.common.SAXHelper;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.beans.Introspector;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ExcelUtil {

    //download
    private final String XML_ENCODING = "UTF-8";

    private List<String[]> mHeader;
    private List<LinkedHashMap<String, Object>> mData;

    public ExcelUtil() {
    }

    public ExcelUtil(List<String[]> header, List<LinkedHashMap<String, Object>> data) {
        mHeader = header;
        mData = data;
    }

    public byte[] makeExcel(String templateFile) {

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
        Map<String, XSSFCellStyle> styles = new HashMap<>();
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

        for (int i = 0; i < mHeader.size(); i++) {
            sw.createCell(i, mHeader.get(i)[0], styleIndex);
        }
        sw.endRow();

        int mDataCnt = mData.size() + 1;
        int mHeaderCnt = mHeader.size();

        // write data rows
        for (int rowNum = 1; rowNum < mDataCnt; rowNum++) {
            sw.insertRow(rowNum);

            for (int colNum = 0; colNum < mHeaderCnt; colNum++) {

                parseCellData(sw, rowNum, colNum, mHeader.get(colNum)[1], mHeader.get(colNum)[2], mData.get(rowNum - 1).get(mHeader.get(colNum)[2]));
            }

            sw.endRow();
        }
        sw.endSheet();
    }

    private void parseCellData(SpreadsheetWriter sw, int rowNum, int colNum, String dataType, String dataName, Object cellData) throws Exception {

        String strCellData = "";

        if("class java.time.LocalDate".equals(dataType)
            || "class java.time.LocalDateTime".equals(dataType)){

            HashMap<String, Object> tmpDateData = ((HashMap<String, Object>) (cellData));

            if(cellData != null) {
                strCellData = String.valueOf(tmpDateData.get("year"))
                        .concat(lpadDate(String.valueOf(tmpDateData.get("monthValue"))))
                        .concat(lpadDate(String.valueOf(tmpDateData.get("dayOfMonth"))));

                if("class java.time.LocalDateTime".equals(dataType)){

                    strCellData = strCellData.concat(lpadDate(String.valueOf(tmpDateData.get("hour"))))
                            .concat(lpadDate(String.valueOf(tmpDateData.get("minute"))))
                            .concat(lpadDate(String.valueOf(tmpDateData.get("second"))));
                }

            }
        }else if(dataType.contains("Dto")){

            String dtoClassName = dataType.split("\\.")[dataType.split("\\.").length - 1];
            String data = String.valueOf(((HashMap<String, String>) mData.get(rowNum - 1).get(Introspector.decapitalize(dtoClassName))).get(dataName));

            strCellData = data;

            //제품상태
            if("productFaultyYn".equals(dataName)){
                strCellData = convertFaultyYn(data);
            }
        }else{

            strCellData = String.valueOf(cellData);

            if("productFaultyYn".equals(dataName)) {
                strCellData = convertFaultyYn(String.valueOf(cellData));
            }
        }

        sw.createCell(colNum, strCellData);
    }

    private String lpadDate(String data){
        return  CommonUtil.appendLeft(data, "0", 2);
    }

    private String convertFaultyYn(String data){
        return "Y".equals(data) ? "불량" : "N".equals(data) ? "정상" : "-";
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
     * Writes spreadsheet data in a Writer
     */
    public class SpreadsheetWriter {
        private final Writer _out;
        private int _rowNum;

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
         * @param rowNum 0-based row number
         */
        public void insertRow(int rowNum) throws IOException {
            _out.write("<row r=\"" + (rowNum + 1) + "\">\n");
            this._rowNum = rowNum;
        }

        /**
         * Insert row end marker
         */
        public void endRow() throws IOException {
            _out.write("</row>\n");
        }

        public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
            String ref = new CellReference(_rowNum, columnIndex).formatAsString();
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
            String ref = new CellReference(_rowNum, columnIndex).formatAsString();
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


    //excel read
    private LinkedHashMap<String, String> headers = new LinkedHashMap<>();
    private List<HashMap<String, String>> rows = new ArrayList();

    public LinkedHashMap<String, String> getHeaders() {
        return headers;
    }

    public List<HashMap<String, String>> getRows() {
        return rows;
    }

    public void readXlsSheet(File file) {

        SheetToListHandler sheetToListHandler = new SheetToListHandler();

        try (OPCPackage opc = OPCPackage.open(file)) {
            XSSFReader xssfReader = new XSSFReader(opc);
            XSSFReader.SheetIterator itr = (XSSFReader.SheetIterator) xssfReader.getSheetsData();

            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);

            // Sheet 수 만큼 Loop
            while (itr.hasNext()) {
                InputStream sheetStream = itr.next();
                InputSource sheetSource = new InputSource(sheetStream);

                ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, sheetToListHandler, false);

                XMLReader xmlReader = SAXHelper.newXMLReader(new XmlOptionsBean());
                xmlReader.setContentHandler(handler);
                xmlReader.parse(sheetSource);
                sheetStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SheetToListHandler implements SheetContentsHandler {

        private LinkedHashMap<String, String> rowMap = new LinkedHashMap<>();

        public SheetToListHandler() {
        }

        @Override
        public void startRow(int rowNum) {
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            int iRow = (new CellReference(cellReference)).getRow();

            if(iRow == 0){
                headers.put(cellReference.replaceAll("[0-9]",""), formattedValue);
            }else{
                rowMap.put(headers.get(cellReference.replaceAll("[0-9]","")), formattedValue);
            }
        }

        @Override
        public void endRow(int rowNum) {
            if(rowNum > 0){
                rows.add(rowMap);
                rowMap = new LinkedHashMap<>();
            }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

        @Override
        public void endSheet() {

        }
    }
}


















