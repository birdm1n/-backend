package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="dvc_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="device")
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_id")
    private long dvcId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_option_id")
    private GoodsOption goodsOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Transient
    private long pub_noti_id;

    @Builder
    public Device(long dvcId, GoodsOption goodsOption, Goods goods, long pub_noti_id) {
        this.dvcId = dvcId;
        this.goodsOption = goodsOption;
        this.goods = goods;
        this.pub_noti_id = pub_noti_id;
    }
}
