package com.daema.repository;

import com.daema.domain.OpenStore;
import com.daema.domain.QOpenStore;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class OpenStoreRepositoryImpl extends QuerydslRepositorySupport implements CustomOpenStoreRepository {

    public OpenStoreRepositoryImpl() {
        super(OpenStore.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<OpenStore> getSearchPage(Pageable pageable) {

        //TODO 하드코딩
        long storeId = 1L;
        List<OpenStore> resultList = searchOpenStoreList(storeId, pageable);

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) as totalCnt" +
                " from (" +
                        "select os.* " +
                        "  from open_store as os " +
                        " where del_yn = 'N' " +
                        "   and store_id = :storeId " +
                        " " +
                        "union " +
                        " " +
                        "select os.* " +
                        "  from open_store as os " +
                        " inner join open_store_sale_store_map osssm " +
                        "    on os.open_store_id = osssm.open_store_id " +
                        "       and osssm.sale_store_id = :storeId " +
                        "       and os.del_yn = 'N' " +
                        "       and os.use_yn = 'Y' " +
                ") as data");

        Object totalCnt = em.createNativeQuery(sb.toString())
                .setParameter("storeId", storeId)
                .getSingleResult();

        return new PageImpl<>(resultList, pageable, Long.parseLong(String.valueOf(totalCnt)));
    }

    @Override
    public List<OpenStore> getOpenStoreList(long storeId) {
        return searchOpenStoreList(storeId, null);
    }

    private List<OpenStore> searchOpenStoreList(long storeId, Pageable pageable){

        StringBuilder sb = new StringBuilder();
        sb.append("select os.* " +
                "  from open_store as os " +
                " where store_id = :storeId " +
                "   and del_yn = 'N' ");

                if(pageable == null){
                    sb.append(" and use_yn = 'Y' ");
                }

        sb.append(" " +
                "union " +
                " " +
                "select os.* " +
                "  from open_store as os " +
                " inner join open_store_sale_store_map osssm " +
                "    on osssm.sale_store_id = :storeId " +
                "       and os.open_store_id = osssm.open_store_id " +
                "       and os.del_yn = 'N' " +
                "       and os.use_yn = 'Y' ");

                if(pageable == null){
                    sb.append(" and osssm.use_yn = 'Y' ");
                    sb.append(" order by open_store_name ");
                }else{
                    sb.append(" order by open_store_id desc ");
                }

        Query query = em.createNativeQuery(sb.toString(), OpenStore.class)
                .setParameter("storeId", storeId);

        if(pageable != null){
            query.setFirstResult(Long.valueOf(pageable.getOffset()).intValue())
                .setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    @Override
    public OpenStore findOpenStoreInfo(long openStoreId, long storeId) {
        QOpenStore openStore = QOpenStore.openStore;

        JPQLQuery<OpenStore> query = from(openStore);

        query.where(openStore.openStoreId.eq(openStoreId)
                .and(openStore.storeId.eq(storeId)));

        OpenStore result = query.fetchOne();

        return result;
    }
}



















