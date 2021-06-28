package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.OpenStoreUserMap;
import com.daema.core.commgmt.repository.custom.CustomOpenStoreUserMapRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class OpenStoreUserMapRepositoryImpl extends QuerydslRepositorySupport implements CustomOpenStoreUserMapRepository {

    public OpenStoreUserMapRepositoryImpl() {
        super(OpenStoreUserMap.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<OpenStoreUserMap> getMappingList(long storeId) {

        StringBuilder sb = new StringBuilder();
        sb.append("select data.opening_store_id " +
                "       ,osum.user_id " +
                "       ,osum.use_yn " +
                "  from ( " +
                "      select opening_store_id " +
                "        from open_store as os " +
                "       where store_id = :storeId " +
                "         and os.del_yn = 'N' " +
                "         and os.use_yn = 'Y' " +
                " " +
                "       union " +
                " " +
                "      select osssm.opening_store_id " +
                "        from open_store as os " +
                "        inner join open_store_sale_store_map osssm " +
                "        on osssm.sale_store_id = :storeId " +
                "            and os.opening_store_id = osssm.opening_store_id " +
                "            and os.del_yn = 'N' " +
                "            and os.use_yn = 'Y' " +
                "            and osssm.use_yn = 'Y' " +
                "  ) as data " +
                " inner join open_store_user_map osum " +
                "    on data.opening_store_id = osum.opening_store_id " +
                " inner join members as u " +
                "    on u.store_id = :storeId " +
                "       and osum.user_id = u.member_id " +
                "       and u.user_status = 6 ");

        Query query = em.createNativeQuery(sb.toString(), OpenStoreUserMap.class)
                .setParameter("storeId", storeId);

        return query.getResultList();

    }
}



















