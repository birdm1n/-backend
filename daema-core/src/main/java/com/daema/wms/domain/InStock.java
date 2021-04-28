package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
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

    @NotBlank
    @Column(nullable = false, name = "in_stock_status", columnDefinition ="char(1)")
    @ColumnDefault("1")
    private String inStockStatus;

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
