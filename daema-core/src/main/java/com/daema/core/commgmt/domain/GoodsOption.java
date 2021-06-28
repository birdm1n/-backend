package com.daema.core.commgmt.domain;

import com.daema.core.wms.domain.Device;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of="goodsOptionId")
@ToString(exclude = {"device"})
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "goods_option", comment = "상품 옵션")
public class GoodsOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_option_id", columnDefinition = "BIGINT unsigned comment '상품 옵션 아이디'")
    private long goodsOptionId;

    @Column(name = "color_name", columnDefinition = "varchar(255) comment '색상 이름'")
    private String colorName;

    @Column(name = "common_barcode", columnDefinition = "varchar(255) comment '공통 바코드'")
    private String commonBarcode;

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1) comment '삭제 여부'")
    private String delYn = "N";

    @Column(name = "un_lock_yn", columnDefinition ="char(1) comment '잠금 해제 여부'")
    private String unLockYn = "N";

    @Column(name = "capacity", columnDefinition = "varchar(255) comment '용량'")
    private String capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id", columnDefinition = "BIGINT unsigned comment '상품 아이디'")
    private Goods goods;

    @OneToMany(mappedBy = "goodsOption")
    private List<Device> device;

    @Builder
    public GoodsOption(long goodsOptionId, String colorName, String commonBarcode
                       ,String delYn ,String unLockYn ,String capacity ,Goods goods){
        this.goodsOptionId = goodsOptionId;
        this.colorName = colorName;
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
