package com.daema.rest.common.io.file;

import com.daema.rest.base.service.ExcelDownloadService;
import com.daema.rest.common.Constants;
import com.daema.rest.common.util.ExcelUtil;
import org.joda.time.DateTimeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component("excelDownloadView")
public class ExcelDownloadView extends AbstractView {

    private final ExcelDownloadService excelDownloadService;

    public ExcelDownloadView(ExcelDownloadService excelDownloadService) {
        this.excelDownloadService = excelDownloadService;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            HashMap<String, Object> xlsMap = (HashMap<String, Object>) model.get("xlsMap");

            //xls 정보 세팅
            String excelFileName = (String) xlsMap.get("excelFileName");
            String[][][] headerList = (String[][][]) xlsMap.get("headerList");
            List<LinkedHashMap<String, Object>> dataList = (List<LinkedHashMap<String, Object>>) xlsMap.get("dataList");

            //더미 tmp 파일
            String templatePath = Constants.XLS_TMP_PATH.concat(File.separator);
            String templateFile = templatePath.concat("templateFile_" + DateTimeUtils.currentTimeMillis() + ".xls");

            File dir = new File(templatePath);

            if(!dir.exists()){
                dir.mkdirs();
            }

            ExcelUtil excelUtil = new ExcelUtil(headerList, dataList);
            byte[] excelByteData = excelUtil.makeExcel(templateFile);

            String userAgent = request.getHeader("User-Agent");
            boolean br = userAgent.contains("Chrome");

            String docName = "";

            if (br) {
                docName = new String(excelFileName.getBytes("UTF-8"), "ISO-8859-1");
            } else {
                docName = URLEncoder.encode(excelFileName, "UTF-8").replaceAll("\\+", "%20");
            }

            response.setHeader("Content-Disposition", "attachment; filename=" + docName + ".xlsx");
            response.setContentLength(excelByteData.length);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "private");
            response.setHeader("Expires", "0");

            try (ServletOutputStream out = response.getOutputStream()){
                out.write(excelByteData);
                out.flush();

                excelDownloadService.removeTmpFileAndSaveHistory(templateFile, excelFileName, excelByteData);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
