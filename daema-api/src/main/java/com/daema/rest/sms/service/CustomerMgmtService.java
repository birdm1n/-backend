package com.daema.rest.sms.service;

import com.daema.core.sms.domain.Customer;
import com.daema.core.sms.dto.CustomerDto;
import com.daema.core.sms.dto.request.AppFormReqDto;

import com.daema.core.sms.repository.CustomerRepository;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.util.AuthenticationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CustomerMgmtService {

    private final CustomerRepository customerRepository;
    private final AuthenticationUtil authenticationUtil;

    @Transactional
    public ResponseCodeEnum insertCustomer(AppFormReqDto appFormReqDto) {
        Customer customer = CustomerDto.buildEntity(appFormReqDto.getCustomerDto());


        if(customer == null) {
            return ResponseCodeEnum.FAIL;
        }
        customerRepository.save(customer);
        return ResponseCodeEnum.OK;
    }



}

