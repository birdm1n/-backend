package com.daema.rest.commgmt.service;

import com.daema.base.enums.StatusEnum;
import com.daema.commgmt.domain.PubNoti;
import com.daema.commgmt.domain.dto.response.PubNotiMappingDto;
import com.daema.commgmt.domain.dto.response.PubNotiRawDataListDto;
import com.daema.commgmt.repository.PubNotiRawDataRepository;
import com.daema.commgmt.repository.PubNotiRepository;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.ChargeMgmtDto;
import com.daema.rest.commgmt.dto.GoodsMgmtDto;
import com.daema.rest.commgmt.dto.PubNotiMgmtDto;
import com.daema.rest.commgmt.dto.response.PubNotiMgmtResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.exception.UnAuthorizedException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
                        .regiUserId(authenticationUtil.getMemberSeq())
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

        if (authenticationUtil.isAdmin()
                && modelMap.get("pubNotiId") != null) {

            Number pubNotiId = Long.parseLong(modelMap.get("pubNotiId") + "");
            PubNoti pubNoti = pubNotiRepository.findById(pubNotiId).orElse(null);

            if(pubNoti != null) {
                pubNoti.updateDelInfo(pubNoti, authenticationUtil.getMemberSeq(), StatusEnum.FLAG_Y.getStatusMsg());
            } else {
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        }else{
            throw new UnAuthorizedException(ServiceReturnMsgEnum.UNAUTHORIZED.name());
        }
    }

    public List<PubNotiRawDataListDto> getRawDataList() {

        List<PubNotiRawDataListDto> dataList = pubNotiRawDataRepository.searchPubNotiRawData();

        return dataList;
    }
}


































