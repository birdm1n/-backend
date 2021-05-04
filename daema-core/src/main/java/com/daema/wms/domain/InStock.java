package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(of="in_stock_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="in_stock")
public class InStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "in_stock_id")
    private Long inStockId;

    @Column
    @Enumerated(EnumType.STRING)
    private WmsEnum.StockStatus inStockStatus;

    @Column(name = "in_stock_amt")
    private int inStockAmt;

    @Column(name = "in_stock_memo")
    private String inStockMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prov_id")
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @OneToOne
    @JoinColumn(name = "dvc_id")
    private Device device;

}
