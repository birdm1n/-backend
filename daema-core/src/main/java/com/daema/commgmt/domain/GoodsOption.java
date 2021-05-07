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

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1)")
    private String delYn = "N";

    @Column(name = "un_lock_yn", columnDefinition ="char(1)")
    private String unLockYn = "N";

    @Column(name = "capacity")
    private String capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @OneToMany(mappedBy = "goodsOption")
    private List<Device> device;



    @Builder
    public GoodsOption(long goodsOptionId, String colorName, String distributor, String commonBarcode
                       ,String delYn ,String unLockYn ,String capacity ,Goods goods){
        this.goodsOptionId = goodsOptionId;
        this.colorName = colorName;
        this.distributor = distributor;
        this.commonBarcode = commonBarcode;
        this.goods = goods;
        this.capacity = capacity;
        this.delYn = delYn;
        this.unLockYn = unLockYn;
    }

    public GoodsOption updateDelYn(GoodsOption goodsOption, String delYn){
        goodsOption.setDelYn(delYn);
        return goodsOption;
    }
}
