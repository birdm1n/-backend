package com.daema.rest.base.service;

import com.daema.base.enums.TypeEnum;
import com.daema.commgmt.repository.PubNotiRawDataRepository;
import com.daema.rest.base.excel.ExcelTemplate;
import com.daema.rest.base.excel.ExcelVO;
import com.daema.rest.common.consts.Constants;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import com.daema.rest.common.util.DateUtil;
import com.daema.rest.wms.service.*;
import com.daema.wms.domain.dto.request.*;
import com.daema.wms.domain.enums.WmsEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
    private final InStockMgmtService inStockMgmtService;
    private final MoveStockMgmtService moveStockMgmtService;
    private final ReturnStockMgmtService returnStockMgmtService;
    private final StoreStockMgmtService storeStockMgmtService;
    private final DeviceCurrentMgmtService deviceCurrentMgmtService;
    private final OpeningCurrentMgmtService openingCurrentMgmtService;
    private final AuthenticationUtil authenticationUtil;

    private String fileName;
    private Class<?> cls;
    private List dataList;

    public ExcelDownloadService(PubNotiRawDataRepository pubNotiRawDataRepository, ReturnStockMgmtService returnStockMgmtService, InStockMgmtService inStockMgmtService, MoveStockMgmtService moveStockMgmtService, StoreStockMgmtService storeStockMgmtService, DeviceCurrentMgmtService deviceCurrentMgmtService, OpeningCurrentMgmtService openingCurrentMgmtService, AuthenticationUtil authenticationUtil) {
        this.pubNotiRawDataRepository = pubNotiRawDataRepository;
        this.returnStockMgmtService = returnStockMgmtService;
        this.inStockMgmtService = inStockMgmtService;
        this.moveStockMgmtService = moveStockMgmtService;
        this.storeStockMgmtService = storeStockMgmtService;
        this.deviceCurrentMgmtService = deviceCurrentMgmtService;
        this.openingCurrentMgmtService = openingCurrentMgmtService;
        this.authenticationUtil = authenticationUtil;
    }

    public HashMap<String, Object> init(ModelMap modelMap, String pageType) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        if ("sample".equals(pageType)) {
            fileName = "공시지원금목록_";
            cls = ExcelVO.PubNotiRawData.class;
            dataList = pubNotiRawDataRepository.searchPubNotiRawData();
        } else if ("getInStockListExcel".equals(pageType)) {
            fileName = "입고현황_";
            cls = ExcelVO.InStockList.class;
            dataList = inStockMgmtService.getInStockList(
                    mapper.convertValue(modelMap, InStockRequestDto.class)
            ).getResultList();
        } else if ("insertInStockWaitExcelException".equals(pageType)) {
            fileName = "입고대기_엑셀업로드_실패목록_";
            cls = ExcelVO.BarcodeList.class;

            if(modelMap.get("failList") != null){
                List<String> failList = (List) modelMap.get("failList");
                List<HashMap<String, Object>> mapList = new ArrayList<>();
                failList.forEach(
                        fail -> {
                            HashMap<String, Object> map = new HashMap();
                            map.put("rawBarcode", fail);
                            mapList.add(map);
                        }
                );

                dataList = mapList;
            }
        } else if ("getReturnStockListExcel".equals(pageType)) {
            fileName = "이동재고반품_";
            cls = ExcelVO.ReturnStockList.class;
            dataList = returnStockMgmtService.getReturnStockList(
                    mapper.convertValue(modelMap, ReturnStockRequestDto.class)
            ).getResultList();
        } else if ("insertReturnStockExcelException".equals(pageType)) {
            fileName = "이동재고반품_엑셀업로드_실패목록_";
            cls = ExcelVO.BarcodeList.class;

            if(modelMap.get("failList") != null){
                List<String> failList = (List) modelMap.get("failList");
                List<HashMap<String, Object>> mapList = new ArrayList<>();
                failList.forEach(
                        fail -> {
                            HashMap<String, Object> map = new HashMap();
                            map.put("rawBarcode", fail);
                            mapList.add(map);
                        }
                );

                dataList = mapList;
            }
        } else if ("getStoreStockListExcel".equals(pageType)
                || "getLongTimeStoreStockListExcel".equals(pageType)
                || "getFaultyStoreStockListExcel".equals(pageType)) {

            WmsEnum.StoreStockPathType storeStockPathType = null;
            dataList = new ArrayList();

            if ("getStoreStockListExcel".equals(pageType)) {
                fileName = "재고현황_";
                cls = ExcelVO.StoreStockList.class;
                storeStockPathType = WmsEnum.StoreStockPathType.STORE_STOCK;
            } else if ("getLongTimeStoreStockListExcel".equals(pageType)) {
                fileName = "장기재고_";
                cls = ExcelVO.LongTimeStoreStockList.class;
                storeStockPathType = WmsEnum.StoreStockPathType.LONG_TIME_STORE_STOCK;
            } else if ("getFaultyStoreStockListExcel".equals(pageType)) {
                fileName = "불량기기현황_";
                cls = ExcelVO.FaultyStoreStockList.class;
                storeStockPathType = WmsEnum.StoreStockPathType.FAULTY_STORE_STOCK;
            }

            if(storeStockPathType != null) {
                dataList = storeStockMgmtService.getStoreStockList(
                        mapper.convertValue(modelMap, StoreStockRequestDto.class), storeStockPathType
                ).getResultList();
            }
        } else if ("SELL_MOVEExcel".equals(pageType)
                || "STOCK_MOVEExcel".equals(pageType)
                || "STOCK_TRNSExcel".equals(pageType)
                || "FAULTY_TRNSExcel".equals(pageType)
                || "SELL_TRNSExcel".equals(pageType)) {

            WmsEnum.MovePathType movePathType = null;
            dataList = new ArrayList();

            if ("SELL_MOVEExcel".equals(pageType)) {
                fileName = "판매이동_";
                cls = ExcelVO.SellMoveList.class;
                movePathType = WmsEnum.MovePathType.SELL_MOVE;
            } else if ("STOCK_MOVEExcel".equals(pageType)) {
                fileName = "재고이동_";
                cls = ExcelVO.StockMoveList.class;
                movePathType = WmsEnum.MovePathType.STOCK_MOVE;
            } else if ("STOCK_TRNSExcel".equals(pageType)) {
                fileName = "재고이관_";
                cls = ExcelVO.StoreTransList.class;
                movePathType = WmsEnum.MovePathType.STOCK_TRNS;
            } else if ("FAULTY_TRNSExcel".equals(pageType)) {
                fileName = "불량이관_";
                cls = ExcelVO.FaultyTransList.class;
                movePathType = WmsEnum.MovePathType.FAULTY_TRNS;
            } else if ("SELL_TRNSExcel".equals(pageType)) {
                fileName = "판매이관_";
                cls = ExcelVO.SellTransList.class;
                movePathType = WmsEnum.MovePathType.SELL_TRNS;
            }

            if(movePathType != null) {
                dataList = moveStockMgmtService.getMoveAndTrnsList(
                        movePathType, mapper.convertValue(modelMap, MoveStockRequestDto.class)
                ).getResultList();
            }
        } else if ("getMoveMgmtListExcel".equals(pageType)) {
            fileName = "이동현황_";
            cls = ExcelVO.MoveMgmtList.class;
            dataList = moveStockMgmtService.getMoveMgmtList(mapper.convertValue(modelMap, MoveMgmtRequestDto.class)).getResultList();
        } else if ("getOpeningCurrentListExcel".equals(pageType)) {
            fileName = "개통현황_";
            cls = ExcelVO.OpeningCurrentList.class;
            dataList = openingCurrentMgmtService.getOpeningCurrentList(mapper.convertValue(modelMap, OpeningCurrentRequestDto.class)).getResultList();
        } else if ("getDeviceCurrentListExcel".equals(pageType)) {
            fileName = "기기현황_";
            cls = ExcelVO.DeviceCurrentList.class;
            dataList = deviceCurrentMgmtService.getDeviceCurrentPage(mapper.convertValue(modelMap, DeviceCurrentRequestDto.class)).getResultList();
        }

        return makeExcel();
    }

    /**
     * ExcelVO inner Class 의 정보를 기반으로 header 와 query 맵핑 처리
     *
     * @param cls
     * @return
     */
    private List<String[]> makeHeaderInfo(Class<?> cls) {

        List<String[]> headerInfo = new ArrayList<>();

        for (Field field : cls.getDeclaredFields()) {
            String[] header = new String[3];

            header[0] = field.getDeclaredAnnotation(ExcelTemplate.class).columnName();
            header[1] = field.getType().toString();
            header[2] = field.getName();

            headerInfo.add(header);
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






















