package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.commgmt.domain.Goods;
import com.daema.commgmt.domain.GoodsOption;
import com.daema.commgmt.domain.Store;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@EqualsAndHashCode(of="dvc_id")
@ToString
@NoArgsConstructor
@AuditOverride(forClass = BaseEntity.class) // 상속받을 경우 상속받은 컬럼도 이력이 필요한 경우
@Audited // envers
@Entity
@Table(name="device")
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_id")
    private Long dvcId;

    // 1, "매장재고" / 2, "이동재고" / 3, "판매이동" / 4, "재고이관" / 5, "불량이관"
    @NotBlank
    @Column(nullable = false, name = "stock_type", columnDefinition ="char(1)")
    @ColumnDefault("1")
    private String stockType;

    @NotAudited
    @Nullable
    @Column(name = "product_faulty_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productFaultyYn;

    @NotAudited
    @NotBlank
    @Column(nullable = false, name = "extrr_status", columnDefinition ="char(1)")
    @ColumnDefault("1")
    private String extrrStatus;

    @NotAudited
    @Nullable
    @Column(name = "product_miss_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String productMissYn;

    @NotAudited
    @Column(name = "miss_product")
    private String missProduct;

    @NotAudited
    @Column(name = "ddct_amt")
    private int ddctAmt;

    // 이관처 필요한가...?

    // 소유권을 가지는 Store
    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "own_store_id", referencedColumnName = "store_id")
    private Store ownStore;


    // 보유처 store
    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hold_store_id", referencedColumnName = "store_id")
    private Store holdStore;

    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_option_id")
    private GoodsOption goodsOption;

    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

}