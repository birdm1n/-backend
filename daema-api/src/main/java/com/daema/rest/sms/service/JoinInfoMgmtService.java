package com.daema.rest.sms.service;


import com.daema.core.sms.domain.Addition;
import com.daema.core.sms.domain.JoinAddition;
import com.daema.core.sms.domain.JoinInfo;
import com.daema.core.sms.dto.JoinInfoDto;
import com.daema.core.sms.dto.request.AppFormReqDto;
import com.daema.core.sms.repository.JoinInfoAdditionRepository;
import com.daema.core.sms.repository.JoinInfoRepository;
import com.daema.rest.common.enums.ResponseCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JoinInfoMgmtService {


    private final JoinInfoRepository joinInfoRepository;
    private final JoinInfoAdditionRepository joinInfoAdditionRepository;

    @Transactional
    public ResponseCodeEnum insertJoinInfo(AppFormReqDto appFormReqDto) {
        JoinInfo joinInfo = JoinInfoDto.toEntity(appFormReqDto.getJoinInfoDto());
        if (joinInfo == null) {
            return ResponseCodeEnum.FAIL;
        }


        JoinAddition joinAddition = JoinAddition.builder()
                .joinInfo(JoinInfo.builder()
                        .joinInfoId(joinInfoRepository.save(joinInfo).getJoinInfoId())
                        .build())
                .addition(Addition.builder()
                        .additionId(appFormReqDto.getJoinInfoDto().getAdditionId())
                        .build())
                .build();

        joinInfoAdditionRepository.save(joinAddition);



        return ResponseCodeEnum.OK;
    }


}
