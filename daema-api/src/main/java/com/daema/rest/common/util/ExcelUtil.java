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

        // write data rows
        for (int rownum = 1; rownum < mData.size() + 1; rownum++) {
            sw.insertRow(rownum);

            for (int i = 0; i < mHeader.size(); i++) {

                if("class java.time.LocalDate".equals(mHeader.get(i)[1])){

                    String[] tmpLocalDate = String.valueOf(mData.get(rownum - 1).get(mHeader.get(i)[2])).split(",");

                    sw.createCell(i,
                            tmpLocalDate.length > 1 ?
                                    tmpLocalDate[0].split("=")[1]
                                .concat(CommonUtil.appendLeft(tmpLocalDate[3].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmpLocalDate[2].split("=")[1], "0", 2))
                                    : null
                    );
                }else if("class java.time.LocalDateTime".equals(mHeader.get(i)[1])){

                    String[] tmpLocalDateTime = String.valueOf(mData.get(rownum - 1).get(mHeader.get(i)[2])).split(",");

                    sw.createCell(i,
                            tmpLocalDateTime.length > 1 ?
                                    tmpLocalDateTime[1].split("=")[1]
                                .concat(CommonUtil.appendLeft(tmpLocalDateTime[5].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmpLocalDateTime[2].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmpLocalDateTime[3].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmpLocalDateTime[4].split("=")[1], "0", 2))
                                .concat(CommonUtil.appendLeft(tmpLocalDateTime[7].split("=")[1], "0", 2))
                                    : null
                    );
                }else {
                    sw.createCell(i, String.valueOf(mData.get(rownum - 1).get(mHeader.get(i)[2])));
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
     * Writes spreadsheet data in a Writer
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


















