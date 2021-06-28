package com.daema.core.wms.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="provId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "provider", comment = "공급처")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prov_id", columnDefinition = "BIGINT unsigned comment '공급 아이디'")
    private Long provId;

    @NotBlank
    @Column(name = "prov_name", columnDefinition = "varchar(255) comment '공급 이름'")
    private String provName;

    @Column(name = "charger_name", columnDefinition = "varchar(255) comment '담당자 이름'")
    private String chargerName;

    @Column(name = "charger_email", columnDefinition = "varchar(255) comment '담당자 이메일'")
    private String chargerEmail;

    @Column( name = "charger_phone", columnDefinition = "varchar(255) comment '담당자 연락처'")
    private String chargerPhone;

    @Column(name = "charger_phone1", columnDefinition = "varchar(255) comment '담당자 연락처1'")
    private String chargerPhone1;

    @Column(name = "charger_phone2", columnDefinition = "varchar(255) comment '담당자 연락처2'")
    private String chargerPhone2;

    @Column(name = "charger_phone3", columnDefinition = "varchar(255) comment '담당자 연락처3'")
    private String chargerPhone3;

    @Column(name = "return_zip_code", nullable = false, columnDefinition = "varchar(255) comment '반품 우편 코드'")
    private String returnZipCode;

    @Column(name = "return_addr", nullable = false, columnDefinition = "varchar(255) comment '반품 주소'")
    private String returnAddr;

    @Column(name = "return_addr_detail", nullable = false, columnDefinition = "varchar(255) comment '반품 주소 상세'")
    private String returnAddrDetail;

    @Column(nullable = false, columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private Long storeId;

    @Column(nullable = false, name = "use_yn", columnDefinition ="char(1) comment '사용 여부'")
    private String useYn;

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1) comment '삭제 여부'")
    private String delYn = "N";

    @Column(name = "regi_user_id", columnDefinition = "BIGINT unsigned comment '등록 유저 아이디'")
    private Long regiUserId;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'")
    private LocalDateTime regiDateTime;

    @Column(name = "upd_user_id", columnDefinition = "BIGINT unsigned comment '수정 유저 아이디'")
    private Long updUserId;

    @Column(name = "upd_datetime", columnDefinition = "DATETIME(6) comment '수정 일시'")
    private LocalDateTime updDateTime;

    @Transient
    private String name;

    public Provider(Long provId){
        this.provId = provId;
    }

    @Builder
    public Provider(Long provId, String provName, String chargerName, String chargerEmail
            , String chargerPhone, String chargerPhone1, String chargerPhone2, String chargerPhone3
            , String returnZipCode, String returnAddr, String returnAddrDetail, String delYn, String useYn, long regiUserId, LocalDateTime regiDateTime
    ,Long updUserId, LocalDateTime updDateTime, String name, Long storeId){
        this.provId = provId;
        this.provName = provName;
        this.chargerPhone = chargerPhone;
        this.chargerPhone1 = chargerPhone1;
        this.chargerPhone2 = chargerPhone2;
        this.chargerPhone3 = chargerPhone3;
        this.chargerName = chargerName;
        this.chargerEmail = chargerEmail;
        this.returnZipCode = returnZipCode;
        this.returnAddr = returnAddr;
        this.returnAddrDetail = returnAddrDetail;
        this.delYn = delYn;
        this.useYn = useYn;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
        this.updUserId = updUserId;
        this.updDateTime = updDateTime;
        this.name = name;
        this.storeId = storeId;
    }

    public void updateDelYn(Provider provider, String delYn, Long updUserId){
        provider.setDelYn(delYn);
        provider.setUpdUserId(updUserId);
        provider.setUpdDateTime(LocalDateTime.now());
    }

    public void updateUseYn(Provider provider, String useYn, Long updUserId){
        provider.setUseYn(useYn);
        provider.setUpdUserId(updUserId);
        provider.setUpdDateTime(LocalDateTime.now());
    }
}
