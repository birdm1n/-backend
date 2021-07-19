package com.daema.core.scm.domain.customer;


import com.daema.core.scm.domain.appform.AppForm;
import com.daema.core.scm.domain.vo.Address;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.CustomerDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "customerId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "customer", comment = "고객")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "BIGINT UNSIGNED comment '고객 아이디'")
    private Long customerId;

    @Column(name = "regist_num", columnDefinition = "INT comment '주민등록번호'")
    private int registNo;

    @Column(name = "charge_reduct", columnDefinition = "INT comment '요금 감면'")
    private int chargeReduct;

    @Column(name = "phone_num", columnDefinition = "INT comment '휴대폰 번호'")
    private int phoneNo;

    @Column(name = "emg_phone", columnDefinition = "INT comment '비상연락망'")
    private int emgPhone;

    @Column(name = "email", columnDefinition = "varchar(255) comment '이메일'")
    private String email;

    @Embedded
    private Address cusAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", columnDefinition = "varchar(255) comment '고객 유형'")
    private ScmEnum.CustomerType customerType;

    @Embedded
    private LicenseAuth licenseAuth;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "court_proctor_id", columnDefinition = "BIGINT UNSIGNED comment '법정 대리인 아이디'")
    private CourtProctor courtProctor;

    @OneToOne(mappedBy = "customer")
    private AppForm appform;


    public static Customer create(CustomerDto customerDto) {
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
                .courtProctor(CourtProctor.toEntity(customerDto.getCourtProctorDto()))
                .build();
    }

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
                .courtProctorDto(CourtProctor.from(customer.getCourtProctor()))
                .build();
    }
    /*public void updateCustomer(CustomerDto customerDto)
    {
        this.customerType = customerDto.getCustomerType();
        this.registNo = customerDto.getRegistNo();
        this.chargeReduct = customerDto.getChargeReduct();
        this.phoneNo = customerDto.getPhoneNo();
        this.emgPhone = customerDto.getEmgPhone();
        this.email = customerDto.getEmail();
        this.cusAddress = customerDto.getCusAddress();
        this.customerType = customerDto.getCustomerType();
        this.licenseAuth = customerDto.getLicenseAuth();

    }*/

    public static void update(Customer Customer, CustomerDto customerDto) {

        Customer.setChargeReduct(customerDto.getChargeReduct());
        Customer.setCustomerType(customerDto.getCustomerType());
        Customer.setCusAddress(customerDto.getCusAddress());
        Customer.setCourtProctor(CourtProctor.toEntity(customerDto.getCourtProctorDto()));
        Customer.setPhoneNo(customerDto.getPhoneNo());
        Customer.setLicenseAuth(customerDto.getLicenseAuth());
        Customer.setRegistNo(customerDto.getRegistNo());
        Customer.setEmail(customerDto.getEmail());
        Customer.setEmgPhone(customerDto.getEmgPhone());

    }
    }


