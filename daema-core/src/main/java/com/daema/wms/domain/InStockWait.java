package com.daema.wms.domain;

import com.daema.base.domain.common.BaseEntity;
import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(of="wait_id")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="in_stock_wait")
public class InStockWait extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wait_id", columnDefinition = "BIGINT unsigned comment '임시 아이디'")
    private Long waitId;

    @NotNull
    @Column(name = "telecom_code_id", columnDefinition = "int comment '통신사'")
    private int telecom;

    @Column(name = "telecom_name", columnDefinition = "varchar(255) comment '통신사 이름'")
    private String telecomName;

    @Column(name = "prov_id", columnDefinition = "BIGINT unsigned comment '공급 아이디'")
    private Long provId;

    @Column(name = "stock_id", columnDefinition = "BIGINT unsigned comment '재고 아이디'")
    private Long stockId;

    @Column(name = "stock_name", columnDefinition = "varchar(255) comment '재고 이름'")
    private String stockName;
    // 재고구분
    @Column(name = "status_str", columnDefinition = "varchar(255) comment '상태 문자열'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.StockStatStr statusStr;

    @Column(name = "maker_code_id", columnDefinition = "int comment '제조사'")
    private int maker;

    @Column(name = "maker_name", columnDefinition = "varchar(255) comment '제조사 이름'")
    private String makerName;

    @Column(name = "goods_id", columnDefinition = "BIGINT unsigned comment '상품 아이디'")
    private Long goodsId;

    @Column(name = "goods_name", columnDefinition = "varchar(255) comment '상품 이름'")
    private String goodsName;

    @Column(name = "model_name", columnDefinition = "varchar(255) comment '모델 이름'")
    private String modelName;

    @Column(name = "capacity", columnDefinition = "varchar(255) comment '용량'")
    private String capacity;

    @Column(name = "goods_option_id", columnDefinition = "BIGINT unsigned comment '상품 옵션 아이디'")
    private Long goodsOptionId;

    @Column(name = "color_name", columnDefinition = "varchar(255) comment '색상 이름'")
    private String colorName;

    @Column(name = "barcode_type", columnDefinition = "varchar(255) comment '바코드 타입'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.BarcodeType barcodeType;


    @Column(name = "raw_barcode", columnDefinition = "varchar(255) comment '원시 바코드'")
    private String rawBarcode;

    @Column(name = "full_barcode", columnDefinition = "varchar(255) comment '원시, 가공 바코드'")
    private String fullBarcode;

    @Column(name = "serial_no", columnDefinition = "varchar(255) comment '시리얼_ex : 뒷 7자리'")
    private String serialNo;

    @Column(name = "common_barcode", columnDefinition = "varchar(255) comment '공통 바코드'")
    private String commonBarcode;

    // 입고 단가
    @Column(name = "in_stock_amt", columnDefinition = "int comment '입고 금액'")
    private int inStockAmt;
    
    // 입고상태
    @Column(name = "in_stock_status", columnDefinition = "varchar(255) comment '입고 상태'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.InStockStatus inStockStatus;

    // 제품상태
    @Nullable
    @Column(name = "product_faulty_yn", columnDefinition ="char(1) comment '제품 불량 여부'")
    @ColumnDefault("\"N\"")
    private String productFaultyYn = "N";

    // 외장상태
    @Column(name = "extrr_status", columnDefinition = "varchar(255) comment '외장 상태'")
    @Enumerated(EnumType.STRING)
    private WmsEnum.DeviceExtrrStatus extrrStatus;

    @Column(name = "in_stock_memo", columnDefinition = "varchar(255) comment '입고 메모'")
    private String inStockMemo;

    @Nullable
    @Column(name = "product_miss_yn", columnDefinition ="char(1) comment '제품 누락 여부'")
    @ColumnDefault("\"N\"")
    private String productMissYn = "N";

    @Column(name = "miss_product", columnDefinition = "varchar(255) comment '누락 제품'")
    private String missProduct;

    @Column(name = "ddct_amt", columnDefinition = "int comment '차감 금액'")
    private int ddctAmt;

    @Column(name = "add_ddct_amt", columnDefinition = "int comment '추가 차감 금액'")
    private int addDdctAmt;

    /**
     * 출고가 차감 YN
     */
    @Nullable
    @Column(name = "ddct_release_amt_yn", columnDefinition ="char(1) comment '출고가 차감 여부'")
    @ColumnDefault("\"N\"")
    private String ddctReleaseAmtYn = "N";

    @Column(name = "own_store_id", columnDefinition = "BIGINT unsigned comment '소유 관리점 아이디'")
    private Long ownStoreId;

    @Column(name = "hold_store_id", columnDefinition = "BIGINT unsigned comment '보유 관리점 아이디'")
    private Long holdStoreId;

    public void updateDelYn(String delYn){
        super.setDelYn(delYn);
    }
}