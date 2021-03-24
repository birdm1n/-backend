package com.daema.repository;

import com.daema.domain.OpenStoreSaleStoreMap;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class OpenStoreSaleStoreMapRepositoryImpl extends QuerydslRepositorySupport implements CustomOpenStoreSaleStoreMapRepository {

    public OpenStoreSaleStoreMapRepositoryImpl() {
        super(OpenStoreSaleStoreMap.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<OpenStoreSaleStoreMap> getMappingList(long storeId) {

        StringBuilder sb = new StringBuilder();
        sb.append("select data.open_store_id " +
                "       ,osssm.sale_store_id " +
                "       ,osssm.use_yn " +
                "  from ( " +
                "      select open_store_id " +
                "        from open_store as os " +
                "       where store_id = :storeId " +
                "         and os.del_yn = 'N' " +
                "         and os.use_yn = 'Y' " +
                " " +
                "       union " +
                " " +
                "      select osssm.open_store_id " +
                "        from open_store as os " +
                "        inner join open_store_sale_store_map osssm " +
                "        on osssm.sale_store_id = :storeId " +
                "            and os.open_store_id = osssm.open_store_id " +
                "            and os.del_yn = 'N' " +
                "            and os.use_yn = 'Y' " +
                "            and osssm.use_yn = 'Y' " +
                "  ) as data " +
                " inner join open_store_sale_store_map osssm " +
                "    on data.open_store_id = osssm.open_store_id " +
                " inner join store_map as sm " +
                "    on sm.parent_store_id = :storeId " +
                "       and osssm.sale_store_id = sm.store_id " +
                "       and sm.use_yn = 'Y' " +
                " inner join store s " +
                "    on sm.store_id = s.store_id " +
                "       and s.use_yn = 'Y' ");

        Query query = em.createNativeQuery(sb.toString(), OpenStoreSaleStoreMap.class)
                .setParameter("storeId", storeId);

        return query.getResultList();

    }
}



















