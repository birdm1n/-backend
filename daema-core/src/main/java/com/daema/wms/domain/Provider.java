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
    @Column(name = "prov_id")
    private long provId;

    @NotBlank
    @Column(length = 50, nullable = false, unique = true, name = "prov_name")
    private String provName;

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

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1)")
    private String delYn = "N";

    @Column(name = "regi_user_id")
    private long regiUserId;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @Column(name = "upd_user_id")
    private Long updUserId;

    @Column(name = "upd_datetime")
    private LocalDateTime updDateTime;

    @Transient
    private String name;

    public Provider(long provId){
        this.provId = provId;
    }

    @Builder
    public Provider(long provId, String provName, String chargerPhone, String chargerName, String chargerEmail
            , String returnZipCode, String returnAddr, String returnAddrDetail, String delYn, String useYn, long regiUserId, LocalDateTime regiDateTime
    ,Long updUserId, LocalDateTime updDateTime, String name){
        this.provId = provId;
        this.provName = provName;
        this.chargerPhone = chargerPhone;
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
