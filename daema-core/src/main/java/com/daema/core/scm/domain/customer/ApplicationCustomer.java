package com.daema.core.scm.domain.customer;


import com.daema.core.base.domain.common.BaseUserInfoEntity;
import com.daema.core.scm.domain.application.Application;
import com.daema.core.scm.domain.vo.Address;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.dto.ApplicationCustomerDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applId")
@ToString(exclude = {"application"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application_customer", comment = "신청서 고객")
public class ApplicationCustomer extends BaseUserInfoEntity {

    @Id
    private Long applId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appl_id", columnDefinition = "BIGINT UNSIGNED comment '신청서 아이디'")
    @MapsId
    private Application application;

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
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "cus_zip_code", columnDefinition = "varchar(255) comment '고객 우편 코드'")),
            @AttributeOverride(name = "addr", column = @Column(name = "cus_addr", columnDefinition = "varchar(255) comment '고객 주소'")),
            @AttributeOverride(name = "addrDetail", column = @Column(name = "cus_addr_detail", columnDefinition = "varchar(255) comment '고객 주소 상세'"))
    })
    private Address cusAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", columnDefinition = "varchar(255) comment '고객 유형'")
    private ScmEnum.CustomerType customerType;

    @Embedded
    private LicenseAuth licenseAuth;

    @Embedded
    private CustomerCourtProctorAttribute customerCourtProctorAttribute;


    public static ApplicationCustomer create(Application application, ApplicationCustomerDto applicationCustomerDto) {
        return ApplicationCustomer.builder()
                .application(application)
                .registNo(applicationCustomerDto.getRegistNo())
                .chargeReduct(applicationCustomerDto.getChargeReduct())
                .phoneNo(applicationCustomerDto.getPhoneNo())
                .emgPhone(applicationCustomerDto.getEmgPhone())
                .email(applicationCustomerDto.getEmail())
                .cusAddress(applicationCustomerDto.getCusAddress())
                .customerType(applicationCustomerDto.getCustomerType())
                .licenseAuth(applicationCustomerDto.getLicenseAuth())
                .customerCourtProctorAttribute(applicationCustomerDto.getCustomerCourtProctorAttribute())
                .build();
    }

    public static ApplicationCustomerDto from(ApplicationCustomer applicationCustomer) {
        return ApplicationCustomerDto.builder()
                .applId(applicationCustomer.getApplId())
                .registNo(applicationCustomer.getRegistNo())
                .chargeReduct(applicationCustomer.getChargeReduct())
                .phoneNo(applicationCustomer.getPhoneNo())
                .emgPhone(applicationCustomer.getEmgPhone())
                .email(applicationCustomer.getEmail())
                .cusAddress(applicationCustomer.getCusAddress())
                .customerType(applicationCustomer.getCustomerType())
                .licenseAuth(applicationCustomer.getLicenseAuth())
                .customerCourtProctorAttribute((applicationCustomer.getCustomerCourtProctorAttribute()))
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

    public static void update(ApplicationCustomer ApplicationCustomer, ApplicationCustomerDto applicationCustomerDto) {

        ApplicationCustomer.setChargeReduct(applicationCustomerDto.getChargeReduct());
        ApplicationCustomer.setCustomerType(applicationCustomerDto.getCustomerType());
        ApplicationCustomer.setCusAddress(applicationCustomerDto.getCusAddress());
        ApplicationCustomer.setCustomerCourtProctorAttribute(applicationCustomerDto.getCustomerCourtProctorAttribute());
        ApplicationCustomer.setPhoneNo(applicationCustomerDto.getPhoneNo());
        ApplicationCustomer.setLicenseAuth(applicationCustomerDto.getLicenseAuth());
        ApplicationCustomer.setRegistNo(applicationCustomerDto.getRegistNo());
        ApplicationCustomer.setEmail(applicationCustomerDto.getEmail());
        ApplicationCustomer.setEmgPhone(applicationCustomerDto.getEmgPhone());

    }
    }


