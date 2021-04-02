package com.daema.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="pubNotiRawDataId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="pub_noti_raw_data")
public class PubNotiRawData extends PubNotiBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_noti_raw_data_id")
    private long pubNotiRawDataId;

    @Column(name = "goods_name", length = 30)
    private String goodsName;

    @Column(name = "model_name", length = 20)
    private String modelName;

    @Column(name = "maker_name", length = 20)
    private String makerName;

    @Column(name = "charge_name", length = 30)
    private String chargeName;

    @Column(name = "telecom_name", length = 20)
    private String telecomName;

    @Column(name = "network_name", length = 20)
    private String networkName;

    @Column(name = "release_amt")
    private int releaseAmt;

    @Column(name = "support_amt")
    private int supportAmt;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @Column(name = "deadline_datetime")
    private LocalDateTime deadLineDateTime;

    @Column(name = "deadline_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String deadLineYn;

    @Column(name = "deadline_user_id")
    private Long deadLineUserId;

    @Builder
    public PubNotiRawData(long pubNotiRawDataId, String goodsName, String modelName, String makerName, String chargeName
    ,String telecomName, String networkName, int releaseAmt, int supportAmt, LocalDate releaseDate, LocalDateTime regiDateTime
    ,LocalDateTime deadLineDateTime, String deadLineYn, Long deadLineUserId){
        this.pubNotiRawDataId = pubNotiRawDataId;
        this.goodsName = goodsName;
        this.modelName = modelName;
        this.makerName = makerName;
        this.chargeName = chargeName;
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
