package com.daema.commgmt.repository;

import com.daema.base.enums.StatusEnum;
import com.daema.commgmt.domain.*;
import com.daema.commgmt.repository.custom.CustomPubNotiRepository;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

public class PubNotiRepositoryImpl extends QuerydslRepositorySupport implements CustomPubNotiRepository {

    public PubNotiRepositoryImpl() {
        super(PubNoti.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public HashMap<String, List> getMappingList(Long telecom, Long network) {

        HashMap<String, List> retMap = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append("select data.goods_id " +
                "       , data.charge_id " +
                "       , data.release_amt " +
                "       , data.support_amt " +
                "       , data.release_date " +
                "  from (select data.goods_id " +
                "             , data.charge_id " +
                "             , data.release_amt " +
                "             , data.support_amt " +
                "             , data.release_date " +
                "             , case @id_group when concat(data.charge_id, data.goods_id) " +
                "                              then @row_num \\:= @row_num + 1 " +
                "                              else @row_num \\:= 1 " +
                "                end as row_num " +
                "              , @id_group \\:= concat(data.charge_id, data.goods_id) " +
                "          from (select pn.* " +
                "                  from pub_noti as pn " +
                "                 inner join goods as g " +
                "                    on g.goods_id = pn.goods_id " +
                "                       and g.telecom_code_id = :telecom " +
                "                       and g.network_code_id = :network " +
                "                       and g.del_yn = 'N' " +
                "                       and g.use_yn = 'Y' " +
                "                       and g.matching_yn = 'Y' " +
                "                       and pn.del_yn = 'N' " +
                "                 inner join charge as c " +
                "                    on c.charge_id = pn.charge_id " +
                "                       and c.telecom_code_id = :telecom " +
                "                       and c.network_code_id = :network " +
                "                       and c.del_yn = 'N' " +
                "                       and c.use_yn = 'Y' " +
                "                       and c.matching_yn = 'Y' " +
                "                       and pn.del_yn = 'N' " +
                "               ) as data, (select @id_group \\:= '', @row_num \\:= 0) r " +
                "         order by goods_id, charge_id, release_date desc, regi_datetime desc " +
                "       ) as data " +
                " where row_num = 1 ");

        Query query = em.createNativeQuery(sb.toString(), "PubNotiMapping")
                .setParameter("telecom", telecom)
                .setParameter("network", network);

        retMap.put("goods", getGoodsList(telecom, network));
        retMap.put("charge", getChargeList(telecom, network));
        retMap.put("mapping", query.getResultList());

        return retMap;
    }

    @Override
    public List<PubNoti> getHistoryList(PubNoti reqPubNoti) {
        QPubNoti pubNoti = QPubNoti.pubNoti;

        JPQLQuery<PubNoti> query = getQuerydsl().createQuery();
        query.from(pubNoti)
                .where(
                        pubNoti.chargeId.eq(reqPubNoti.getChargeId())
                        .and(pubNoti.goodsId.eq(reqPubNoti.getGoodsId()))
                        .and(pubNoti.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
                )
                .orderBy(pubNoti.releaseDate.desc()
                        ,pubNoti.regiDateTime.desc());
        return query.fetch();
    }

    private List<Goods> getGoodsList(Long telecom, Long network){
        QGoods goods = QGoods.goods;

        JPQLQuery<Goods> goodsQuery = getQuerydsl().createQuery();
        goodsQuery.from(goods)
                .where(
                        goods.networkAttribute.telecom.eq(telecom)
                                .and(goods.networkAttribute.network.eq(network))
                                .and(goods.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
                                .and(goods.matchingYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
                                .and(goods.useYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
                )
                .orderBy(goods.goodsName.asc());

        return goodsQuery.fetch();
    }

    private List<Charge> getChargeList(Long telecom, Long network){
        QCharge charge = QCharge.charge;

        JPQLQuery<Charge> chargeQuery = getQuerydsl().createQuery();
        chargeQuery.from(charge)
                .where(
                        charge.networkAttribute.telecom.eq(telecom)
                                .and(charge.networkAttribute.network.eq(network))
                                .and(charge.delYn.eq(StatusEnum.FLAG_N.getStatusMsg()))
                                .and(charge.matchingYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
                                .and(charge.useYn.eq(StatusEnum.FLAG_Y.getStatusMsg()))
                )
                .orderBy(charge.chargeName.asc());

        return chargeQuery.fetch();
    }
}












