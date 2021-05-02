package com.daema.rest.base.service;

import com.daema.commgmt.repository.PubNotiRawDataRepository;
import com.daema.rest.base.excel.ExcelMeta;
import com.daema.rest.common.Constants;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.common.util.DateUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ExcelDownloadService {

    private final PubNotiRawDataRepository pubNotiRawDataRepository;
    private final AuthenticationUtil authenticationUtil;

    public ExcelDownloadService(PubNotiRawDataRepository pubNotiRawDataRepository, AuthenticationUtil authenticationUtil) {
        this.pubNotiRawDataRepository = pubNotiRawDataRepository;

        this.authenticationUtil = authenticationUtil;
    }

    /**
     * ExcelVO inner Class 의 정보를 기반으로 header 와 query 맵핑 처리
     * @param cls
     * @return
     */
    private String[][][] makeHeaderInfo(Class<?> cls){

        int fieldLen = cls.getDeclaredFields().length;

        String[][][] headerInfo = new String[fieldLen][fieldLen][fieldLen];

        int cnt = 0;

        for(Field field : cls.getDeclaredFields()){
            headerInfo[cnt][0][0] = field.getDeclaredAnnotation(ExcelMeta.class).headerName();
            headerInfo[cnt][1][0] = field.getType().toString();
            headerInfo[cnt][0][1] = field.getName();
            cnt++;
        }

        return headerInfo;
    }

    private void convertDataList(HashMap<String, Object> xlsMap, List dataList){
        List<LinkedHashMap<String, Object>> convertDataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        if(CommonUtil.isNotEmptyList(dataList)){
            dataList.forEach(
                    data -> convertDataList.add(
                            mapper.convertValue(data, new TypeReference<LinkedHashMap<String, Object>>() {})
                    )
            );
        }

        xlsMap.put("dataList", convertDataList);
    }

    public HashMap<String, Object> makeExcel(String fileName, Class<?> cls, String dataType) {

        HashMap<String, Object> xlsMap = new HashMap<>();

        try {

            //파일명 세팅 S
            xlsMap.put("excelFileName", fileName.concat("_" + DateTimeUtils.currentTimeMillis()));
            //파일명 세팅 E

            //헤더 세팅 S
            xlsMap.put("headerList", makeHeaderInfo(cls));
            //헤더 세팅 E

            List dataList = new ArrayList();

            //본문 조회
            switch (dataType) {
                case "sample":
                    dataList = pubNotiRawDataRepository.searchPubNotiRawData();
                default :
                    System.out.println(dataType);
            }

            //본문 세팅 S
            convertDataList(xlsMap, dataList);
            //본문 세팅 E

        } catch (Exception e) {
            e.printStackTrace();
        }

        return xlsMap;
    }

    public void removeTmpFileAndSaveHistory(String templateFile, String excelFileName, byte[] excelData) {

        String saveFilePath = Constants.XLS_DOWNLOAD_PATH.concat(File.separator).concat(DateUtil.getTodayYYYYMM())
                .concat(File.separator).concat(authenticationUtil.getMemberSeq() + "")
                .concat(File.separator);

        try{
            //파일경로 셋팅
            File dir = new File(saveFilePath);
            if(!dir.exists()){
                dir.mkdirs();
            }

            try (OutputStream os = new FileOutputStream(saveFilePath.concat(File.separator).concat(excelFileName).concat(".xlsx"))) {
                os.write(excelData);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //template 파일 삭제
            System.gc();
            Thread.sleep(500);
            new File(templateFile).delete();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}






















