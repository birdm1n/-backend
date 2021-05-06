package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of="return_stock_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="return_stock")
public class ReturnStock extends BaseEntity {

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
    private int returnStockAmt;

    @Column(name = "return_stock_memo")
    private String returnStockMemo;

    @OneToOne
    @JoinColumn(name = "dvc_id")
    private Device device;

    @OneToOne
    @JoinColumn(name = "dvc_stock_id")
    private DeviceStock deviceStock;

    @OneToOne
    @JoinColumn(name = "dvc_status_id")
    private DeviceStatus returnDeviceStatus;

    @Builder
    public ReturnStock(Long returnStockId, WmsEnum.InStockStatus inStockStatus, int returnStockAmt
            , String returnStockMemo, Device device, DeviceStock deviceStock, DeviceStatus returnDeviceStatus){
        this.returnStockId = returnStockId;
        this.inStockStatus = inStockStatus;
        this.returnStockAmt = returnStockAmt;
        this.returnStockMemo = returnStockMemo;
        this.device = device;
        this.deviceStock = deviceStock;
        this.returnDeviceStatus = returnDeviceStatus;
    }
}
