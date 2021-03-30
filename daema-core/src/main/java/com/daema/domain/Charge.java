package com.daema.domain;

import com.daema.domain.attr.NetworkAttribute;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="chargeId")
@ToString
@Entity
@Table(name="charge")
public class Charge extends ChargeBase{

    public Charge() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_id")
    private long chargeId;

    /**
     * 스마트초이스 연동(S)과 요청 승인(R) 구분
     * 코드 + pk : S123, R123
     */
    @Column(name = "origin_key", length = 8)
    private String originKey;

    @Nullable
    @Column(name = "use_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String useYn;

    @Nullable
    @Column(name = "matching_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String matchingYn;

    @Nullable
    @Column(name = "del_yn", columnDefinition ="char(1)")
    @ColumnDefault("\"N\"")
    private String delYn;

    @Builder
    public Charge(long chargeId, String chargeName, int chargeAmt, String category, int telecom, int network
            , String originKey, LocalDateTime regiDateTime, String useYn, String matchingYn, String delYn){
        this.chargeId = chargeId;
        this.chargeName = chargeName;
        this.chargeAmt = chargeAmt;
        this.category = category;
        this.networkAttribute = new NetworkAttribute(telecom, network);
        this.originKey = originKey;
        this.useYn = useYn;
        this.matchingYn = matchingYn;
        this.delYn = delYn;
        this.regiDateTime = regiDateTime;
    }

    public void updateUseYn(Charge charge, String useYn){
        charge.setUseYn(useYn);
    }

    public void updateDelYn(Charge charge, String delYn){
        charge.setDelYn(delYn);
    }

    public void updateMatchYn(Charge charge, String matchingYn){
        charge.setMatchingYn(matchingYn);
    }
}
