/*
package com.daema.rest.sms.service;

import com.daema.core.sms.domain.CourtProctor;
import com.daema.core.sms.domain.Customer;
import com.daema.core.sms.domain.VO.Address;
import com.daema.core.sms.domain.VO.LicenseAuth;
import com.daema.core.sms.dto.CustomerDto;
import com.daema.core.sms.dto.request.FillOutApplicationRequestDto;
import com.daema.core.sms.repository.CustomerRepository;
import com.daema.core.wms.domain.Stock;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.util.AuthenticationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.daema.core.sms.domain.QCourtProctor.courtProctor;

@RequiredArgsConstructor
@Service
public class CustomerMgmtService {

    private final CustomerRepository customerRepository;
    private final AuthenticationUtil authenticationUtil;

 @Transactional
    public ResponseCodeEnum insertCustomer(FillOutApplicationRequestDto fillOutApplicationRequestDto) {

     Address cusAddress = new Address(fillOutApplicationRequestDto.getCusCity(),
             fillOutApplicationRequestDto.getCusStreet(),
             fillOutApplicationRequestDto.getCusZipcode());
     LicenseAuth licenseAuth = new LicenseAuth(fillOutApplicationRequestDto.getCusLicenseType(),
             fillOutApplicationRequestDto.getCusIssueDate(),
             fillOutApplicationRequestDto.getCusExpiredDate(),
             fillOutApplicationRequestDto.getCusForeignRegiNo(),
             fillOutApplicationRequestDto.getCusArea(),
             fillOutApplicationRequestDto.getCusLicenseNo(),
             fillOutApplicationRequestDto.getCusStayCode(),
             fillOutApplicationRequestDto.getCusCountry());

     CourtProctor courtProctor = CourtProctor.builder()
             .courtProctorId(fillOutApplicationRequestDto.getCourtProctorId())
             .build();

    Customer customer =
     Customer.builder()
             .customerId(fillOutApplicationRequestDto.getCustomerId())
             .registNo(fillOutApplicationRequestDto.getCusRegistNo())
             .chargeReduct(fillOutApplicationRequestDto.getChargeReduct())
             .phoneNo(fillOutApplicationRequestDto.getCusPhoneNo())
             .emgPhone(fillOutApplicationRequestDto.getCusEmgPhone())
             .email(fillOutApplicationRequestDto.getCusEmail())
             .cusAddress(cusAddress)
             .customerType(fillOutApplicationRequestDto.getCustomerType())
             .licenseAuth(licenseAuth)
             .courtProctor(courtProctor)
             .build();


     if(customer == null) {
         return ResponseCodeEnum.FAIL;
     }
     customerRepository.save(customer);
        return ResponseCodeEnum.OK;
    }



}

*/
