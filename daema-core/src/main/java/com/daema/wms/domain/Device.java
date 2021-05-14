package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.dto.response.StockDeviceListDto;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SqlResultSetMapping(
        name="StockDeviceList",
        classes = @ConstructorResult(
                targetClass = StockDeviceListDto.class,
                columns = {
                        @ColumnResult(name="dvc_id", type = Long.class),
                        @ColumnResult(name="stock_id", type = Long.class),
                        @ColumnResult(name="stock_name", type = String.class),
                        @ColumnResult(name="hierarchy", type = String.class),
                        @ColumnResult(name="full_barcode", type = String.class),
                        @ColumnResult(name="in_stock_amt", type = Integer.class),
                        @ColumnResult(name="goods_name", type = String.class),
                        @ColumnResult(name="model_name", type = String.class),
                        @ColumnResult(name="capacity", type = String.class),
                        @ColumnResult(name="color_name", type = String.class),
                        @ColumnResult(name="maker", type = Long.class),
                        @ColumnResult(name="telecom", type = Long.class),
                        @ColumnResult(name="telecom_name", type = String.class),
                        @ColumnResult(name="maker_name", type = String.class)
                })
)

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "dvcId")
@ToString(exclude = {"inStocks", "outStocks", "deviceStatusList", "moveStockList", "returnStockList", "storeStock", "deviceJudges"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device")
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_id")
    private Long dvcId;

    @Column(name = "barcode_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.BarcodeType barcodeType;

    @Column(name = "full_barcode")
    private String fullBarcode;

    @Column(name = "in_stock_amt")
    private int inStockAmt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_option_id")
    private GoodsOption goodsOption;

    // 소유권을 가지는 Store
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "device")
    private List<InStock> inStocks = new ArrayList<>();

    @OneToMany(mappedBy = "device")
    private List<OutStock> outStocks = new ArrayList<>();

    @OneToMany(mappedBy = "device")
    private List<DeviceStatus> deviceStatusList = new ArrayList<>();

    @OneToMany(mappedBy = "device")
    private List<MoveStock> moveStockList = new ArrayList<>();

    @OneToMany(mappedBy = "device")
    private List<ReturnStock> returnStockList = new ArrayList<>();

    @OneToOne(mappedBy = "device")
    private StoreStock storeStock;

    @OneToMany(mappedBy = "device")
    private List<DeviceJudge> deviceJudges = new ArrayList<>();
}