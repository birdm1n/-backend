package com.daema.core.commgmt.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of="pubNotiRawDataId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "pub_noti_code_map_data", comment = "공시 알림 코드 맵핑 데이터")
public class PubNotiCodeMapData extends PubNotiBase {

    @Id
    @Column(name = "pub_noti_raw_data_id", columnDefinition = "BIGINT unsigned comment '공시 알림 원본 데이터 아이디'")
    private Long pubNotiRawDataId;

    @Column(name = "charge_id", columnDefinition = "BIGINT unsigned comment '요금 아이디'")
    private Long chargeId;

    @Column(name = "goods_id", columnDefinition = "BIGINT unsigned comment '상품 아이디'")
    private Long goodsId;

    @Column(name = "deadline_date", columnDefinition = "DATE comment '마감 일자'")
    private LocalDate deadLineDate;

    @Column(name = "deadline_yn", columnDefinition ="char(1) comment '마감 여부'")
    @ColumnDefault("\"N\"")
    private String deadLineYn;

    @Column(name = "deadline_user_id", columnDefinition = "BIGINT unsigned comment '마감 유저 아이디'")
    private Long deadLineUserId;

    public void updateDeadLineInfo(PubNotiCodeMapData pubNotiCodeMapData, long deadLineUserId, String deadLineYn){
        pubNotiCodeMapData.setDeadLineDate(LocalDate.now());
        pubNotiCodeMapData.setDeadLineUserId(deadLineUserId);
        pubNotiCodeMapData.setDeadLineYn(deadLineYn);
    }
}
