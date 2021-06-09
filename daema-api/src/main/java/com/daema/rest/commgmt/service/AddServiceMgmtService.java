package com.daema.rest.commgmt.service;

import com.daema.base.enums.StatusEnum;
import com.daema.commgmt.domain.AddService;
import com.daema.commgmt.domain.AddServiceRegReq;
import com.daema.commgmt.domain.AddServiceRegReqReject;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.commgmt.repository.AddServiceRegReqRejectRepository;
import com.daema.commgmt.repository.AddServiceRegReqRepository;
import com.daema.commgmt.repository.AddServiceRepository;
import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.commgmt.dto.AddServiceMgmtDto;
import com.daema.rest.commgmt.dto.AddServiceRegReqDto;
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
public class AddServiceMgmtService {

    private final AddServiceRepository addServiceRepository;
    private final AddServiceRegReqRepository addServiceRegReqRepository;
    private final AddServiceRegReqRejectRepository addServiceRegReqRejectRepository;

    private final AuthenticationUtil authenticationUtil;

    public AddServiceMgmtService(AddServiceRepository addServiceRepository, AddServiceRegReqRepository addServiceRegReqRepository
                            , AddServiceRegReqRejectRepository addServiceRegReqRejectRepository, AuthenticationUtil authenticationUtil) {
        this.addServiceRepository = addServiceRepository;
        this.addServiceRegReqRepository = addServiceRegReqRepository;
        this.addServiceRegReqRejectRepository = addServiceRegReqRejectRepository;
        this.authenticationUtil = authenticationUtil;
    }

    public ResponseDto<AddServiceMgmtDto> getList(ComMgmtRequestDto requestDto) {

        //관리자 외 사용자는 useYn = Y 정보만 출력
        Page<AddService> addServiceList = addServiceRepository.getSearchPage(requestDto, authenticationUtil.isAdmin());

        return new ResponseDto(AddServiceMgmtDto.class, addServiceList);
    }

    /**
     * 시스템 관리자 : addService 테이블
     * 일반 사용자 : addService_reg_req 테이블
     */
    public void insertData(AddServiceMgmtDto addServiceMgmtDto) {
        //TODO ifnull return 함수 추가
        if (authenticationUtil.isAdmin()) {
            addServiceRepository.save(
                    AddService.builder()
                            .addSvcName(addServiceMgmtDto.getAddSvcName())
                            .addSvcCharge(addServiceMgmtDto.getAddSvcCharge())
                            .addSvcType(addServiceMgmtDto.getAddSvcType())
                            .telecom(addServiceMgmtDto.getTelecom())
                            .originKey(addServiceMgmtDto.getOriginKey())
                            .addSvcMemo(addServiceMgmtDto.getAddSvcMemo())
                            .useYn(addServiceMgmtDto.getUseYn())
                            .delYn(addServiceMgmtDto.getDelYn())
                            .regiDateTime(LocalDateTime.now())
                            .build()
            );
        } else {
            addServiceRegReqRepository.save(
                AddServiceRegReq.builder()
                        .addSvcName(addServiceMgmtDto.getAddSvcName())
                        .addSvcCharge(addServiceMgmtDto.getAddSvcCharge())
                        .addSvcMemo(addServiceMgmtDto.getAddSvcMemo())
                        .addSvcType(addServiceMgmtDto.getAddSvcType())
                        .telecom(addServiceMgmtDto.getTelecom())
                        .reqStoreId(authenticationUtil.getStoreId())
                        .reqStatus(String.valueOf(StatusEnum.REG_REQ.getStatusCode()))
                        .regiDateTime(LocalDateTime.now())
                    .build()
            );
        }
    }

    @Transactional
    public void updateData(AddServiceMgmtDto addServiceMgmtDto) {

        AddService addService = addServiceRepository.findById(addServiceMgmtDto.getAddSvcId()).orElse(null);

        if (addService != null) {

            //TODO ifnull return 함수 추가
            addService.setAddSvcId(addServiceMgmtDto.getAddSvcId());
            addService.setAddSvcName(addServiceMgmtDto.getAddSvcName());
            addService.setAddSvcCharge(addServiceMgmtDto.getAddSvcCharge());
            addService.setAddSvcMemo(addServiceMgmtDto.getAddSvcMemo());
            addService.setAddSvcType(addServiceMgmtDto.getAddSvcType());
            addService.setTelecom(addServiceMgmtDto.getTelecom());

            if (StringUtils.hasText(addServiceMgmtDto.getOriginKey())) {
                addService.setOriginKey(addServiceMgmtDto.getOriginKey());
            }
            if (StringUtils.hasText(addServiceMgmtDto.getUseYn())) {
                addService.setUseYn(addServiceMgmtDto.getUseYn());
            }
            if (StringUtils.hasText(addServiceMgmtDto.getDelYn())) {
                addService.setDelYn(addServiceMgmtDto.getDelYn());
            }
        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    @Transactional
    public void deleteData(ModelMap reqModelMap) {

        List<Number> addSvcIds = (ArrayList<Number>) reqModelMap.get("addSvcId");

        if (CommonUtil.isNotEmptyList(addSvcIds)) {

            List<AddService> addServiceList = addServiceRepository.findAllById(
                    addSvcIds.stream()
                            .map(Number::longValue).collect(Collectors.toList())
            );

            if(CommonUtil.isNotEmptyList(addServiceList)) {
                Optional.ofNullable(addServiceList).orElseGet(Collections::emptyList).forEach(addService -> {
                    addService.updateDelYn(addService, StatusEnum.FLAG_Y.getStatusMsg());
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

        long addSvcId = Long.parseLong(String.valueOf(reqModelMap.getAttribute("addSvcId")));
        String useYn = String.valueOf(reqModelMap.getAttribute("useYn"));

        AddService addService = addServiceRepository.findById(addSvcId).orElse(null);

        if (addService != null) {
            addService.updateUseYn(addService, useYn);
        } else {
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }

    public ResponseDto<AddServiceRegReqDto> getRegReqList(ComMgmtRequestDto requestDto) {

        requestDto.setStoreId(authenticationUtil.getStoreId());

        //관리자 외 사용자는 해당 req_store_id 정보만 출력
        Page<AddServiceRegReq> addServiceList = addServiceRegReqRepository.getSearchPage(requestDto, authenticationUtil.isAdmin());

        return new ResponseDto(AddServiceRegReqDto.class, addServiceList);
    }

    @Transactional
    public void updateReqStatus(AddServiceRegReqDto addServiceRegReqDto) {

        AddServiceRegReq addServiceRegReq = addServiceRegReqRepository.findById(addServiceRegReqDto.getAddSvcRegReqId()).orElse(null);

        if(addServiceRegReq != null) {
            addServiceRegReq.updateReqStatus(addServiceRegReq, addServiceRegReqDto.getReqStatus());

            if(Integer.parseInt(addServiceRegReqDto.getReqStatus()) == StatusEnum.REG_REQ_APPROVAL.getStatusCode()) {
                //insertData 사용 안함. 요청 승인 정책이 시스템관리자에서 확장 또는 변경될 수 있음
                addServiceRepository.save(
                        AddService.builder()
                                .addSvcName(addServiceRegReq.getAddSvcName())
                                .addSvcCharge(addServiceRegReq.getAddSvcCharge())
                                .addSvcType(addServiceRegReq.getAddSvcType())
                                .telecom(addServiceRegReq.getTelecom())
                                .originKey("R".concat(String.valueOf(addServiceRegReq.getAddSvcRegReqId())))
                                .addSvcMemo(addServiceRegReq.getAddSvcMemo())
                                .regiDateTime(LocalDateTime.now())
                                .useYn(StatusEnum.FLAG_N.getStatusMsg())
                                .delYn(StatusEnum.FLAG_N.getStatusMsg())
                                .build()
                );
            }else if(Integer.parseInt(addServiceRegReqDto.getReqStatus()) == StatusEnum.REG_REQ_REJECT.getStatusCode()){
                AddServiceRegReqReject addServiceRegReqReject = new AddServiceRegReqReject();
                addServiceRegReqReject.setAddSvcRegReqId(addServiceRegReq.getAddSvcRegReqId());
                addServiceRegReqReject.setRejectComment(addServiceRegReqDto.getRegReqRejectDto().getRejectComment());
                addServiceRegReqReject.setRejectDateTime(LocalDateTime.now());
                addServiceRegReqReject.setRejectUserId(authenticationUtil.getMemberSeq());

                addServiceRegReqRejectRepository.save(addServiceRegReqReject);
            }else{
                throw new ProcessErrorException(ServiceReturnMsgEnum.ILLEGAL_ARGUMENT.name());
            }
        }else{
            throw new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name());
        }
    }
}


































