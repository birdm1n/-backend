package com.daema.core.commgmt.domain;

import com.daema.core.commgmt.dto.response.OpenStoreListDto;
import com.daema.core.sms.domain.JoinInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SqlResultSetMapping(
        name="OpenStoreList",
        classes = @ConstructorResult(
                targetClass = OpenStoreListDto.class,
                columns = {
                        @ColumnResult(name="open_store_id", type = Integer.class),
                        @ColumnResult(name="biz_no", type = String.class),
                        @ColumnResult(name="charger_name", type = String.class),
                        @ColumnResult(name="charger_phone", type = String.class),
                        @ColumnResult(name="charger_phone1", type = String.class),
                        @ColumnResult(name="charger_phone2", type = String.class),
                        @ColumnResult(name="charger_phone3", type = String.class),
                        @ColumnResult(name="open_store_name", type = String.class),
                        @ColumnResult(name="regi_datetime", type = LocalDateTime.class),
                        @ColumnResult(name="return_addr", type = String.class),
                        @ColumnResult(name="return_addr_detail", type = String.class),
                        @ColumnResult(name="return_zip_code", type = String.class),
                        @ColumnResult(name="store_id", type = Long.class),
                        @ColumnResult(name="telecom", type = Long.class),
                        @ColumnResult(name="use_yn", type = String.class),
                        @ColumnResult(name="del_yn", type = String.class),
                        @ColumnResult(name="telecomName", type = String.class),
                        @ColumnResult(name="req_store_id", type = Long.class)
                })
)

@Getter
@Setter
@EqualsAndHashCode(of="openStoreId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "open_store", comment = "개통 관리점")
public class OpenStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opening_store_id", columnDefinition = "BIGINT unsigned comment '개통 관리점 아이디'")
    private long openStoreId;

    @NotNull
    @Column(name = "store_id", columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private long storeId;

    @NotBlank
    @Column(name = "opening_store_name", columnDefinition = "varchar(255) comment '개통 관리점 이름'")
    private String openStoreName;

    @NotNull
    @Column(name = "telecom_code_id", columnDefinition = "BIGINT unsigned comment '통신사 코드 아이디'")
    private Long telecom;

    @Transient
    private String telecomName;

    @NotBlank
    @Column(name = "biz_num", columnDefinition = "varchar(255) comment '사업자 번호'")
    private String bizNo;

    @Column(name = "charger_name", columnDefinition = "varchar(255) comment '담당자 이름'")
    private String chargerName;

    @NotBlank
    @Column(name = "charger_phone", columnDefinition = "varchar(255) comment '담당자 연락처'")
    private String chargerPhone;

    @NotBlank
    @Column(name = "charger_phone1", columnDefinition = "varchar(255)    comment '담당자 연락처1'")
    private String chargerPhone1;

    @NotBlank
    @Column(name = "charger_phone2", columnDefinition = "varchar(255)   comment '담당자 연락처2'")
    private String chargerPhone2;

    @NotBlank
    @Column(name = "charger_phone3", columnDefinition = "varchar(255)   comment '담당자 연락처3'")
    private String chargerPhone3;

    @Column(name = "return_zip_code", nullable = false, columnDefinition = "varchar(255) comment '반품 우편 코드'")
    private String returnZipCode;

    @Column(name = "return_addr", nullable = false, columnDefinition = "varchar(255) comment '반품 주소'")
    private String returnAddr;

    @Column(name = "return_addr_detail", nullable = false, columnDefinition = "varchar(255) comment '반품 주소 상세'")
    private String returnAddrDetail;

    @Column(name = "use_yn", nullable = false, columnDefinition ="char(1) comment '사용 여부'")
    private String useYn;

    @Column(name = "del_yn", nullable = false, columnDefinition = "char(1) comment '삭제 여부'")
    private String delYn;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'")
    private LocalDateTime regiDateTime;
    @OneToMany(mappedBy = "openStore")
    private List<JoinInfo> joinInfoList = new ArrayList<>();

    @Builder
    public OpenStore(long openStoreId, long storeId, String openStoreName, Long telecom, String telecomName, String bizNo, String chargerName
            , String chargerPhone, String chargerPhone1, String chargerPhone2, String chargerPhone3
            , String returnZipCode, String returnAddr, String returnAddrDetail, String useYn, String delYn, LocalDateTime regiDateTime){
        this.openStoreId = openStoreId;
        this.storeId = storeId;
        this.openStoreName = openStoreName;
        this.telecom = telecom;
        this.telecomName = telecomName;
        this.bizNo = bizNo;
        this.chargerPhone = chargerPhone;
        this.chargerPhone1 = chargerPhone1;
        this.chargerPhone2 = chargerPhone2;
        this.chargerPhone3 = chargerPhone3;
        this.chargerName = chargerName;
        this.returnZipCode = returnZipCode;
        this.returnAddr = returnAddr;
        this.returnAddrDetail = returnAddrDetail;
        this.useYn = useYn;
        this.delYn = delYn;
        this.regiDateTime = regiDateTime;
    }

    public void updateUseYn(OpenStore openStore, String useYn){
        openStore.setUseYn(useYn);
    }

    public void updateDelYn(OpenStore openStore, String delYn){
        openStore.setDelYn(delYn);
    }

}
