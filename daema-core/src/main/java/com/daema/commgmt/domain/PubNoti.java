package com.daema.commgmt.domain;

import com.daema.commgmt.domain.dto.response.PubNotiMappingDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SqlResultSetMapping(
        name="PubNotiMapping",
        classes = @ConstructorResult(
                targetClass = PubNotiMappingDto.class,
                columns = {
                        @ColumnResult(name="goods_id", type = Long.class),
                        @ColumnResult(name="charge_id", type = Long.class),
                        @ColumnResult(name="support_amt", type = Integer.class),
                        @ColumnResult(name="release_amt", type = Integer.class),
                        @ColumnResult(name="release_date", type = LocalDate.class)
                })
)

@Getter
@Setter
@EqualsAndHashCode(of="pubNotiId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="pub_noti", indexes = @Index(name = "idx_pub_noti_goods_id_charge_id", columnList = "goods_id, charge_id"))
public class PubNoti extends PubNotiBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_noti_id", columnDefinition = "BIGINT unsigned comment '공시 알림 아이디'")
    private Long pubNotiId;

    @Column(name = "regi_user_id", columnDefinition = "BIGINT unsigned comment '등록 유저 아이디'")
    private Long regiUserId;

    @Column(name = "charge_id", columnDefinition = "BIGINT unsigned comment '요금 아이디'")
    private Long chargeId;

    @Column(name = "goods_id", columnDefinition = "BIGINT unsigned comment '상품 아이디'")
    private Long goodsId;

    /**
     * 스마트초이스 연동과 관리자 직접 등록(A) 구분
     * pub_noti_raw_data pk or 'A' : 123, A
     */
    @Column(name = "origin_key", length = 8, columnDefinition = "varchar(255) comment '출처 키'")
    private String originKey;

    @Column(name = "del_yn", columnDefinition ="char(1) comment '삭제 여부' ")
    @ColumnDefault("\"N\"")
    private String delYn;

    @Nullable
    @Column(name = "del_datetime", columnDefinition = "DATETIME(6) comment '삭제 날짜시간'")
    private LocalDateTime delDateTime;

    @Nullable
    @Column(name = "del_user_id", columnDefinition = "BIGINT unsigned comment '삭제 유저 아이디'")
    private Long delUserId;

    @Builder
    public PubNoti(long pubNotiId, int supportAmt, int releaseAmt
            ,LocalDate releaseDate , LocalDateTime regiDateTime, long regiUserId, String originKey
            ,long chargeId, long goodsId, String delYn , LocalDateTime delDateTime, long delUserId){
        this.pubNotiId = pubNotiId;
        this.supportAmt = supportAmt;
        this.releaseAmt = releaseAmt;
        this.releaseDate = releaseDate;
        this.regiUserId = regiUserId;
        this.originKey = originKey;
        this.chargeId = chargeId;
        this.goodsId = goodsId;
        this.delYn = delYn;
        this.delDateTime = delDateTime;
        this.delUserId = delUserId;
        this.regiDateTime = regiDateTime;
    }

    public void updateDelInfo(PubNoti pubNoti, long delUserId, String delYn){
        pubNoti.setDelDateTime(LocalDateTime.now());
        pubNoti.setDelYn(delYn);
        pubNoti.setDelUserId(delUserId);
    }
}
