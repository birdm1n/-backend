package com.daema.commgmt.domain;

import com.daema.commgmt.domain.dto.response.OpenStoreListDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@SqlResultSetMapping(
        name="OpenStoreList",
        classes = @ConstructorResult(
                targetClass = OpenStoreListDto.class,
                columns = {
                        @ColumnResult(name="open_store_id", type = Integer.class),
                        @ColumnResult(name="biz_no", type = String.class),
                        @ColumnResult(name="charger_name", type = String.class),
                        @ColumnResult(name="charger_phone", type = String.class),
                        @ColumnResult(name="open_store_name", type = String.class),
                        @ColumnResult(name="regi_datetime", type = LocalDateTime.class),
                        @ColumnResult(name="return_addr", type = String.class),
                        @ColumnResult(name="return_addr_detail", type = String.class),
                        @ColumnResult(name="return_zip_code", type = String.class),
                        @ColumnResult(name="store_id", type = Long.class),
                        @ColumnResult(name="telecom", type = Integer.class),
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
@Table(name="open_store")
public class OpenStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "open_store_id")
    private long openStoreId;

    @NotNull
    @Column(name = "store_id")
    private long storeId;

    @NotBlank
    @Column(length = 50, nullable = false, name = "open_store_name")
    private String openStoreName;

    @NotNull
    @Column(name = "telecom")
    private int telecom;

    @Transient
    private String telecomName;

    @NotBlank
    @Column(nullable = false, name = "biz_no", columnDefinition = "char(12)")
    private String bizNo;

    @Column(name = "charger_name")
    private String chargerName;

    @NotBlank
    @Column(length = 15, nullable = false, name = "charger_phone")
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
    private String delYn;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @Builder
    public OpenStore(long openStoreId, long storeId, String openStoreName, int telecom, String telecomName, String bizNo, String chargerName, String chargerPhone
            , String returnZipCode, String returnAddr, String returnAddrDetail, String useYn, String delYn, LocalDateTime regiDateTime){
        this.openStoreId = openStoreId;
        this.storeId = storeId;
        this.openStoreName = openStoreName;
        this.telecom = telecom;
        this.telecomName = telecomName;
        this.bizNo = bizNo;
        this.chargerPhone = chargerPhone;
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
