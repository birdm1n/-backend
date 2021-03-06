package com.daema.rest.commgmt.service;

import com.daema.core.base.enums.StatusEnum;
import com.daema.core.commgmt.domain.PubNoti;
import com.daema.core.commgmt.dto.response.PubNotiMappingDto;
import com.daema.core.commgmt.dto.response.PubNotiRawDataListDto;
import com.daema.core.commgmt.repository.PubNotiRawDataRepository;
import com.daema.core.commgmt.repository.PubNotiRepository;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.core.commgmt.dto.ChargeMgmtDto;
import com.daema.core.commgmt.dto.GoodsMgmtDto;
import com.daema.core.commgmt.dto.PubNotiMgmtDto;
import com.daema.core.commgmt.dto.response.PubNotiMgmtResponseDto;
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

    public PubNotiMgmtResponseDto getList(Long telecom, Long network) {

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
     * pub_noti_map ??? ????????? ?????? ???, pub_noti ??? pub_noti_map_id ??????
     * ???????????? ?????? ????????? originkey ??? A ??? ??????
     */
    @Transactional
    public void insertData(PubNotiMgmtDto pubNotiMgmtDto) {
        //TODO ifnull return ?????? ??????
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


































