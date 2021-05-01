package com.daema.rest.common.io.file;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

@Component("fileDownloadView")
public class FileDownloadView extends AbstractView {

    public void FileDownloadView() {
        setContentType("application/download; utf-8");
    }

    private String getBrowser(HttpServletRequest req) {
        String header = req.getHeader("User-Agent");
        if (header.contains("MSIE")) {
            return "MSIE";
        } else if (header.contains("Chrome")) {
            return "Chrome";
        } else if (header.contains("Opera")) {
            return "Opera";
        }
        return "MSIE";
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        File file = (File) model.get("downloadFile");

        //System.out.println("DownloadView --> file.getPath() : " + file.getPath());
        //System.out.println("DownloadView --> file.getName() : " + file.getName());

        response.setContentType(getContentType());
        response.setContentLength((int) file.length());
        String userAgent = request.getHeader("User-Agent");
        boolean br = userAgent.indexOf("Chrome") > -1;

        String fileName = null;
        fileName = (String) model.get("fileName");
        if (br) {
            String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
        } else {
            String docName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
        }
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        /*
         * 참고
         * String fileName = null;
        String header = getBrowser(request);
        System.out.println(header);
        if (header.contains("MSIE")) {
               String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
               response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
        } else if (header.contains("Firefox")) {
               String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
               response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
        } else if (header.contains("Opera")) {
               String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
               response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
        } else if (header.contains("Chrome")) {
               String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
               response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
        }
        response.setHeader("Content-Type", "application/octet-stream");
        response.setContentLength((int)file.length());
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");*/


        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                }
            }
        }// try end;
        out.flush();
    }


}
