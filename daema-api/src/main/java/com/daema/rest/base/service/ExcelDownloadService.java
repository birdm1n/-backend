package com.daema.rest.base.service;

import com.daema.base.enums.TypeEnum;
import com.daema.commgmt.repository.PubNotiRawDataRepository;
import com.daema.rest.base.excel.ExcelTemplate;
import com.daema.rest.base.excel.ExcelVO;
import com.daema.rest.common.Constants;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.common.util.DateUtil;
import com.daema.rest.wms.service.InStockMgmtService;
import com.daema.rest.wms.service.ReturnStockMgmtService;
import com.daema.rest.wms.service.StoreStockMgmtService;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.domain.dto.request.ReturnStockRequestDto;
import com.daema.wms.domain.dto.request.StoreStockRequestDto;
import com.daema.wms.domain.enums.WmsEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

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
    private final ReturnStockMgmtService returnStockMgmtService;
    private final InStockMgmtService inStockMgmtService;
    private final StoreStockMgmtService storeStockMgmtService;

    private final AuthenticationUtil authenticationUtil;

    private String fileName;
    private Class<?> cls;
    private List dataList;

    public ExcelDownloadService(PubNotiRawDataRepository pubNotiRawDataRepository, ReturnStockMgmtService returnStockMgmtService, InStockMgmtService inStockMgmtService, StoreStockMgmtService storeStockMgmtService, AuthenticationUtil authenticationUtil) {
        this.pubNotiRawDataRepository = pubNotiRawDataRepository;
        this.returnStockMgmtService = returnStockMgmtService;
        this.inStockMgmtService = inStockMgmtService;
        this.storeStockMgmtService = storeStockMgmtService;

        this.authenticationUtil = authenticationUtil;
    }

    public HashMap<String, Object> init(ModelMap modelMap, String pageType) {

        ObjectMapper mapper = new ObjectMapper();

        if ("sample".equals(pageType)) {
            fileName = "공시지원금목록_";
            cls = ExcelVO.PubNotiRawData.class;
            dataList = pubNotiRawDataRepository.searchPubNotiRawData();
        } else if ("getInStockListExcel".equals(pageType)) {
            fileName = "입고현황_";
            cls = ExcelVO.InStockList.class;
            dataList = inStockMgmtService.getInStockList(mapper.convertValue(modelMap, InStockRequestDto.class)).getResultList();
        } else if ("getReturnStockListExcel".equals(pageType)) {
            fileName = "이동재고반품_";
            cls = ExcelVO.ReturnStockList.class;
            dataList = returnStockMgmtService.getReturnStockList(mapper.convertValue(modelMap, ReturnStockRequestDto.class)).getResultList();
        } else if ("/download/excel/insertReturnStockExcelException".equals(pageType)) {
            fileName = "이동재고반품_엑셀업로드_실패목록_";
            cls = ExcelVO.BarcodeList.class;
            dataList = (List) modelMap.get("failList");
        } else if ("getStoreStockListExcel".equals(pageType)
                || "getLongTimeStoreStockListExcel".equals(pageType)
                || "getFaultyStoreStockListExcel".equals(pageType)) {

            WmsEnum.StoreStockPathType storeStockPathType = null;

            if ("getFaultyStoreStockListExcel".equals(pageType)) {
                fileName = "불량기기현황_";
                cls = ExcelVO.FaultyStoreStockList.class;
                storeStockPathType = WmsEnum.StoreStockPathType.FAULTY_STORE_STOCK;
            } else if ("getLongTimeStoreStockListExcel".equals(pageType)) {
                fileName = "장기재고_";
                cls = ExcelVO.LongTimeStoreStockList.class;
                storeStockPathType = WmsEnum.StoreStockPathType.LONG_TIME_STORE_STOCK;
            } else {
                fileName = "재고현황_";
                cls = ExcelVO.StoreStockList.class;
                storeStockPathType = WmsEnum.StoreStockPathType.STORE_STOCK;
            }

            dataList = storeStockMgmtService.getStoreStockList(mapper.convertValue(modelMap, StoreStockRequestDto.class), storeStockPathType).getResultList();

        } else if ("getMove......ListExcel".equals(pageType)) {
            //TODO 이동/이관 엑셀 다운로드 추가 필요
            //fileName = "이동현황_";
            //cls = ExcelVO.MoveMgmtList.class;
            //dataList = returnStockMgmtService.getReturnStockList(mapper.convertValue(modelMap, ReturnStockRequestDto.class)).getResultList();
        } else if ("getMoveMgmtListExcel".equals(pageType)) {
            //TODO 이동현황 엑셀 다운로드 추가 필요
            fileName = "이동현황_";
            cls = ExcelVO.MoveMgmtList.class;
            //dataList = returnStockMgmtService.getReturnStockList(mapper.convertValue(modelMap, ReturnStockRequestDto.class)).getResultList();
        } else if ("getDeviceCurrentListExcel".equals(pageType)) {
            //TODO 기기현황 엑셀 다운로드 추가 필요
            fileName = "기기현황_";
            cls = ExcelVO.DeviceCurrentList.class;
            //dataList = deviceMgmtService.getReturnStockList(mapper.convertValue(modelMap, ReturnStockRequestDto.class)).getResultList();
        }

        return makeExcel();
    }

    /**
     * ExcelVO inner Class 의 정보를 기반으로 header 와 query 맵핑 처리
     *
     * @param cls
     * @return
     */
    private String[][][] makeHeaderInfo(Class<?> cls) {

        int fieldLen = cls.getDeclaredFields().length;

        String[][][] headerInfo = new String[fieldLen][fieldLen][fieldLen];

        int cnt = 0;

        for (Field field : cls.getDeclaredFields()) {
            headerInfo[cnt][0][0] = field.getDeclaredAnnotation(ExcelTemplate.class).columnName();
            headerInfo[cnt][1][0] = field.getType().toString();
            headerInfo[cnt][0][1] = field.getName();
            cnt++;
        }

        return headerInfo;
    }

    private void convertDataList(HashMap<String, Object> xlsMap, List dataList) {
        List<LinkedHashMap<String, Object>> convertDataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        if (CommonUtil.isNotEmptyList(dataList)) {
            dataList.forEach(
                    data -> convertDataList.add(
                            mapper.convertValue(data, new TypeReference<LinkedHashMap<String, Object>>() {
                            })
                    )
            );
        }

        xlsMap.put("dataList", convertDataList);
    }

    public HashMap<String, Object> makeExcel() {

        HashMap<String, Object> xlsMap = new HashMap<>();

        try {

            //파일명 세팅 S
            xlsMap.put("excelFileName", fileName.concat("_" + DateTimeUtils.currentTimeMillis()));
            //파일명 세팅 E

            //헤더 세팅 S
            xlsMap.put("headerList", makeHeaderInfo(cls));
            //헤더 세팅 E

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

        try {
            //파일경로 셋팅
            File dir = new File(saveFilePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try (OutputStream os = new FileOutputStream(saveFilePath.concat(File.separator).concat(excelFileName).concat(".").concat(TypeEnum.XLSX.getStatusMsg()))) {
                os.write(excelData);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //template 파일 삭제
            System.gc();
            Thread.sleep(500);
            new File(templateFile).delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






















