package com.daema.wms.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "seq")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "stock_tmp", comment = "텔킷 재고")
public class StockTmp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;

    private String inStockDate;
    private String provider;
    private String instockAmt;
    private String telecom;
    private String modelName;
    private String barcode;
    private String colorName;
    private String stockType;
    private String moveDate;
    private String nowStockName;
    private String memo;

    private long provId;
    private long networkCodeId;
    private long nowStockId;
    private long goodsId;
    private long goodsOptionId;
    private long telecomCodeId;
    private long upStockId;
    private long moveType;

    @Transient
    private long selDvcId;

}
