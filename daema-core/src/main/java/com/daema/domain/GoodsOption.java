package com.daema.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of="goodsOptionId")
@ToString
@Entity
@Table(name="goods_option")
public class GoodsOption {

    public GoodsOption() {

    }

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

    @Column(name = "goods_id")
    private long goodsId;

    @Builder
    public GoodsOption(long goodsOptionId, String colorName, String distributor, String commonBarcode, long goodsId){
        this.goodsOptionId = goodsOptionId;
        this.colorName = colorName;
        this.distributor = distributor;
        this.commonBarcode = commonBarcode;
        this.goodsId = goodsId;
    }

}
