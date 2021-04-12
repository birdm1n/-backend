package com.daema.service;

import com.daema.common.enums.StatusEnum;
import com.daema.domain.*;
import com.daema.domain.pk.OpenStoreSaleStoreMapPK;
import com.daema.domain.pk.OpenStoreUserMapPK;
import com.daema.dto.OpeningStoreMgmtDto;
import com.daema.dto.OpeningStoreMgmtRequestDto;
import com.daema.dto.OpeningStoreSaleStoreResponseDto;
import com.daema.dto.OpeningStoreUserResponseDto;
import com.daema.dto.common.ResponseDto;
import com.daema.repository.*;
import com.daema.response.enums.ServiceReturnMsgEnum;
import com.daema.response.exception.DataNotFoundException;
import com.daema.response.exception.ProcessErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public OpeningStoreMgmtService(OpenStoreRepository openStoreRepository
            , StoreRepository storeRepository ,OpenStoreSaleStoreMapRepository openStoreSaleStoreMapRepository
            ,MemberRepository memberRepository ,OpenStoreUserMapRepository openStoreUserMapRepository) {
        this.openStoreRepository = openStoreRepository;
        this.storeRepository = storeRepository;
        this.openStoreSaleStoreMapRepository = openStoreSaleStoreMapRepository;
        this.memberRepository = memberRepository;
        this.openStoreUserMapRepository = openStoreUserMapRepository;
    }

    public ResponseDto<OpeningStoreMgmtDto> getOpenStoreList(OpeningStoreMgmtRequestDto requestDto) {

        PageRequest pageable = PageRequest.of(requestDto.getPageNo(), requestDto.getPerPageCnt());

        Page<OpenStore> dataList = openStoreRepository.getSearchPage(pageable);

        return new ResponseDto(OpeningStoreMgmtDto.class, dataList);
    }

    public void insertOpenStore(OpeningStoreMgmtDto openingStoreMgmtDto) {
        //TODO ifnull return 함수 추가
        openStoreRepository.save(
                OpenStore.builder()
                        .openStoreId(openingStoreMgmtDto.getOpenStoreId())
                        .storeId(openingStoreMgmtDto.getStoreId())
                        .openStoreName(openingStoreMgmtDto.getOpenStoreName())
                        .telecom(openingStoreMgmtDto.getTelecom())
                        .bizNo(openingStoreMgmtDto.getBizNo())
                        .chargerPhone(openingStoreMgmtDto.getChargerPhone())
                        .returnZipCode(openingStoreMgmtDto.getReturnZipCode())
                        .returnAddr(openingStoreMgmtDto.getReturnAddr())
                        .returnAddrDetail(openingStoreMgmtDto.getReturnAddrDetail())
                        .useYn(openingStoreMgmtDto.getUseYn())
                        .delYn(openingStoreMgmtDto.getDelYn())
                        .regiDateTime(LocalDateTime.now())
                        .build()
        );
    }

    @Transactional
    public void updateOpenStoreInfo(OpeningStoreMgmtDto openingStoreMgmtDto) {

        OpenStore openStore = openStoreRepository.findOpenStoreInfo(openingStoreMgmtDto.getOpenStoreId(), openingStoreMgmtDto.getStoreId());

        if (openStore != null) {

            //TODO ifnull return 함수 추가
            openStore.setOpenStoreId(openingStoreMgmtDto.getOpenStoreId());
            openStore.setStoreId(openingStoreMgmtDto.getStoreId());
            openStore.setOpenStoreName(openingStoreMgmtDto.getOpenStoreName());
            openStore.setTelecom(openingStoreMgmtDto.getTelecom());
            openStore.setBizNo(openingStoreMgmtDto.getBizNo());
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
        long storeId = Long.parseLong(String.valueOf(reqModelMap.get("storeId")));

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
        long storeId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("storeId")));
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

    public OpeningStoreSaleStoreResponseDto getSaleStoreMapInfo(long storeId) {

        //개통점 전체 목록
        List<OpenStore> openStoreList = openStoreRepository.getOpenStoreList(storeId);

        //영업점 전체 목록
        List<Store> saleStoreList = storeRepository.findBySaleStore(storeId, QStore.store.storeName.asc());

        //개통점, 영업점 맵핑 목록
        List<OpenStoreSaleStoreMap> mapList = openStoreSaleStoreMapRepository.getMappingList(storeId);

        //맵핑 데이터 없는 sales store에 사용
        List<String[]> emptyOpenStoreList = new ArrayList<>();
        for (OpenStore openStore : openStoreList) {
            emptyOpenStoreList.add(new String[]{ String.valueOf(openStore.getOpenStoreId()), StatusEnum.FLAG_N.getStatusMsg() });
        }

        //response 데이터 세팅
        OpeningStoreSaleStoreResponseDto responseDto = new OpeningStoreSaleStoreResponseDto();

        responseDto.openStoreList = openStoreList.stream()
                .map(openStore -> OpeningStoreMgmtDto.from(openStore))
                .collect(Collectors.toList());

        List<String[]> filterOpenStoreInfos;

        for (Store store : saleStoreList) {

            filterOpenStoreInfos = new ArrayList<>();

            if (mapList.stream().anyMatch(map -> map.getSaleStoreId() == store.getStoreId())) {
                for (OpenStore openStore : openStoreList) {
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

    public OpeningStoreUserResponseDto getUserMapInfo(long storeId) {

        //개통점 전체 목록
        List<OpenStore> openStoreList = openStoreRepository.getOpenStoreList(storeId);

        //사용자 전체 목록
        List<Member> memberList = memberRepository.findByMember(storeId, QMember.member.name.asc());

        //개통점, 사용자 맵핑 목록
        List<OpenStoreUserMap> mapList = openStoreUserMapRepository.getMappingList(storeId);

        //맵핑 데이터 없는 user에 사용
        List<String[]> emptyOpenStoreList = new ArrayList<>();
        for (OpenStore openStore : openStoreList) {
            emptyOpenStoreList.add(new String[]{ String.valueOf(openStore.getOpenStoreId()), StatusEnum.FLAG_N.getStatusMsg() });
        }

        //response 데이터 세팅
        OpeningStoreUserResponseDto responseDto = new OpeningStoreUserResponseDto();

        responseDto.openStoreList = openStoreList.stream()
                .map(openStore -> OpeningStoreMgmtDto.from(openStore))
                .collect(Collectors.toList());

        List<String[]> filterOpenStoreInfos;

        for (Member member : memberList) {

            filterOpenStoreInfos = new ArrayList<>();

            if (mapList.stream().anyMatch(map -> map.getUserId() == member.getSeq())) {
                for (OpenStore openStore : openStoreList) {
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


































