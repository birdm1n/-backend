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
    @Column(name = "store_id")
    private long storeId;

    @NotBlank
    @Column(length = 50, nullable = false, unique = true, name = "store_name")
    private String storeName;

    @NotNull
    @Column(name = "telecom")
    private int telecom;

    @Transient
    private String telecomName;

    @NotBlank
    @Column(nullable = false, name = "biz_no", unique = true, columnDefinition = "char(12)")
    private String bizNo;

    @Column(name = "ceo_name")
    private String ceoName;

    @Column(name = "charger_name")
    private String chargerName;

    @Column(name = "charger_email")
    private String chargerEmail;

    @Column(length = 15, name = "charger_phone")
    private String chargerPhone;

    @Column(length = 7, nullable = false, name = "return_zip_code")
    private String returnZipCode;

    @Column(length = 100, nullable = false, name = "return_addr")
    private String returnAddr;

    @Column(length = 100, nullable = false, name = "return_addr_detail")
    private String returnAddrDetail;

    @Column(nullable = false, name = "use_yn", columnDefinition ="char(1)")
    private String useYn;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @Builder
    public Store (long storeId, String storeName, int telecom, String telecomName, String bizNo, String ceoName, String chargerPhone
            ,String chargerName, String chargerEmail, String returnZipCode
            ,String returnAddr, String returnAddrDetail, String useYn, LocalDateTime regiDateTime){
        this.storeId = storeId;
        this.storeName = storeName;
        this.telecom = telecom;
        this.telecomName = telecomName;
        this.bizNo = bizNo;
        this.ceoName = ceoName;
        this.chargerPhone = chargerPhone;
        this.chargerName = chargerName;
        this.chargerEmail = chargerEmail;
        this.returnZipCode = returnZipCode;
        this.returnAddr = returnAddr;
        this.returnAddrDetail = returnAddrDetail;
        this.useYn = useYn;
        this.regiDateTime = regiDateTime;
    }
}
