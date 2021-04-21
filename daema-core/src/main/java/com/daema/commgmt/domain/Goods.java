package com.daema.commgmt.domain;

import com.daema.commgmt.domain.attr.NetworkAttribute;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of="goodsId")
@ToString
@NoArgsConstructor
@Entity
@Table(name="goods")
public class Goods extends GoodsBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private long goodsId;

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

    @Transient
    private List<GoodsOption> optionList;

    @Builder
    public Goods(long goodsId, String goodsName, String modelName, int maker, int telecom, int network, String capacity
            , String originKey, LocalDateTime regiDateTime, String useYn, String matchingYn, String delYn, String makerName, String networkName, String telecomName){
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.modelName = modelName;
        this.maker = maker;
        this.networkAttribute = new NetworkAttribute(telecom, network);
        this.capacity = capacity;
        this.originKey = originKey;
        this.useYn = useYn;
        this.matchingYn = matchingYn;
        this.delYn = delYn;
        this.regiDateTime = regiDateTime;
        this.makerName = makerName;
        this.networkName = networkName;
        this.telecomName = telecomName;
    }

    public void updateUseYn(Goods goods, String useYn){
        goods.setUseYn(useYn);
    }

    public void updateDelYn(Goods goods, String delYn){
        goods.setDelYn(delYn);
    }

    public void updateMatchYn(Goods goods, String matchingYn){
        goods.setMatchingYn(matchingYn);
    }
}
