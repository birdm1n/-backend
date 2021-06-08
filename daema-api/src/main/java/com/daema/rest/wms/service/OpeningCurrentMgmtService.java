package com.daema.rest.wms.service;

import com.daema.rest.base.dto.common.ResponseDto;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.util.AuthenticationUtil;
import com.daema.rest.wms.dto.request.OpeningUpdateReqDto;
import com.daema.wms.domain.Opening;
import com.daema.wms.domain.dto.request.OpeningCurrentRequestDto;
import com.daema.wms.domain.dto.response.OpeningCurrentResponseDto;
import com.daema.wms.repository.OpeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OpeningCurrentMgmtService {

    private final AuthenticationUtil authenticationUtil;
    private final OpeningRepository openingRepository;


    @Transactional(readOnly = true)
    public ResponseDto<OpeningCurrentResponseDto> getOpeningCurrentList(OpeningCurrentRequestDto reqDto) {
        reqDto.setStoreId((authenticationUtil.getStoreId()));
        Page<OpeningCurrentResponseDto> responseDtoPage = openingRepository.getOpeningCurrentList(reqDto);
        return new ResponseDto(responseDtoPage);
    }

    @Transactional
    public void updateCancel(OpeningUpdateReqDto reqDto) {
        Opening opening = openingRepository.findById(reqDto.getOpeningId()).orElseThrow(() ->
                new DataNotFoundException(ServiceReturnMsgEnum.IS_NOT_PRESENT.name())
        );
        opening.updateCancel(reqDto.getCancelDate(), reqDto.getCancelMemo());
    }
}

