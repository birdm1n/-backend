package com.daema.core.commgmt.domain;

import com.daema.core.commgmt.dto.response.PubNotiRawDataListDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SqlResultSetMapping(
        name="PubNotiRawDataList",
        classes = @ConstructorResult(
                targetClass = PubNotiRawDataListDto.class,
                columns = {
                        @ColumnResult(name="pub_noti_raw_data_id", type = Long.class),
                        @ColumnResult(name="regi_datetime", type = LocalDateTime.class),
                        @ColumnResult(name="release_amt", type = Integer.class),
                        @ColumnResult(name="release_date", type = LocalDate.class),
                        @ColumnResult(name="support_amt", type = Integer.class),
                        @ColumnResult(name="charge_name", type = String.class),
                        @ColumnResult(name="deadline_datetime", type = LocalDateTime.class),
                        @ColumnResult(name="deadline_user_id", type = Long.class),
                        @ColumnResult(name="deadline_yn", type = String.class),
                        @ColumnResult(name="goods_name", type = String.class),
                        @ColumnResult(name="maker_name", type = String.class),
                        @ColumnResult(name="model_name", type = String.class),
                        @ColumnResult(name="telecom_name", type = String.class),
                        @ColumnResult(name="network_name", type = String.class),
                        @ColumnResult(name="charge_code", type = String.class),
                        @ColumnResult(name="diff_release_amt", type = Integer.class),
                        @ColumnResult(name="diff_support_amt", type = Integer.class),
                        @ColumnResult(name="prev_release_date", type = LocalDate.class),
                        @ColumnResult(name="prev_release_amt", type = Integer.class)
                })
)


@Getter
@Setter
@EqualsAndHashCode(of="pubNotiRawDataId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "pub_noti_raw_data", comment = "?????? ?????? ?????? ?????????")
public class PubNotiRawData extends PubNotiBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_noti_raw_data_id", columnDefinition = "BIGINT unsigned comment '?????? ?????? ?????? ????????? ?????????'")
    private long pubNotiRawDataId;

    @Column(name = "goods_name", columnDefinition = "varchar(255) comment '?????? ??????'")
    private String goodsName;

    @Column(name = "model_name", columnDefinition = "varchar(255) comment '?????? ??????'")
    private String modelName;

    @Column(name = "maker_name", columnDefinition = "varchar(255) comment '????????? ??????'")
    private String makerName;

    @Column(name = "charge_name", columnDefinition = "varchar(255) comment '?????? ??????'")
    private String chargeName;

    @Column(name = "charge_code", columnDefinition = "varchar(255) comment '?????? ??????'")
    private String chargeCode;

    @Column(name = "telecom_name", columnDefinition = "varchar(255) comment '????????? ??????'")
    private String telecomName;

    @Column(name = "network_name", columnDefinition = "varchar(255) comment '????????? ??????'")
    private String networkName;

    @Column(name = "deadline_datetime", columnDefinition = "DATETIME(6) comment '?????? ??????'")
    private LocalDateTime deadLineDateTime;

    @Column(name = "deadline_yn", columnDefinition ="char(1) comment '?????? ??????'")
    @ColumnDefault("\"N\"")
    private String deadLineYn;

    @Column(name = "deadline_user_id", columnDefinition = "BIGINT unsigned comment '?????? ?????? ?????????'")
    private Long deadLineUserId;

    @Builder
    public PubNotiRawData(long pubNotiRawDataId, String goodsName, String modelName, String makerName, String chargeName, String chargeCode
    ,String telecomName, String networkName, int releaseAmt, int supportAmt, LocalDate releaseDate, LocalDateTime regiDateTime
    ,LocalDateTime deadLineDateTime, String deadLineYn, Long deadLineUserId){
        this.pubNotiRawDataId = pubNotiRawDataId;
        this.goodsName = goodsName;
        this.modelName = modelName;
        this.makerName = makerName;
        this.chargeName = chargeName;
        this.chargeCode = chargeCode;
        this.telecomName = telecomName;
        this.networkName = networkName;
        this.releaseAmt = releaseAmt;
        this.supportAmt = supportAmt;
        this.releaseDate = releaseDate;
        this.regiDateTime = regiDateTime;
        this.deadLineDateTime = deadLineDateTime;
        this.deadLineYn = deadLineYn;
        this.deadLineUserId = deadLineUserId;
    }

    public void updateDeadLineInfo(PubNotiRawData pubNotiRawData, long deadLineUserId, String deadLineYn){
        pubNotiRawData.setDeadLineDateTime(LocalDateTime.now());
        pubNotiRawData.setDeadLineUserId(deadLineUserId);
        pubNotiRawData.setDeadLineYn(deadLineYn);
    }
}
