package com.daema.service;

import com.daema.common.enums.StatusEnum;
import com.daema.common.util.AuthenticationUtil;
import com.daema.common.util.CommonUtil;
import com.daema.domain.PubNoti;
import com.daema.domain.PubNotiRawData;
import com.daema.domain.dto.PubNotiMappingDto;
import com.daema.dto.*;
import com.daema.dto.common.ResponseDto;
import com.daema.repository.PubNotiRawDataRepository;
import com.daema.repository.PubNotiRepository;
import com.daema.response.enums.ServiceReturnMsgEnum;
import com.daema.response.exception.ProcessErrorException;
import com.daema.response.exception.UnAuthorizedException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PubNotiMgmtService {

    private final PubNotiRepository pubNotiRepository;
    private final PubNotiRawDataRepository pubNotiRawDataRepository;

    private final AuthenticationUtil authenticationUtil;

    public PubNotiMgmtService(PubNotiRepository pubNotiRepository, PubNotiRawDataRepository pubNotiRawDataRepository,  AuthenticationUtil authenticationUtil) {
        this.pubNotiRepository = pubNotiRepository;
        this.pubNotiRawDataRepository = pubNotiRawDataRepository;
        this.authenticationUtil = authenticationUtil;
    }

    public PubNotiMgmtResponseDto getList(int telecom, int network) {

        HashMap<String, List> pubNotiMap = pubNotiRepository.getMappingList(telecom, network);

        PubNotiMgmtResponseDto responseDto = new PubNotiMgmtResponseDto();

        responseDto.setGoodsList(CommonUtil.entityToDto(GoodsMgmtDto.class, pubNotiMap.get("goods"), "from"));
        responseDto.setChargeList(CommonUtil.entityToDto(ChargeMgmtDto.class, pubNotiMap.get("charge"), "from"));

        HashMap<String, PubNotiMappingDto> mapData = new HashMap<>();

        Optional.ofNullable(pubNotiMap.get("mapping"))
                .orElseGet(ArrayList::new)
                .forEach(
                        data -> {
                            PubNotiMappingDto peek = ((PubNotiMappingDto) data);
                            mapData.put(peek.getChargeId() + "_" + peek.getGoodsId(), peek);
                        }
                );
        responseDto.setMappingData(mapData);

        return responseDto;
    }

    /**
     * pub_noti_map 에 데이터 생성 후, pub_noti 에 pub_noti_map_id 적용
     * 관리자가 신규 등록시 originkey 는 A 로 통일
     */
    @Transactional
    public void insertData(PubNotiMgmtDto pubNotiMgmtDto) {
        //TODO ifnull return 함수 추가
        pubNotiRepository.save(
                PubNoti.builder()
                        .releaseAmt(pubNotiMgmtDto.getReleaseAmt())
                        .supportAmt(pubNotiMgmtDto.getSupportAmt())
                        .releaseDate(pubNotiMgmtDto.getReleaseDate())
                        .chargeId(pubNotiMgmtDto.getChargeId())
                        .goodsId(pubNotiMgmtDto.getGoodsId())
                        .originKey("A")
                        //TODO 하드코딩
                        .regiUserId(1)
                        .regiDateTime(LocalDateTime.now())
                        .delYn(StatusEnum.FLAG_N.getStatusMsg())
                        .build()
        );
    }

    public ResponseDto<PubNotiMgmtDto> getHistoryList(long chargeId, long goodsId) {

        List<PubNoti> pubNotiList = pubNotiRepository.getHistoryList(
                PubNoti.builder()
                        .chargeId(chargeId)
                        .goodsId(goodsId)
                        .build()
        );

        return new ResponseDto(PubNotiMgmtDto.class, pubNotiList);
    }

    @Transactional
    public void deleteData(ModelMap modelMap) {

        //TODO 관리자인 경우만 삭제 가능. 현재 로그인 없어서 관리자 아닐때 삭제 하도록 처리함
        //if (authenticationUtil.isAdmin()) {
        if (!authenticationUtil.isAdmin()
            && modelMap.get("pubNotiId") != null) {

            Number pubNotiId = Long.parseLong(modelMap.get("pubNotiId") + "");
            PubNoti pubNoti = pubNotiRepository.findById(pubNotiId).orElse(null);

            if(pubNoti != null) {
                //TODO security 설정에 따라 userId 가져오는 방식 변경 필요
                //pubNoti.updateDelInfo(pubNoti, authenticationUtil.getId("userId"), StatusEnum.FLAG_Y.getStatusMsg());
                pubNoti.updateDelInfo(pubNoti, 1, StatusEnum.FLAG_Y.getStatusMsg());
            } else {
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        }else{
            throw new UnAuthorizedException(ServiceReturnMsgEnum.UNAUTHORIZED.name());
        }
    }

    public ResponseDto<PubNotiRawDataMgmtDto> getRawDataList() {

        Sort sort = Sort.by(Sort.Direction.DESC, "telecomName")
                .and(Sort.by(Sort.Direction.DESC, "networkName"))
                .and(Sort.by(Sort.Direction.DESC, "goodsName"))
                .and(Sort.by(Sort.Direction.DESC, "chargeName"));

        List<PubNotiRawData> dataList = pubNotiRawDataRepository.findByDeadLineYn("N", sort);

        return new ResponseDto(PubNotiRawDataMgmtDto.class, dataList);
    }

    private <E> boolean isNotEmptyList(Collection<E> itemList){
        return itemList != null && itemList.size() > 0;
    }
}


































