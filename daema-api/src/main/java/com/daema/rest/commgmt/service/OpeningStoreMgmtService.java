package com.daema.rest.commgmt.service;

import com.daema.base.domain.Member;
import com.daema.base.domain.QMember;
import com.daema.base.repository.MemberRepository;
import com.daema.commgmt.domain.*;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.commgmt.domain.dto.response.OpenStoreListDto;
import com.daema.commgmt.domain.pk.OpenStoreSaleStoreMapPK;
import com.daema.commgmt.domain.pk.OpenStoreUserMapPK;
import com.daema.commgmt.repository.OpenStoreRepository;
import com.daema.commgmt.repository.OpenStoreSaleStoreMapRepository;
import com.daema.commgmt.repository.OpenStoreUserMapRepository;
import com.daema.commgmt.repository.StoreRepository;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.OpeningStoreMgmtDto;
import com.daema.rest.commgmt.dto.response.OpeningStoreSaleStoreResponseDto;
import com.daema.rest.commgmt.dto.response.OpeningStoreUserResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.enums.StatusEnum;
import com.daema.rest.common.enums.TypeEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.StockMgmtDto;
import com.daema.rest.wms.service.StockMgmtService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpeningStoreMgmtService {

    private final OpenStoreRepository openStoreRepository;
    private final StoreRepository storeRepository;
    private final OpenStoreSaleStoreMapRepository openStoreSaleStoreMapRepository;
    private final MemberRepository memberRepository;
    private final OpenStoreUserMapRepository openStoreUserMapRepository;
    private final StockMgmtService stockMgmtService;
    private final AuthenticationUtil authenticationUtil;

    public OpeningStoreMgmtService(OpenStoreRepository openStoreRepository
            , StoreRepository storeRepository ,OpenStoreSaleStoreMapRepository openStoreSaleStoreMapRepository
            ,MemberRepository memberRepository ,OpenStoreUserMapRepository openStoreUserMapRepository
           , StockMgmtService stockMgmtService ,AuthenticationUtil authenticationUtil) {
        this.openStoreRepository = openStoreRepository;
        this.storeRepository = storeRepository;
        this.openStoreSaleStoreMapRepository = openStoreSaleStoreMapRepository;
        this.memberRepository = memberRepository;
        this.openStoreUserMapRepository = openStoreUserMapRepository;
        this.stockMgmtService = stockMgmtService;
        this.authenticationUtil = authenticationUtil;
    }

    public ResponseDto<OpeningStoreMgmtDto> getOpenStoreList(ComMgmtRequestDto requestDto) {

        requestDto.setParentStoreId(authenticationUtil.getTargetStoreId(requestDto.getParentStoreId()));

        Page<OpenStoreListDto> dataList = openStoreRepository.getSearchPage(requestDto);

        return new ResponseDto(OpeningStoreMgmtDto.class, dataList, "dtoToDto");
    }

    @Transactional
    public void insertOpenStore(OpeningStoreMgmtDto openingStoreMgmtDto) {
        //TODO ifnull return 함수 추가
        long openStoreId = openStoreRepository.save(
                OpenStore.builder()
                        .openStoreId(openingStoreMgmtDto.getOpenStoreId())
                        .storeId(authenticationUtil.getTargetStoreId(openingStoreMgmtDto.getStoreId()))
                        .openStoreName(openingStoreMgmtDto.getOpenStoreName())
                        .telecom(openingStoreMgmtDto.getTelecom())
                        .bizNo(openingStoreMgmtDto.getBizNo())
                        .chargerName(openingStoreMgmtDto.getChargerName())
                        .chargerPhone(openingStoreMgmtDto.getChargerPhone())
                        .returnZipCode(openingStoreMgmtDto.getReturnZipCode())
                        .returnAddr(openingStoreMgmtDto.getReturnAddr())
                        .returnAddrDetail(openingStoreMgmtDto.getReturnAddrDetail())
                        .useYn(StatusEnum.FLAG_N.getStatusMsg())
                        .delYn(StatusEnum.FLAG_N.getStatusMsg())
                        .regiDateTime(LocalDateTime.now())
                    .build()
        ).getOpenStoreId();

        //창고 생성
        stockMgmtService.insertStock(
                StockMgmtDto.builder()
                        .stockId(0)
                        .stockName(openingStoreMgmtDto.getOpenStoreName())
                        .parentStockId(0)
                        .storeId(openStoreId)
                        .stockType(TypeEnum.STOCK_TYPE_O.getStatusCode())
                        .regiStoreId(authenticationUtil.getTargetStoreId(openingStoreMgmtDto.getStoreId()))
                        .chargerName(openingStoreMgmtDto.getChargerName())
                        .chargerPhone(openingStoreMgmtDto.getChargerPhone())
                        .delYn(StatusEnum.FLAG_N.getStatusMsg())
                        .regiUserId(authenticationUtil.getMemberSeq())
                        .regiDateTime(LocalDateTime.now())
                        .updUserId(authenticationUtil.getMemberSeq())
                        .updDateTime(LocalDateTime.now())
                    .build()

        );
    }

    @Transactional
    public void updateOpenStoreInfo(OpeningStoreMgmtDto openingStoreMgmtDto) {

        openingStoreMgmtDto.setStoreId(authenticationUtil.getTargetStoreId(openingStoreMgmtDto.getStoreId()));

        OpenStore openStore = openStoreRepository.findOpenStoreInfo(openingStoreMgmtDto.getOpenStoreId(), openingStoreMgmtDto.getStoreId());

        if (openStore != null) {

            //TODO ifnull return 함수 추가
            openStore.setOpenStoreId(openingStoreMgmtDto.getOpenStoreId());
            openStore.setStoreId(openingStoreMgmtDto.getStoreId());
            openStore.setOpenStoreName(openingStoreMgmtDto.getOpenStoreName());
            openStore.setTelecom(openingStoreMgmtDto.getTelecom());
            openStore.setBizNo(openingStoreMgmtDto.getBizNo());
            openStore.setChargerName(openingStoreMgmtDto.getChargerName());
            openStore.setChargerPhone(openingStoreMgmtDto.getChargerPhone());
            openStore.setReturnZipCode(openingStoreMgmtDto.getReturnZipCode());
            openStore.setReturnAddr(openingStoreMgmtDto.getReturnAddr());
            openStore.setReturnAddrDetail(openingStoreMgmtDto.getReturnAddrDetail());

            if (StringUtils.hasText(openingStoreMgmtDto.getUseYn())) {
                openStore.setUseYn(openingStoreMgmtDto.getUseYn());
            }

            if (StringUtils.hasText(openingStoreMgmtDto.getDelYn())) {
                openStore.setDelYn(openingStoreMgmtDto.getDelYn());
            }
        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    @Transactional
    public void deleteOpenStore(ModelMap reqModelMap) {

        List<Number> delOpenStoreIds = (ArrayList<Number>) reqModelMap.get("delOpenStoreId");
        long storeId = authenticationUtil.getTargetStoreId(Long.parseLong(String.valueOf(reqModelMap.get("storeId"))));

        if (delOpenStoreIds != null
                && delOpenStoreIds.size() > 0) {

            for (Number openStoreId : delOpenStoreIds) {
                OpenStore openStore = openStoreRepository.findByOpenStoreIdAndStoreIdAndDelYn(openStoreId.longValue(), storeId, "N").orElse(null);
                if (openStore != null) {
                    openStore.updateDelYn(openStore, StatusEnum.FLAG_Y.getStatusMsg());
                }
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    @Transactional
    public void updateOpenStoreUse(ModelMap reqModelMap) {

        long openStoreId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("openStoreId")));
        long storeId = authenticationUtil.getTargetStoreId(Long.parseLong(String.valueOf(reqModelMap.getAttribute("storeId"))));
        long authStoreId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("authStoreId")));
        String useYn = String.valueOf(reqModelMap.getAttribute("useYn"));

        //authStoreId 개통점을 생성한 storeId
        //내가 소유한 개통점은 open_store 의 use_yn 변경
        //관리점에서 맵핑해준 개통점은 open_store_sale_store_map 테이블의 사용여부를 변경한다
        if(storeId == authStoreId){
            OpenStore openStore = openStoreRepository.findByOpenStoreIdAndStoreIdAndDelYn(openStoreId, storeId, "N").orElse(null);

            if (openStore != null) {
                openStore.updateUseYn(openStore, useYn);
            } else {
                throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        }else{
            OpenStoreSaleStoreMap openStoreSaleStoreMap = openStoreSaleStoreMapRepository.findByOpenStoreIdAndSaleStoreId(openStoreId, storeId).orElse(null);

            if (openStoreSaleStoreMap != null) {
                openStoreSaleStoreMap.updateUseYn(openStoreSaleStoreMap, useYn);
            } else {
                throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        }
    }

    public OpeningStoreSaleStoreResponseDto getSaleStoreMapInfo(ComMgmtRequestDto requestDto) {

        long targetStoreId = authenticationUtil.getTargetStoreId(requestDto.getStoreId());
        requestDto.setStoreId(targetStoreId);

        //개통점 전체 목록
        List<OpenStoreListDto> openStoreList = openStoreRepository.getOpenStoreList(requestDto);

        //영업점 전체 목록
        List<Store> saleStoreList = storeRepository.findBySaleStore(targetStoreId, QStore.store.storeName.asc());

        //개통점, 영업점 맵핑 목록
        List<OpenStoreSaleStoreMap> mapList = openStoreSaleStoreMapRepository.getMappingList(targetStoreId);

        //맵핑 데이터 없는 sales store에 사용
        List<String[]> emptyOpenStoreList = new ArrayList<>();
        for (OpenStoreListDto openStore : openStoreList) {
            emptyOpenStoreList.add(new String[]{ String.valueOf(openStore.getOpenStoreId()), StatusEnum.FLAG_N.getStatusMsg() });
        }

        //response 데이터 세팅
        OpeningStoreSaleStoreResponseDto responseDto = new OpeningStoreSaleStoreResponseDto();

        responseDto.openStoreList = openStoreList.stream()
                .map(OpeningStoreMgmtDto::dtoToDto)
                .collect(Collectors.toList());

        List<String[]> filterOpenStoreInfos;

        for (Store store : saleStoreList) {

            filterOpenStoreInfos = new ArrayList<>();

            if (mapList.stream().anyMatch(map -> map.getSaleStoreId() == store.getStoreId())) {
                for (OpenStoreListDto openStore : openStoreList) {
                    filterOpenStoreInfos.add(
                            new String[]{String.valueOf(openStore.getOpenStoreId())
                                    , mapList.contains(new OpenStoreSaleStoreMap(openStore.getOpenStoreId(), store.getStoreId())) ? StatusEnum.FLAG_Y.getStatusMsg() : StatusEnum.FLAG_N.getStatusMsg() }
                    );
                }
            } else {
                filterOpenStoreInfos = emptyOpenStoreList;
            }

            responseDto.saleStoreList.add(new OpeningStoreSaleStoreResponseDto.OpenStoreSaleStoreMap(store, filterOpenStoreInfos));
        }

        return responseDto;
    }

    @Transactional
    public void setSalesStoreMapInfo (List<ModelMap> reqModelMap){

        if (reqModelMap != null
                && reqModelMap.size() > 0) {

            for(ModelMap reqMap : reqModelMap){
                try {
                    if (StatusEnum.FLAG_Y.getStatusMsg().equals(String.valueOf(reqMap.get("mapYn")))) {
                        openStoreSaleStoreMapRepository.save(
                                new OpenStoreSaleStoreMap(
                                        Long.parseLong(String.valueOf(reqMap.get("openStoreId")))
                                        ,Long.parseLong(String.valueOf(reqMap.get("saleStoreId")))
                                        ,StatusEnum.FLAG_Y.getStatusMsg()
                                )
                        );
                    } else {
                        openStoreSaleStoreMapRepository.deleteById(
                                new OpenStoreSaleStoreMapPK(
                                        Long.parseLong(String.valueOf(reqMap.get("openStoreId")))
                                        ,Long.parseLong(String.valueOf(reqMap.get("saleStoreId")))
                                )
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    public OpeningStoreUserResponseDto getUserMapInfo(ComMgmtRequestDto requestDto) {

        long targetStoreId = authenticationUtil.getTargetStoreId(requestDto.getStoreId());
        requestDto.setStoreId(targetStoreId);

        //개통점 전체 목록
        List<OpenStoreListDto> openStoreList = openStoreRepository.getOpenStoreList(requestDto);

        //사용자 전체 목록
        List<Member> memberList = memberRepository.findByMember(targetStoreId, QMember.member.name.asc());

        //개통점, 사용자 맵핑 목록
        List<OpenStoreUserMap> mapList = openStoreUserMapRepository.getMappingList(targetStoreId);

        //맵핑 데이터 없는 user에 사용
        List<String[]> emptyOpenStoreList = new ArrayList<>();
        for (OpenStoreListDto openStore : openStoreList) {
            emptyOpenStoreList.add(new String[]{ String.valueOf(openStore.getOpenStoreId()), StatusEnum.FLAG_N.getStatusMsg() });
        }

        //response 데이터 세팅
        OpeningStoreUserResponseDto responseDto = new OpeningStoreUserResponseDto();

        responseDto.openStoreList = openStoreList.stream()
                .map(OpeningStoreMgmtDto::dtoToDto)
                .collect(Collectors.toList());

        List<String[]> filterOpenStoreInfos;

        for (Member member : memberList) {

            filterOpenStoreInfos = new ArrayList<>();

            if (mapList.stream().anyMatch(map -> map.getUserId() == member.getSeq())) {
                for (OpenStoreListDto openStore : openStoreList) {
                    filterOpenStoreInfos.add(
                            new String[]{String.valueOf(openStore.getOpenStoreId())
                                    , mapList.contains(new OpenStoreUserMap(openStore.getOpenStoreId(), member.getSeq())) ? StatusEnum.FLAG_Y.getStatusMsg() : StatusEnum.FLAG_N.getStatusMsg() }
                    );
                }
            } else {
                filterOpenStoreInfos = emptyOpenStoreList;
            }

            responseDto.userList.add(new OpeningStoreUserResponseDto.OpenStoreUserMap(member, filterOpenStoreInfos));
        }

        return responseDto;
    }

    @Transactional
    public void setUserMapInfo (List<ModelMap> reqModelMap){

        if (reqModelMap != null
                && reqModelMap.size() > 0) {

            for(ModelMap reqMap : reqModelMap){
                try {
                    if (StatusEnum.FLAG_Y.getStatusMsg().equals(String.valueOf(reqMap.get("mapYn")))) {
                        openStoreUserMapRepository.save(
                                new OpenStoreUserMap(
                                        Long.parseLong(String.valueOf(reqMap.get("openStoreId")))
                                        ,Long.parseLong(String.valueOf(reqMap.get("userId")))
                                        ,StatusEnum.FLAG_Y.getStatusMsg()
                                )
                        );
                    } else {
                        openStoreUserMapRepository.deleteById(
                                new OpenStoreUserMapPK(
                                        Long.parseLong(String.valueOf(reqMap.get("openStoreId")))
                                        ,Long.parseLong(String.valueOf(reqMap.get("userId")))
                                )
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }
}


































