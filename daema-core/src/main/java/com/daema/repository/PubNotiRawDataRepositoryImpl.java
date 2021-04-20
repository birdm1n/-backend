package com.daema.repository;

import com.daema.domain.PubNotiRawData;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 1. Process
 * - PubNotiRawData. deadLineYn = 'N' 조회
 * - model_name 그룹핑 및 Goods 테이블 조회해서 신규 상품은 Goods 테이블에 인서트
 * - Charge, Goods, CodeDetail(모델명, 제조사, 통신사) 매칭 건만 PubNotiCodeMapData 테이블 인서트
 * - PubNoti 테이블에 공시금액 및 코드 데이터 저장
 * - PubNoti에 정상 저장된 건에 대해 PubNotiRawData, PubNotiCodeMapData 마감 처리
 *
 * 2. Rule
 * - 미리 코드화 되지 않은 데이터는 마감처리 불가
 * - PubNotiCodeMapData 테이블에 금일 마감요청 들어온 건에 대해서만 처리 됨
 */
public class PubNotiRawDataRepositoryImpl extends QuerydslRepositorySupport implements CustomPubNotiRawDataRepository {

    public PubNotiRawDataRepositoryImpl() {
        super(PubNotiRawData.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public void migrationSmartChoiceData(long memberSeq) {

        //신규 상품 DB화
        insertNewGoods();

        //상품_요금_코드화 및 공시 정보 임시 저장
        if(insertCodeMapData(memberSeq) > 0){
            //공시 테이블 인서트 및 원천 데이터 테이블과 코드 맵핑 테이블 마감 처리
            insertPubInfoUpdateRawData(memberSeq);
        }
    }

    private void insertNewGoods(){

        StringBuilder sb = new StringBuilder();
        sb.append("insert into goods (capacity, goods_name, maker, model_name, network, telecom" +
                "  ,regi_datetime, del_yn, matching_yn, origin_key, use_yn) " +
                "  select null " +
                "         ,data.goods_name " +
                "         ,(select code_seq from code_detail cd where cd.code_desc = data.maker_name) as maker " +
                "         ,data.model_name " +
                "         ,(select code_seq from code_detail cd2 where cd2.code_desc = data.network_name) as network " +
                "         ,(select code_seq from code_detail cd3 where cd3.code_desc = data.telecom_name) as telecom " +
                "         ,now() " +
                "         ,'N' " +
                "         ,'N' " +
                "         ,concat('S', data.pub_noti_raw_data_id) " +
                "         ,'N' " +
                "    from pub_noti_raw_data data " +
                "    left join goods " +
                "      on data.model_name = goods.model_name " +
                "   where goods.goods_id is null " +
                "   group by data.model_name ");

        em.createNativeQuery(sb.toString())
                .executeUpdate();
    }

    private int insertCodeMapData(long memberSeq){

        StringBuilder sb = new StringBuilder();
        sb.append("insert ignore into pub_noti_code_map_data (regi_datetime, charge_id, deadline_date, deadline_user_id, deadline_yn, goods_id, pub_noti_raw_data_id, release_amt, release_date, support_amt) " +
                "   select now() " +
                "       , c.charge_id " +
                "       , now() " +
                "       , :memberSeq " +
                "       , 'N' " +
                "       , g.goods_id " +
                "       , pub_noti_raw_data_id " +
                "       , release_amt " +
                "       , release_date " +
                "       , support_amt " +
                "   from pub_noti_raw_data as a " +
                "  inner join charge as c " +
                "     on a.deadline_yn = 'N' " +
                "        and a.charge_code = c.charge_code " +
                "  inner join goods as g " +
                "     on a.model_name = g.model_name " +
                "  inner join code_detail as cd1 " +
                "     on a.maker_name = cd1.code_desc " +
                "  inner join code_detail as cd2 " +
                "     on a.telecom_name = cd2.code_desc " +
                "  inner join code_detail as cd3 " +
                "     on a.network_name = cd3.code_desc ");

        return em.createNativeQuery(sb.toString())
                .setParameter("memberSeq", memberSeq)
                .executeUpdate();
    }

    private void insertPubInfoUpdateRawData(long memberSeq){

        //공시관리테이블 인서트
        StringBuilder sb = new StringBuilder();
        sb.append("insert into pub_noti (regi_datetime, release_amt, support_amt, regi_user_id, release_date" +
                "                        ,charge_id, goods_id, origin_key) " +
                "  select now() " +
                "         , release_amt " +
                "         , support_amt " +
                "         , :memberSeq " +
                "         , release_date " +
                "         , charge_id " +
                "         , goods_id " +
                "         , concat('S', pub_noti_raw_data_id) " +
                "    from pub_noti_code_map_data " +
                "   where deadline_yn = 'N' " +
                "     and deadline_date = date_format(now(), '%Y-%m-%d') ");

        em.createNativeQuery(sb.toString())
                .setParameter("memberSeq", memberSeq)
                .executeUpdate();

        sb.setLength(0);

        //원천 데이터 마감처리
        sb.append("update pub_noti_raw_data p, (select replace(origin_key, 'S', '') as o_key " +
                "                              from pub_noti " +
                "                             where origin_key in (select concat('S', pub_noti_raw_data_id) " +
                "                                                    from pub_noti_code_map_data " +
                "                                                   where deadline_yn = 'N' " +
                "                                                     and deadline_date = date_format(now(), '%Y-%m-%d')" +
                "                                                 )" +
                "                              ) map " +
                "   set p.deadline_yn = 'Y' " +
                "       ,p.deadline_datetime = now() " +
                "       ,p.deadline_user_id = :memberSeq " +
                " where p.pub_noti_raw_data_id = map.o_key ");

        em.createNativeQuery(sb.toString())
                .setParameter("memberSeq", memberSeq)
                .executeUpdate();

        sb.setLength(0);

        //코드 맵핑 데이터 마감처리
        sb.append("update pub_noti_code_map_data p, (select o_key " +
                "                                      from (select replace(origin_key, 'S', '') as o_key " +
                "                                              from pub_noti " +
                "                                             where origin_key in (select concat('S', pub_noti_raw_data_id) " +
                "                                                                    from pub_noti_code_map_data " +
                "                                                                   where deadline_yn = 'N' " +
                "                                                                     and deadline_date = date_format(now(), '%Y-%m-%d')" +
                "                                                                  )" +
                "                                            ) as map" +
                "                                    ) as map " +
                "     set p.deadline_yn = 'Y' " +
                "   where p.pub_noti_raw_data_id = map.o_key ");

        em.createNativeQuery(sb.toString())
                .executeUpdate();
    }
}












