package com.daema.commgmt.repository;

import com.daema.commgmt.domain.PubNotiRawData;
import com.daema.commgmt.domain.dto.response.PubNotiRawDataListDto;
import com.daema.commgmt.repository.custom.CustomPubNotiRawDataRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class PubNotiRawDataRepositoryImpl extends QuerydslRepositorySupport implements CustomPubNotiRawDataRepository {

    public PubNotiRawDataRepositoryImpl() {
        super(PubNotiRawData.class);
    }

    @PersistenceContext
    private EntityManager em;

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
        sb.append("insert into goods (goods_name, maker_code_id, model_name, network_code_id, telecom_code_id" +
                "  ,regi_datetime, del_yn, matching_yn, origin_key, use_yn) " +
                "  select data.goods_name " +
                "         ,(select code_id from code_detail cd where cd.code_name = data.maker_name and code_group = 'MAKER' ) as maker " +
                "         ,data.model_name " +
                "         ,(select code_id from code_detail cd2 where cd2.code_name = data.network_name and code_group = 'NETWORK' ) as network " +
                "         ,(select code_id from code_detail cd3 where cd3.code_name = data.telecom_name and code_group = 'TELECOM' ) as telecom " +
                "         ,now() " +
                "         ,'N' " +
                "         ,'N' " +
                "         ,concat('S', data.pub_noti_raw_data_id) " +
                "         ,'N' " +
                "    from pub_noti_raw_data data " +
                "    left join goods " +
                "      on data.model_name = goods.model_name " +
                "   where goods.goods_id is null " +
                "   group by data.model_name, data.network_name, telecom_name ");

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
                "     on a.maker_name = cd1.code_name" +
                "        and cd1.code_group = 'MAKER'  " +
                "  inner join code_detail as cd2 " +
                "     on a.telecom_name = cd2.code_name " +
                "        and cd2.code_group = 'TELECOM'  " +
                "  inner join code_detail as cd3 " +
                "     on a.network_name = cd3.code_name " +
                "        and cd3.code_group = 'NETWORK'  ");

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
                "     and deadline_date >= concat(date_format(now(), '%Y-%m-%d'), ' 00:00:00') ");

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
                "                                                     and deadline_date >= concat(date_format(now(), '%Y-%m-%d'), ' 00:00:00') " +
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
                "                                                                     and deadline_date >= concat(date_format(now(), '%Y-%m-%d'), ' 00:00:00') " +
                "                                                                  )" +
                "                                            ) as map" +
                "                                    ) as map " +
                "     set p.deadline_yn = 'Y' " +
                "   where p.pub_noti_raw_data_id = map.o_key ");

        em.createNativeQuery(sb.toString())
                .executeUpdate();
    }

    @Override
    public List<PubNotiRawDataListDto> searchPubNotiRawData() {

        StringBuilder sb = new StringBuilder();
        sb.append("select raw.pub_noti_raw_data_id " +
                "       ,raw.regi_datetime " +
                "       ,raw.release_amt " +
                "       ,raw.release_date " +
                "       ,raw.support_amt " +
                "       ,raw.charge_name " +
                "       ,raw.deadline_datetime " +
                "       ,raw.deadline_user_id " +
                "       ,raw.deadline_yn " +
                "       ,raw.goods_name " +
                "       ,raw.maker_name " +
                "       ,raw.model_name " +
                "       ,raw.telecom_name " +
                "       ,raw.network_name " +
                "       ,raw.charge_code " +
                "       ,raw.release_amt - data.prev_release_amt as diff_release_amt " +
                "       ,raw.support_amt - data.prev_support_amt as diff_support_amt " +
                "       ,data.prev_release_date as prev_release_date " +
                "       ,data.prev_release_amt as prev_release_amt " +
                "  from (select * " +
                "        from pub_noti_raw_data " +
                "       where deadline_yn = 'N' " +
                "  ) as raw " +
                "  left join (select pub_noti_id, model_name " +
                "                   , charge_code " +
                "                   , p.release_date as prev_release_date " +
                "                   , p.release_amt as prev_release_amt " +
                "                   , p.support_amt as prev_support_amt " +
                "              from pub_noti as p " +
                "             inner join (select max(release_date) as max_release_date " +
                "                                , a.goods_id " +
                "                                , a.charge_id " +
                "                                , model_name " +
                "                                , charge_code " +
                "                           from pub_noti as a " +
                "                          inner join goods as b " +
                "                             on a.goods_id = b.goods_id " +
                "                                and b.use_yn = 'Y' " +
                "                                and b.del_yn = 'N' " +
                "                          inner join charge c " +
                "                             on a.charge_id = c.charge_id " +
                "                                and c.use_yn = 'Y' " +
                "                                and c.del_yn = 'N' " +
                "                          group by a.goods_id, a.charge_id " +
                "                          order by null " +
                "                        ) as data " +
                "                on p.charge_id = data.charge_id " +
                "                   and p.goods_id = data.goods_id " +
                "                   and p.release_date = data.max_release_date " +
                "             where p.del_yn = 'N' " +
                "            ) as data " +
                "         on raw.model_name = data.model_name " +
                "            and raw.charge_code = data.charge_code " +
                " order by raw.telecom_name, raw.network_name, raw.goods_name, raw.charge_name ");

        Query query = em.createNativeQuery(sb.toString(), "PubNotiRawDataList");

        return query.getResultList();
    }
}












































