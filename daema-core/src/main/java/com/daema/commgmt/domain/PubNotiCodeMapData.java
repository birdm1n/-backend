package com.daema.commgmt.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of="seq")
@ToString
@NoArgsConstructor
@Entity
@Table(name="pub_noti_code_map_data")
public class PubNotiCodeMapData extends PubNotiBase {

    @Id
    @Column(name = "pub_noti_raw_data_id")
    private long pubNotiRawDataId;

    @Column(name = "charge_id")
    private long chargeId;

    @Column(name = "goods_id")
    private long goodsId;

    @Column(name = "deadline_date")
    private LocalDate deadLineDate;

    @Column(name = "deadline_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String deadLineYn;

    @Column(name = "deadline_user_id")
    private Long deadLineUserId;

    public void updateDeadLineInfo(PubNotiCodeMapData pubNotiCodeMapData, long deadLineUserId, String deadLineYn){
        pubNotiCodeMapData.setDeadLineDate(LocalDate.now());
        pubNotiCodeMapData.setDeadLineUserId(deadLineUserId);
        pubNotiCodeMapData.setDeadLineYn(deadLineYn);
    }
}
