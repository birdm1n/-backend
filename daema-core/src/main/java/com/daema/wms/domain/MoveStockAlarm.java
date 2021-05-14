package com.daema.wms.domain;

import com.daema.base.domain.Member;
import com.daema.commgmt.domain.Store;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="moveStockAlarmId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="move_stock_alarm")
public class MoveStockAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "move_stock_alarm_id")
    private Long moveStockAlarmId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", unique = true)
    private Store store;

    //재판매 마감일자
    @Column(name = "resell_day")
    private Integer resellDay;

    //미출고 시점
    @Column(name = "undelivered_day")
    private Integer undeliveredDay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upd_user_id", referencedColumnName = "seq")
    private Member updUserId;

    @CreatedDate
    @Column(name = "upd_datetime")
    private LocalDateTime updDateTime;

    @Builder
    public MoveStockAlarm(Long moveStockAlarmId, Store store, Integer resellDay, Integer undeliveredDay, Member updUserId, LocalDateTime updDateTime) {
        this.moveStockAlarmId = moveStockAlarmId;
        this.store = store;
        this.resellDay = resellDay;
        this.undeliveredDay = undeliveredDay;
        this.updUserId = updUserId;
        this.updDateTime = updDateTime;
    }

    public void updateMoveStockAlarm(MoveStockAlarm moveStockAlarm, Integer resellDay, Integer undeliveredDay
            , Member member){
        moveStockAlarm.setResellDay(resellDay);
        moveStockAlarm.setUndeliveredDay(undeliveredDay);
        moveStockAlarm.setUpdUserId(member);
        moveStockAlarm.setUpdDateTime(LocalDateTime.now());
    }
}
