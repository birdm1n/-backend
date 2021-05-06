package com.daema.wms.domain;

import com.daema.wms.domain.enums.WmsEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="dvc_stock_id")
@ToString
@NoArgsConstructor
@Entity
@Table(name="device_stock")
public class DeviceStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvc_stock_id")
    private Long dvcStockId;

    /**
     * 1 - 입고
     * 2 - 판매이동
     * 3 - 재고이동
     * 4 - 재고이관
     * 5 - 불량이관
     * 6 - 판매이관
     * 7 - 반품
     */
    @Column(name = "dvc_stock_type")
    @Enumerated(EnumType.STRING)
    private WmsEnum.DvcStockType dvcStockType;

    @Column(name = "regi_user_id")
    private long regiUserId;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @ManyToOne
    @JoinColumn(name = "dvc_id")
    private Device device;

    /**
     * 현재 보유처.  stockType 참고
     * 1 : 공급처(창고 아이디 없음 stock 맵핑 불가)
     * 2, 3, 4, 5, 6, 7 : 현 보유처
     */
    @ManyToOne
    @JoinColumn(name = "prev_stock_id", referencedColumnName = "stock_id")
    private Stock prevStock;

    /**
     * 다음 보유처.  stockType 참고
     * 1 : 현 보유처
     * 2 : 사용자에게 넘어 감. (창고 아이디 없음 stock 맵핑 불가)
     * 3 : 다음 보유처(내 보유처 또는 내 하위 보유처)
     * 4 : 다음 보유처(관리점)
     * 5 : 다음 보유처(공급처)
     * 6 : 다음 보유처(관리점 또는 미가입 대리점. 오프라인으로 기기 판매 가능하다고 함)
     * 7 : 다음 보유처(내 보유처)
     */
    @ManyToOne
    @JoinColumn(name = "next_stock_id", referencedColumnName = "stock_id")
    private Stock nextStock;

    @OneToOne(mappedBy = "deviceStock")
    private Delivery delivery;

    @Builder
    public DeviceStock(Long dvcStockId, WmsEnum.DvcStockType dvcStockType, long regiUserId, LocalDateTime regiDateTime, Device device, Stock prevStock, Stock nextStock, Delivery delivery) {
        this.dvcStockId = dvcStockId;
        this.dvcStockType = dvcStockType;
        this.regiUserId = regiUserId;
        this.regiDateTime = regiDateTime;
        this.device = device;
        this.prevStock = prevStock;
        this.nextStock = nextStock;
        this.delivery = delivery;
    }
}
