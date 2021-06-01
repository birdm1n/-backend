package com.daema.commgmt.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="storeId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private long storeId;

    @NotBlank
    @Column(unique = true, name = "store_name", columnDefinition = "varchar(255) comment '관리점 이름'")
    private String storeName;

    @NotNull
    @Column(name = "telecom_code_id", columnDefinition = "int comment '통신사 코드 아이디'")
    private int telecom;

    @Transient
    private String telecomName;

    @NotBlank
    @Column(name = "biz_no", unique = true, nullable = false, columnDefinition = "varchar(12) comment '사업자 넘버'")
    private String bizNo;

    @Column(name = "ceo_name", columnDefinition = "varchar(255) comment '대표자 이름'")
    private String ceoName;

    @Column(name = "charger_name", columnDefinition = "varchar(255) comment '담당자 이름'")
    private String chargerName;

    @Column(name = "charger_email", columnDefinition = "varchar(255) comment '담당자 이메일'")
    private String chargerEmail;

    @Column( name = "charger_phone", columnDefinition = "varchar(255) comment '담당자 연락처'")
    private String chargerPhone;

    @Column(name = "charger_phone1", columnDefinition = "varchar(255) comment '담당자 연락처2'")
    private String chargerPhone1;

    @Column(name = "charger_phone2", columnDefinition = "varchar(255) comment '담당자 연락처3'")
    private String chargerPhone2;

    @Column(name = "charger_phone3", columnDefinition = "varchar(255) comment '담당자 연락처4'")
    private String chargerPhone3;

    @Column(name = "return_zip_code", nullable = false, columnDefinition = "varchar(255) comment '반품 우편번호 코드'")
    private String returnZipCode;

    @Column(name = "return_addr", nullable = false, columnDefinition = "varchar(255) comment '반품 주소'")
    private String returnAddr;

    @Column(name = "return_addr_detail", nullable = false, columnDefinition = "varchar(255) comment '반품 주소 상세'")
    private String returnAddrDetail;

    @Column(nullable = false, name = "use_yn", columnDefinition ="char(1) comment '사용 여부'")
    private String useYn;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 날짜시간'")
    private LocalDateTime regiDateTime;

    @Builder
    public Store (long storeId, String storeName, int telecom, String telecomName, String bizNo, String ceoName
            ,String chargerPhone ,String chargerPhone1 ,String chargerPhone2 ,String chargerPhone3
            ,String chargerName, String chargerEmail, String returnZipCode
            ,String returnAddr, String returnAddrDetail, String useYn, LocalDateTime regiDateTime){
        this.storeId = storeId;
        this.storeName = storeName;
        this.telecom = telecom;
        this.telecomName = telecomName;
        this.bizNo = bizNo;
        this.ceoName = ceoName;
        this.chargerPhone = chargerPhone;
        this.chargerPhone1 = chargerPhone1;
        this.chargerPhone2 = chargerPhone2;
        this.chargerPhone3 = chargerPhone3;
        this.chargerName = chargerName;
        this.chargerEmail = chargerEmail;
        this.returnZipCode = returnZipCode;
        this.returnAddr = returnAddr;
        this.returnAddrDetail = returnAddrDetail;
        this.useYn = useYn;
        this.regiDateTime = regiDateTime;
    }
}
