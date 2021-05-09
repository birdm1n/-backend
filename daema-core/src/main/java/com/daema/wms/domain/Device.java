package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "dvc_id")
@ToString
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

}