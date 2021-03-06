package com.daema.rest.commgmt.service;

import com.daema.core.base.enums.StatusEnum;
import com.daema.core.commgmt.domain.Charge;
import com.daema.core.commgmt.domain.ChargeRegReq;
import com.daema.core.commgmt.domain.ChargeRegReqReject;
import com.daema.core.commgmt.domain.attr.NetworkAttribute;
import com.daema.core.commgmt.dto.request.ComMgmtRequestDto;
import com.daema.core.commgmt.repository.ChargeRegReqRejectRepository;
import com.daema.core.commgmt.repository.ChargeRegReqRepository;
import com.daema.core.commgmt.repository.ChargeRepository;
import com.daema.rest.base.dto.response.ResponseDto;
import com.daema.core.commgmt.dto.ChargeMgmtDto;
import com.daema.core.commgmt.dto.ChargeRegReqDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.common.util.CommonUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChargeMgmtService {

    private final ChargeRepository chargeRepository;
    private final ChargeRegReqRepository chargeRegReqRepository;
    private final ChargeRegReqRejectRepository chargeRegReqRejectRepository;

    private final AuthenticationUtil authenticationUtil;

    public ChargeMgmtService(ChargeRepository chargeRepository, ChargeRegReqRepository chargeRegReqRepository
                            , ChargeRegReqRejectRepository chargeRegReqRejectRepository, AuthenticationUtil authenticationUtil) {
        this.chargeRepository = chargeRepository;
        this.chargeRegReqRepository = chargeRegReqRepository;
        this.chargeRegReqRejectRepository = chargeRegReqRejectRepository;
        this.authenticationUtil = authenticationUtil;
    }

    public ResponseDto<ChargeMgmtDto> getList(ComMgmtRequestDto requestDto) {

        //????????? ??? ???????????? useYn = Y ????????? ??????
        Page<Charge> chargeList = chargeRepository.getSearchPage(requestDto, authenticationUtil.isAdmin());

        return new ResponseDto(ChargeMgmtDto.class, chargeList);
    }

    /**
     * ????????? ????????? : charge ?????????
     * ?????? ????????? : charge_reg_req ?????????
     */
    public void insertData(ChargeMgmtDto chargeMgmtDto) {
        //TODO ifnull return ?????? ??????
        if (authenticationUtil.isAdmin()) {
            chargeRepository.save(
                    Charge.builder()
                            .chargeName(chargeMgmtDto.getChargeName())
                            .chargeAmt(chargeMgmtDto.getChargeAmt())
                            .category(chargeMgmtDto.getCategory())
                            .telecom(chargeMgmtDto.getTelecom())
                            .network(chargeMgmtDto.getNetwork())
                            .originKey(chargeMgmtDto.getOriginKey())
                            .chargeCode(chargeMgmtDto.getChargeCode())
                            .useYn(chargeMgmtDto.getUseYn())
                            .matchingYn(chargeMgmtDto.getMatchingYn())
                            .delYn(chargeMgmtDto.getDelYn())
                            .regiDateTime(LocalDateTime.now())
                            .build()
            );
        } else {
            chargeRegReqRepository.save(
                ChargeRegReq.builder()
                        .chargeName(chargeMgmtDto.getChargeName())
                        .chargeAmt(chargeMgmtDto.getChargeAmt())
                        .category(chargeMgmtDto.getCategory())
                        .telecom(chargeMgmtDto.getTelecom())
                        .network(chargeMgmtDto.getNetwork())
                        .chargeCode(chargeMgmtDto.getChargeCode())
                        .reqStoreId(authenticationUtil.getStoreId())
                        .reqStatus(String.valueOf(StatusEnum.REG_REQ.getStatusCode()))
                        .regiDateTime(LocalDateTime.now())
                    .build()
            );
        }
    }

    @Transactional
    public void updateData(ChargeMgmtDto chargeMgmtDto) {

        Charge charge = chargeRepository.findById(chargeMgmtDto.getChargeId()).orElse(null);

        if (charge != null) {

            //TODO ifnull return ?????? ??????
            charge.setChargeId(chargeMgmtDto.getChargeId());
            charge.setChargeName(chargeMgmtDto.getChargeName());
            charge.setChargeAmt(chargeMgmtDto.getChargeAmt());
            charge.setCategory(chargeMgmtDto.getCategory());
            charge.setNetworkAttribute(new NetworkAttribute(chargeMgmtDto.getTelecom(), chargeMgmtDto.getNetwork()));
            charge.setChargeCode(chargeMgmtDto.getChargeCode());

            if (StringUtils.hasText(chargeMgmtDto.getOriginKey())) {
                charge.setOriginKey(chargeMgmtDto.getOriginKey());
            }
            if (StringUtils.hasText(chargeMgmtDto.getUseYn())) {
                charge.setUseYn(chargeMgmtDto.getUseYn());
            }
            if (StringUtils.hasText(chargeMgmtDto.getMatchingYn())) {
                charge.setMatchingYn(chargeMgmtDto.getMatchingYn());
            }
            if (StringUtils.hasText(chargeMgmtDto.getDelYn())) {
                charge.setDelYn(chargeMgmtDto.getDelYn());
            }
        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    @Transactional
    public void deleteData(ModelMap reqModelMap) {

        List<Number> chargeIds = (ArrayList<Number>) reqModelMap.get("chargeId");

        if (CommonUtil.isNotEmptyList(chargeIds)) {

            List<Charge> chargeList = chargeRepository.findAllById(
                    chargeIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );

            if(CommonUtil.isNotEmptyList(chargeList)) {
                Optional.ofNullable(chargeList).orElseGet(Collections::emptyList).forEach(charge -> {
                    charge.updateDelYn(charge, StatusEnum.FLAG_Y.getStatusMsg());
                });
            }else{
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }

    @Transactional
    public void updateUseYn(ModelMap reqModelMap) {

        long chargeId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("chargeId")));
        String useYn = String.valueOf(reqModelMap.getAttribute("useYn"));

        Charge charge = chargeRepository.findById(chargeId).orElse(null);

        if (charge != null) {
            charge.updateUseYn(charge, useYn);
        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    public ResponseDto<ChargeRegReqDto> getRegReqList(ComMgmtRequestDto requestDto) {

        requestDto.setStoreId(authenticationUtil.getStoreId());

        //????????? ??? ???????????? ?????? req_store_id ????????? ??????
        Page<ChargeRegReq> chargeList = chargeRegReqRepository.getSearchPage(requestDto, authenticationUtil.isAdmin());

        return new ResponseDto(ChargeRegReqDto.class, chargeList);
    }

    @Transactional
    public void updateReqStatus(ChargeRegReqDto chargeRegReqDto) {

        ChargeRegReq chargeRegReq = chargeRegReqRepository.findById(chargeRegReqDto.getChargeRegReqId()).orElse(null);

        if(chargeRegReq != null) {
            chargeRegReq.updateReqStatus(chargeRegReq, chargeRegReqDto.getReqStatus());

            if(Integer.parseInt(chargeRegReqDto.getReqStatus()) == StatusEnum.REG_REQ_APPROVAL.getStatusCode()) {
                //insertData ?????? ??????. ?????? ?????? ????????? ???????????????????????? ?????? ?????? ????????? ??? ??????
                chargeRepository.save(
                        Charge.builder()
                                .chargeName(chargeRegReq.getChargeName())
                                .chargeAmt(chargeRegReq.getChargeAmt())
                                .category(chargeRegReq.getCategory())
                                .telecom(chargeRegReq.getNetworkAttribute().getTelecom())
                                .network(chargeRegReq.getNetworkAttribute().getNetwork())
                                .originKey("R".concat(String.valueOf(chargeRegReq.getChargeRegReqId())))
                                .chargeCode(chargeRegReq.getChargeCode())
                                .regiDateTime(LocalDateTime.now())
                                .useYn(StatusEnum.FLAG_N.getStatusMsg())
                                .matchingYn(StatusEnum.FLAG_N.getStatusMsg())
                                .delYn(StatusEnum.FLAG_N.getStatusMsg())
                                .build()
                );
            }else if(Integer.parseInt(chargeRegReqDto.getReqStatus()) == StatusEnum.REG_REQ_REJECT.getStatusCode()){
                ChargeRegReqReject chargeRegReqReject = new ChargeRegReqReject();
                chargeRegReqReject.setChargeRegReqId(chargeRegReq.getChargeRegReqId());
                chargeRegReqReject.setRejectComment(chargeRegReqDto.getRegReqRejectDto().getRejectComment());
                chargeRegReqReject.setRejectDateTime(LocalDateTime.now());
                chargeRegReqReject.setRejectUserId(authenticationUtil.getMemberSeq());

                chargeRegReqRejectRepository.save(chargeRegReqReject);
            }else{
                throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
            }
        }else{
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    public ResponseDto<ChargeMgmtDto> getMatchList(ComMgmtRequestDto requestDto) {

        List<Charge> chargeList = chargeRepository.getMatchList(requestDto);

        return new ResponseDto(ChargeMgmtDto.class, chargeList);
    }

    @Transactional
    public void applyMatchStatus(ModelMap reqModelMap) {

        List<Number>  groupChargeIds = (ArrayList<Number>) reqModelMap.get("groupChargeId");
        Integer useChargeId = (Integer) reqModelMap.get("useChargeId");

        if (CommonUtil.isNotEmptyList(groupChargeIds)) {

            List<Charge> chargeList = chargeRepository.findAllById(
                    groupChargeIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );

            if(CommonUtil.isNotEmptyList(chargeList)) {
                Optional.ofNullable(chargeList).orElseGet(Collections::emptyList).forEach(charge -> {

                    charge.updateMatchYn(charge, StatusEnum.FLAG_Y.getStatusMsg());

                    if(charge.getChargeId() == useChargeId){
                        charge.updateUseYn(charge, StatusEnum.FLAG_Y.getStatusMsg());
                    }else{
                        charge.updateUseYn(charge, StatusEnum.FLAG_N.getStatusMsg());
                    }
                });
            }else{
                throw new ProcessErrorException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
            }
        } else {
            throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
        }
    }
}


































