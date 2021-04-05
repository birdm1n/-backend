package com.daema.domain;

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
    @Column(length = 50, nullable = false, name = "store_name")
    private String storeName;

    @NotNull
    @Column(name = "telecom")
    private int telecom;

    @NotBlank
    @Column(nullable = false, name = "biz_no", columnDefinition = "char(12)")
    private String bizNo;

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
    public Store (long storeId, String storeName, int telecom, String bizNo, String chargerPhone, String returnZipCode,
            String returnAddr, String returnAddrDetail, String useYn, LocalDateTime regiDateTime){
        this.storeId = storeId;
        this.storeName = storeName;
        this.telecom = telecom;
        this.bizNo = bizNo;
        this.chargerPhone = chargerPhone;
        this.returnZipCode = returnZipCode;
        this.returnAddr = returnAddr;
        this.returnAddrDetail = returnAddrDetail;
        this.useYn = useYn;
        this.regiDateTime = regiDateTime;
    }
}
