package com.daema.wms.domain;

import com.daema.wms.domain.dto.response.StockListDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SqlResultSetMapping(
        name="StockList",
        classes = @ConstructorResult(
                targetClass = StockListDto.class,
                columns = {
                        @ColumnResult(name="depth", type = Integer.class),
                        @ColumnResult(name="stock_id", type = Long.class),
                        @ColumnResult(name="store_id", type = Long.class),
                        @ColumnResult(name="parent_stock_id", type = Long.class),
                        @ColumnResult(name="stock_name", type = String.class),
                        @ColumnResult(name="stock_type", type = String.class),
                        @ColumnResult(name="charger_name", type = String.class),
                        @ColumnResult(name="charger_phone", type = String.class),
                        @ColumnResult(name="hierarchy", type = String.class)
                })
)

@Getter
@Setter
@EqualsAndHashCode(of="stock_id")
@ToString(exclude = {"prevMoveStockList", "nextMoveStockList"})
@NoArgsConstructor
@Entity
@Table(name="stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private long stockId;

    @NotBlank
    @Column(length = 50, nullable = false, name = "stock_name")
    private String stockName;

    @Column(name = "parent_stock_id")
    @ColumnDefault(value = "0")
    private long parentStockId;

    /**
     * 개통점 openStoreId, 영업점 storeId, Login storeId
     */
    @Column(nullable = false, name="store_id")
    private long storeId;

    /**
     * O - openStore, S - store, I - inner (내부 관리)
     */
    @Column(nullable = false, name="stock_type", columnDefinition = "char(1)")
    private String stockType;

    @Column(nullable = false, name="regi_store_id")
    private long regiStoreId;

    @Column(name = "charger_name")
    private String chargerName;

    @Column(length = 15, name = "charger_phone")
    private String chargerPhone;

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String delYn;

    @Column(name = "regi_user_id")
    private long regiUserId;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @Column(name = "upd_user_id")
    private Long updUserId;

    @Column(name = "upd_datetime")
    private LocalDateTime updDateTime;

    @OneToMany(mappedBy = "prevStock", fetch = FetchType.LAZY)
    private List<MoveStock> prevMoveStockList = new ArrayList<>();

    @OneToMany(mappedBy = "nextStock", fetch = FetchType.LAZY)
    private List<MoveStock> nextMoveStockList = new ArrayList<>();

    @Builder
    public Stock(long stockId, String stockName, long parentStockId, long storeId, String stockType, String chargerName
            , String chargerPhone, long regiStoreId, String delYn, long regiUserId, LocalDateTime regiDateTime
            , Long updUserId, LocalDateTime updDateTime){
        this.stockId = stockId;
        this.stockName = stockName;
        this.parentStockId = parentStockId;
        this.storeId = storeId;
        this.stockType = stockType;
        this.chargerName = chargerName;
        this.chargerPhone = chargerPhone;
        this.regiStoreId = regiStoreId;
        this.delYn = delYn;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
        this.updUserId = updUserId;
        this.updDateTime = updDateTime;
    }

    public void updateDelYn(Stock provider, String delYn, long updUserId){
        provider.setDelYn(delYn);
        provider.setUpdUserId(updUserId);
        provider.setUpdDateTime(LocalDateTime.now());
    }
}
