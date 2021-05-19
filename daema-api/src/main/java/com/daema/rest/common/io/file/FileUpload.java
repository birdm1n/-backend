package com.daema.rest.common.io.file;

import com.daema.rest.common.Constants;
import com.daema.rest.common.util.DateUtil;
import com.daema.rest.common.util.ExcelUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component("fileUpload")
public class FileUpload {

    public void uploadFile(MultipartRequest request) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param mFile
     * @return Map<String, Object> keys : headers, rows
     *         headers : LinkedHashMap<String, String> : { A : colName, B : colName2, C : colName3 }
     *         rows    : List<HashMap<String, String>> List<{ A : data1, B : data2, C : data3 }>
     */
    public Map<String, Object> uploadExcelAndParser(MultipartFile mFile) {

        Map<String, Object> parserMap = null;

        if(mFile != null
                && StringUtils.hasText(mFile.getName())){

            String filePath = Constants.XLS_UPLOAD_PATH.concat(File.separator);

            String profile = System.getProperty("spring.profiles.active");

            if(profile != null &&
                    !"prod".equals(profile)) {

                filePath = "C:".concat(Constants.XLS_UPLOAD_PATH.concat(File.separator));
            }

            try{
                //파일경로 셋팅
                File dir = new File(filePath);

                if(!dir.exists()){
                    dir.mkdirs();
                }

                String originalFileName = mFile.getOriginalFilename();
                String tmpFileName   = DateUtil.getCurrentDateTimeMilli() + "_";
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
                String uploadFile = filePath.concat(tmpFileName).concat(".").concat(fileExtension);

                File excelFile = new File(uploadFile);

                mFile.transferTo(excelFile);

                ExcelUtil excelUtil = new ExcelUtil();
                excelUtil.readXlsSheet(excelFile);

                parserMap = new HashMap<>();
                parserMap.put("headers", excelUtil.getHeaders());
                parserMap.put("rows", excelUtil.getRows());

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return parserMap;
    }
}
