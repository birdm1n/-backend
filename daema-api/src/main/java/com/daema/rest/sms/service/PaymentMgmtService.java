package com.daema.rest.sms.service;


import com.daema.core.sms.domain.*;
import com.daema.core.sms.dto.*;
import com.daema.core.sms.dto.request.AppFormReqDto;
import com.daema.core.sms.repository.PaymentRepository;
import com.daema.rest.common.enums.ResponseCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentMgmtService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public ResponseCodeEnum insertPayment(AppFormReqDto appFormReqDto) {
        Payment payment = PaymentDto.buildEntity(appFormReqDto.getPaymentDto());

        if (payment == null) {
            return ResponseCodeEnum.FAIL;
        }
        paymentRepository.save(payment);
        return ResponseCodeEnum.OK;
    }
}
