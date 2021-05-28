package com.daema.wms.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="prov_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="provider")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prov_id", columnDefinition = "BIGINT unsigned comment '공급 아이디'")
    private long provId;

    @NotBlank
    @Column(length = 50, name = "prov_name", columnDefinition = "varchar(255) is not null comment '공급 이름'")
    private String provName;

    @Column(name = "charger_name", columnDefinition = "varchar(255) comment '담당자 이름'")
    private String chargerName;

    @Column(name = "charger_email", columnDefinition = "varchar(255) comment '담당자 이메일'")
    private String chargerEmail;

    @Column(length = 15, name = "charger_phone", columnDefinition = "varchar(255) comment '담당자 연락처'")
    private String chargerPhone;

    @Column(length = 4, name = "charger_phone1", columnDefinition = "varchar(255) comment '담당자 연락처1'")
    private String chargerPhone1;

    @Column(length = 4, name = "charger_phone2", columnDefinition = "varchar(255) comment '담당자 연락처2'")
    private String chargerPhone2;

    @Column(length = 4, name = "charger_phone3", columnDefinition = "varchar(255) comment '담당자 연락처3'")
    private String chargerPhone3;

    @Column(length = 7, name = "return_zip_code", columnDefinition = "varchar(255)  is not null comment '반품 우편번호 코드'")
    private String returnZipCode;

    @Column(length = 100, name = "return_addr", columnDefinition = "varchar(255) is not null comment '반품 주소'")
    private String returnAddr;

    @Column(length = 100, name = "return_addr_detail", columnDefinition = "varchar(255) is not null comment '반품 주소 상세'")
    private String returnAddrDetail;

    @Column(nullable = false, columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private long storeId;

    @Column(nullable = false, name = "use_yn", columnDefinition ="char(1) comment '사용 여부'")
    private String useYn;

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1) comment '삭제 여부'")
    private String delYn = "N";

    @Column(name = "regi_user_id", columnDefinition = "BIGINT unsigned comment '등록 유저 아이디'")
    private long regiUserId;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 날짜시간'")
    private LocalDateTime regiDateTime;

    @Column(name = "upd_user_id", columnDefinition = "BIGINT unsigned comment '업데이트 유저 아이디'")
    private Long updUserId;

    @Column(name = "upd_datetime", columnDefinition = "DATETIME(6) comment '업데이트 날짜시간'")
    private LocalDateTime updDateTime;

    @Transient
    private String name;

    public Provider(long provId){
        this.provId = provId;
    }

    @Builder
    public Provider(long provId, String provName, String chargerName, String chargerEmail
            , String chargerPhone, String chargerPhone1, String chargerPhone2, String chargerPhone3
            , String returnZipCode, String returnAddr, String returnAddrDetail, String delYn, String useYn, long regiUserId, LocalDateTime regiDateTime
    ,Long updUserId, LocalDateTime updDateTime, String name, long storeId){
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

    public void updateDelYn(Provider provider, String delYn, long updUserId){
        provider.setDelYn(delYn);
        provider.setUpdUserId(updUserId);
        provider.setUpdDateTime(LocalDateTime.now());
    }

    public void updateUseYn(Provider provider, String useYn, long updUserId){
        provider.setUseYn(useYn);
        provider.setUpdUserId(updUserId);
        provider.setUpdDateTime(LocalDateTime.now());
    }
}
