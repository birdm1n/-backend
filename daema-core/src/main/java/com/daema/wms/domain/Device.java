package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.base.enums.StatusEnum;
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
                        @ColumnResult(name="raw_barcode", type = String.class),
                        @ColumnResult(name="in_stock_amt", type = Integer.class),
                        @ColumnResult(name="goods_name", type = String.class),
                        @ColumnResult(name="model_name", type = String.class),
                        @ColumnResult(name="capacity", type = String.class),
                        @ColumnResult(name="color_name", type = String.class),
                        @ColumnResult(name="maker_code_id", type = Long.class),
                        @ColumnResult(name="telecom_code_id", type = Long.class),
                        @ColumnResult(name="telecom_name", type = String.class),
                        @ColumnResult(name="maker_name", type = String.class)
                })
)

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "dvcId")
@ToString(exclude = {"inStocks", "outStocks", "deviceStatusList", "moveStockList", "returnStockList", "storeStock", "deviceJudges", "openingList"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "device", comment = "기기")
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_id", columnDefinition = "BIGINT UNSIGNED comment '기기 아이디'")
    private Long dvcId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "barcode_type", columnDefinition = "varchar(255) comment '바코드 타입'")
    private WmsEnum.BarcodeType barcodeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_match_status", columnDefinition = "varchar(255) comment '구전산 매칭 상태'")
    private WmsEnum.OldMatchState oldMatchState;

    @Column(name = "raw_barcode", columnDefinition = "varchar(255) comment '원시 바코드'")
    private String rawBarcode;

    @Column(name = "full_barcode", columnDefinition = "varchar(255) comment '원시, 가공 바코드'")
    private String fullBarcode;

    @Column(name = "serial_num", columnDefinition = "varchar(255) comment '시리얼 번호'")
    private String serialNo;

    @Column(name = "in_stock_amt", columnDefinition = "INT comment '입고 금액'" )
    private int inStockAmt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_option_id", columnDefinition = "BIGINT UNSIGNED comment '상품 옵션 아이디'")
    private GoodsOption goodsOption;

    // 소유권을 가지는 Store
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT UNSIGNED comment '관리점 아이디'" )
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

    @OneToMany(mappedBy = "device")
    private List<Opening> openingList = new ArrayList<>();

    public void deleteDevice(){
        super.setDelYn(StatusEnum.FLAG_Y.getStatusMsg());
    }

}