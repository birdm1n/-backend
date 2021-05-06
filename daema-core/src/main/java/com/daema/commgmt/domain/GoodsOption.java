package com.daema.commgmt.domain;

import com.daema.wms.domain.Device;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of="goodsOptionId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="goods_option")
public class GoodsOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_option_id")
    private long goodsOptionId;

    @Column(name = "color_name", length = 20)
    private String colorName;

    @Column(name = "distributor", length = 20)
    private String distributor;

    @Column(name = "common_barcode", length = 20)
    private String commonBarcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @OneToMany(mappedBy = "goodsOption")
    private List<Device> device;



    @Builder
    public GoodsOption(long goodsOptionId, String colorName, String distributor, String commonBarcode, Goods goods){
        this.goodsOptionId = goodsOptionId;
        this.colorName = colorName;
        this.distributor = distributor;
        this.commonBarcode = commonBarcode;
        this.goods = goods;
    }

}
