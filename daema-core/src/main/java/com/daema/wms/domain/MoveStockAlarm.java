package com.daema.wms.domain;

import com.daema.base.domain.Members;
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
@org.hibernate.annotations.Table(appliesTo = "move_stock_alarm", comment = "이동 재고 알람")
public class MoveStockAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "move_stock_alarm_id" , columnDefinition = "BIGINT unsigned comment '이동 재고 알람 아이디'")
    private Long moveStockAlarmId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", unique = true, columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private Store store;

    //재판매 마감일자
    @Column(name = "resell_day", columnDefinition = "int comment '재판매 일'")
    private Integer resellDay;

    //미출고 시점
    @Column(name = "undelivered_day", columnDefinition = "int comment '미출고 일'")
    private Integer undeliveredDay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upd_user_id", referencedColumnName = "member_id", columnDefinition = "BIGINT unsigned comment '수정 유저 아이디'")
    private Members updUserId;

    @CreatedDate
    @Column(name = "upd_datetime", columnDefinition = "DATETIME(6) comment '수정 일시'")
    private LocalDateTime updDateTime;

    @Builder
    public MoveStockAlarm(Long moveStockAlarmId, Store store, Integer resellDay, Integer undeliveredDay, Members updUserId, LocalDateTime updDateTime) {
        this.moveStockAlarmId = moveStockAlarmId;
        this.store = store;
        this.resellDay = resellDay;
        this.undeliveredDay = undeliveredDay;
        this.updUserId = updUserId;
        this.updDateTime = updDateTime;
    }

    public void updateMoveStockAlarm(MoveStockAlarm moveStockAlarm, Integer resellDay, Integer undeliveredDay
            , Members member){
        moveStockAlarm.setResellDay(resellDay);
        moveStockAlarm.setUndeliveredDay(undeliveredDay);
        moveStockAlarm.setUpdUserId(member);
        moveStockAlarm.setUpdDateTime(LocalDateTime.now());
    }
}
