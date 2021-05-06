package com.daema.wms.domain;

import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="return_stock_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="return_stock")
public class ReturnStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_stock_id")
    private Long returnStockId;

    @Column
    @Enumerated(EnumType.STRING)
    private WmsEnum.InStockStatus inStockStatus;

    /**
     * 반품비
     */
    @Column(name = "return_stock_amt")
    private Integer returnStockAmt;

    @Column(name = "return_stock_memo")
    private String returnStockMemo;

    /**
     * 출고가 차감 YN
     */
    @Column(name = "ddct_release_amt_yn", columnDefinition ="char(1)")
    private String ddctReleaseAmtYn;

    @Column(name = "regi_user_id")
    private long regiUserId;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_id")
    private Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_stock_id")
    private DeviceStock deviceStock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dvc_status_id")
    private DeviceStatus returnDeviceStatus;

    @Builder
    public ReturnStock(Long returnStockId, WmsEnum.InStockStatus inStockStatus, Integer returnStockAmt
            , String returnStockMemo, String ddctReleaseAmtYn, Device device, DeviceStock deviceStock, DeviceStatus returnDeviceStatus
            ,long regiUserId ,LocalDateTime regiDateTime){
        this.returnStockId = returnStockId;
        this.inStockStatus = inStockStatus;
        this.returnStockAmt = returnStockAmt;
        this.returnStockMemo = returnStockMemo;
        this.ddctReleaseAmtYn = ddctReleaseAmtYn;
        this.device = device;
        this.deviceStock = deviceStock;
        this.returnDeviceStatus = returnDeviceStatus;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
    }
}
