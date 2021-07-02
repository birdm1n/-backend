package com.daema.core.sms.dto;

import com.daema.core.sms.domain.CourtProctor;
import com.daema.core.sms.domain.Customer;
import com.daema.core.sms.domain.VO.Address;
import com.daema.core.sms.domain.VO.LicenseAuth;
import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Long customerId;
    private int registNo;
    private int chargeReduct;
    private int phoneNo;
    private int emgPhone;
    private String email;
    private Address cusAddress;
    private SmsEnum.CustomerType customerType;
    private LicenseAuth licenseAuth;
    private CourtProctor courtProctor;

    public static CustomerDto from(Customer customer) {
        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .registNo(customer.getRegistNo())
                .chargeReduct(customer.getChargeReduct())
                .phoneNo(customer.getPhoneNo())
                .emgPhone(customer.getEmgPhone())
                .email(customer.getEmail())
                .cusAddress(customer.getCusAddress())
                .customerType(customer.getCustomerType())
                .licenseAuth(customer.getLicenseAuth())
                .courtProctor(customer.getCourtProctor())
                .build();
    }

    public static Customer toEntity(CustomerDto customerDto) {
        return Customer.builder()
                .customerId(customerDto.getCustomerId())
                .registNo(customerDto.getRegistNo())
                .chargeReduct(customerDto.getChargeReduct())
                .phoneNo(customerDto.getPhoneNo())
                .emgPhone(customerDto.getEmgPhone())
                .email(customerDto.getEmail())
                .cusAddress(customerDto.getCusAddress())
                .customerType(customerDto.getCustomerType())
                .licenseAuth(customerDto.getLicenseAuth())
                .courtProctor(customerDto.getCourtProctor())
                .build();
    }

}
