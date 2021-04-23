package com.daema.commgmt.repository;

import com.daema.commgmt.domain.OpenStore;
import com.daema.commgmt.domain.QOpenStore;
import com.daema.base.domain.common.RetrieveClauseBuilder;
import com.daema.commgmt.domain.dto.response.OpenStoreListDto;
import com.daema.commgmt.domain.dto.request.ComMgmtRequestDto;
import com.daema.commgmt.repository.custom.CustomOpenStoreRepository;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

public class OpenStoreRepositoryImpl extends QuerydslRepositorySupport implements CustomOpenStoreRepository {

    public OpenStoreRepositoryImpl() {
        super(OpenStore.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<OpenStoreListDto> getSearchPage(ComMgmtRequestDto requestDto) {

        long storeId = requestDto.getParentStoreId();
        PageRequest pageable = PageRequest.of(requestDto.getPageNo() - 1, requestDto.getPerPageCnt());

        List<OpenStoreListDto> resultList = searchOpenStoreList(storeId, requestDto, pageable);

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) as totalCnt" +
                " from (");


        sb.append(      "select os.* " +
                        "  from open_store as os" +
                        " inner join code_detail as cd " +
                "            on store_id = :storeId " +
                "               and os.telecom = cd.code_seq " +
                "               and code_id = 'TELECOM' " +
                "               and cd.use_yn = 'Y' " +
                        " where del_yn = 'N' " +
                        "   and store_id = :storeId ");

        whereClause(sb, requestDto);

        sb.append(      " " +
                        "union " +
                        " " +
                        "select os.* " +
                        "  from open_store as os " +
                        " inner join open_store_sale_store_map osssm " +
                        "    on os.open_store_id = osssm.open_store_id " +
                        "       and osssm.sale_store_id = :storeId " +
                        "       and os.del_yn = 'N' " +
                        "       and os.use_yn = 'Y' " +
                        " inner join code_detail as cd " +
                        "    on os.telecom = cd.code_seq " +
                        "       and code_id = 'TELECOM' " +
                        "       and cd.use_yn = 'Y' ");

        whereClause(sb, requestDto);

        sb.append(" ) as data");

        Object totalCnt = em.createNativeQuery(sb.toString())
                .setParameter("storeId", storeId)
                .getSingleResult();

        return new PageImpl<>(resultList, pageable, Long.parseLong(String.valueOf(totalCnt)));
    }

    @Override
    public List<OpenStoreListDto> getOpenStoreList(long storeId) {
        return searchOpenStoreList(storeId, null, null);
    }

    private List<OpenStoreListDto> searchOpenStoreList(long storeId, ComMgmtRequestDto requestDto, Pageable pageable){

        StringBuilder sb = new StringBuilder();

        if(pageable == null){
            sb.append("select os.open_store_id " +
                    "       , os.biz_no " +
                    "       , os.charger_name " +
                    "       , os.charger_phone " +
                    "       , os.open_store_name " +
                    "       , os.regi_datetime " +
                    "       , os.return_addr " +
                    "       , os.return_addr_detail " +
                    "       , os.return_zip_code " +
                    "       , os.store_id " +
                    "       , os.telecom " +
                    "       , os.use_yn " +
                    "       , os.del_yn " +
                    "       , cd.code_nm as telecomName " +
                    "       , :storeId as req_store_id " +
                    "  from open_store as os " +
                    " inner join code_detail as cd " +
                    "            on store_id = :storeId " +
                    "               and os.telecom = cd.code_seq " +
                    "               and code_id = 'TELECOM' " +
                    "               and cd.use_yn = 'Y' " +
                    " where store_id = :storeId " +
                    "   and del_yn = 'N' " +
                    "   and os.use_yn = 'Y' " +
                    "union " +
                    " " +
                    "select os.open_store_id " +
                    "       , os.biz_no " +
                    "       , os.charger_name " +
                    "       , os.charger_phone " +
                    "       , os.open_store_name " +
                    "       , os.regi_datetime " +
                    "       , os.return_addr " +
                    "       , os.return_addr_detail " +
                    "       , os.return_zip_code " +
                    "       , os.store_id " +
                    "       , os.telecom " +
                    "       , os.use_yn " +
                    "       , os.del_yn " +
                    "       , cd.code_nm as telecomName " +
                    "       , :storeId as req_store_id " +
                    "  from open_store as os " +
                    " inner join open_store_sale_store_map osssm " +
                    "    on osssm.sale_store_id = :storeId " +
                    "       and os.open_store_id = osssm.open_store_id " +
                    "       and os.del_yn = 'N' " +
                    "       and os.use_yn = 'Y' " +
                    "       and osssm.use_yn = 'Y' " +
                    " inner join code_detail as cd " +
                    "    on os.telecom = cd.code_seq " +
                    "       and code_id = 'TELECOM' " +
                    "       and cd.use_yn = 'Y' " +
                    " order by open_store_name ");
        }else{
            sb.append("select os.open_store_id " +
                    "       , os.biz_no " +
                    "       , os.charger_name " +
                    "       , os.charger_phone " +
                    "       , os.open_store_name " +
                    "       , os.regi_datetime " +
                    "       , os.return_addr " +
                    "       , os.return_addr_detail " +
                    "       , os.return_zip_code " +
                    "       , os.store_id " +
                    "       , os.telecom " +
                    "       , os.use_yn " +
                    "       , os.del_yn " +
                    "       , cd.code_nm as telecomName " +
                    "       , :storeId as req_store_id " +
                    "  from open_store as os " +
                    " inner join code_detail as cd " +
                    "            on store_id = :storeId " +
                    "               and os.telecom = cd.code_seq " +
                    "               and code_id = 'TELECOM' " +
                    "               and cd.use_yn = 'Y' " +
                    " where store_id = :storeId " +
                    "   and del_yn = 'N' ");

            whereClause(sb, requestDto);

            sb.append(" union " +
                    " " +
                    "select os.open_store_id " +
                    "       , os.biz_no " +
                    "       , os.charger_name " +
                    "       , os.charger_phone " +
                    "       , os.open_store_name " +
                    "       , os.regi_datetime " +
                    "       , os.return_addr " +
                    "       , os.return_addr_detail " +
                    "       , os.return_zip_code " +
                    "       , os.store_id " +
                    "       , os.telecom " +
                    "       , os.use_yn " +
                    "       , os.del_yn " +
                    "       , cd.code_nm as telecomName " +
                    "       , :storeId as req_store_id " +
                    "  from open_store as os " +
                    " inner join open_store_sale_store_map osssm " +
                    "    on osssm.sale_store_id = :storeId " +
                    "       and os.open_store_id = osssm.open_store_id " +
                    "       and os.del_yn = 'N' " +
                    "       and os.use_yn = 'Y' " +
                    " inner join code_detail as cd " +
                    "    on os.telecom = cd.code_seq " +
                    "       and code_id = 'TELECOM' " +
                    "       and cd.use_yn = 'Y' ");

            whereClause(sb, requestDto);

            sb.append(" order by open_store_id desc ");
        }

        Query query = em.createNativeQuery(sb.toString(), "OpenStoreList")
                .setParameter("storeId", storeId);

        if(pageable != null){
            query.setFirstResult(Long.valueOf(pageable.getOffset()).intValue())
                .setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    private void whereClause(StringBuilder sb, ComMgmtRequestDto requestDto){

        if(StringUtils.hasText(requestDto.getOpenStoreName())){
            sb.append(" and os.open_store_name like '%" + requestDto.getOpenStoreName() + "%'");
        }
        if(StringUtils.hasText(requestDto.getBizNo())){
            sb.append(" and os.biz_no like '%" + requestDto.getBizNo() + "%'");
        }
        if(StringUtils.hasText(requestDto.getReturnAddr())){
            sb.append(" and (os.return_addr like '%" + requestDto.getReturnAddr() + "%' " +
                    "        or os.return_addr_detail like '%" + requestDto.getReturnAddr() + "%') ");
        }
        if(requestDto.getTelecom() != null
                && !Arrays.stream(requestDto.getTelecom()).anyMatch(telecom -> telecom == 0)){
            sb.append(" and os.telecom in (" + Arrays.toString(requestDto.getTelecom()).replace("[", "").replace("]", "") + ")");
        }
        if(StringUtils.hasText(requestDto.getChargerPhone())){
            sb.append(" and os.charger_phone like '%" + requestDto.getChargerPhone() + "%'");
        }
        if(StringUtils.hasText(requestDto.getUseYn())
                && !"all".equals(requestDto.getUseYn())){
            sb.append(" and os.use_yn = '" + requestDto.getUseYn() + "'");
        }
        if(StringUtils.hasText(requestDto.getSrhStartDate())
                && StringUtils.hasText(requestDto.getSrhEndDate())){
            sb.append(" and (os.regi_datetime between '" + RetrieveClauseBuilder.stringToLocalDateTime(requestDto.getSrhStartDate(), "s") + "'" +
                    "        and '" + RetrieveClauseBuilder.stringToLocalDateTime(requestDto.getSrhEndDate(), "e") + "')  ");
        }
    }

    @Override
    public OpenStore findOpenStoreInfo(long openStoreId, long storeId) {
        QOpenStore openStore = QOpenStore.openStore;

        JPQLQuery<OpenStore> query = from(openStore);

        query.where(openStore.openStoreId.eq(openStoreId)
                .and(openStore.storeId.eq(storeId)));

        return query.fetchOne();
    }
}



















