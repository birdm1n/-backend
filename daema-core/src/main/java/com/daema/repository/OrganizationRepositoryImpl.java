package com.daema.repository;

import com.daema.domain.MemberRole;
import com.daema.domain.Organization;
import com.daema.domain.QMember;
import com.daema.domain.QMemberRole;
import com.daema.domain.dto.OrgnztListDto;
import com.daema.domain.dto.OrgnztMemberListDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

public class OrganizationRepositoryImpl extends QuerydslRepositorySupport implements CustomOrganizationRepository {

    public OrganizationRepositoryImpl() {
        super(Organization.class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public HashMap<String, List> getOrgnztAndMemberList(long StoreId) {

        HashMap<String, List> retMap = new HashMap<>();

        retMap.put("orgnztList", searchOrgnztList(StoreId));
        retMap.put("memberList", searchOrgnztMemberList(StoreId));
        retMap.put("memberRoleList", searchOrgnztMemberRoleList(StoreId));

        return retMap;
    }

    private List<OrgnztListDto> searchOrgnztList(long store_id){

        StringBuilder sb = new StringBuilder();
        sb.append("select 1 as depth " +
                "           , org_id " +
                "           , parent_org_id " +
                "           , org_name " +
                "           , concat(org_id, '/') as hierarchy " +
                "        from organization " +
                "       where store_id = :store_id " +
                "         and parent_org_id = 0 " +
                "         and del_yn = 'N' " +
                " " +
                "       union " +
                " " +
                "      select 2 as depth " +
                "           , child.org_id " +
                "           , child.parent_org_id " +
                "           , child.org_name " +
                "           , concat(parent.org_id, '/', child.org_id, '/') as hierarchy " +
                "        from organization child " +
                "        inner join organization parent " +
                "        on child.store_id = :store_id " +
                "            and child.del_yn = 'N' " +
                "            and parent.del_yn = 'N' " +
                "            and parent.parent_org_id = 0 " +
                "            and child.parent_org_id = parent.org_id " +
                " " +
                "       union " +
                " " +
                "      select 3 as depth " +
                "           , child.org_id " +
                "           , child.parent_org_id " +
                "           , child.org_name " +
                "           , concat(parent.hierarchy, child.org_id, '/') as hierarchy " +
                "        from organization child " +
                "        inner join (select child.org_id " +
                "                         , child.parent_org_id " +
                "                         , child.org_name " +
                "                         , concat(parent.org_id, '/', child.org_id, '/') as hierarchy " +
                "                      from organization child " +
                "                      inner join organization parent " +
                "                      on parent.parent_org_id = 0 " +
                "                          and child.parent_org_id = parent.org_id " +
                "                     where child.store_id = :store_id " +
                "                       and child.del_yn = 'N' " +
                "                       and parent.del_yn = 'N') as parent " +
                "        on parent.org_id = child.parent_org_id " +
                "            and child.store_id = :store_id " +
                "            and child.del_yn = 'N' " +
                "      order by hierarchy + '' ");


        Query query = em.createNativeQuery(sb.toString(), "OrgnztList")
                .setParameter("store_id", store_id);

        return query.getResultList();
    }
    
    private List<OrgnztMemberListDto> searchOrgnztMemberList(long store_id){

        StringBuilder sb = new StringBuilder();
        sb.append("select members.seq " +
                "     , members.username " +
                "     , members.name " +
                "     , members.email " +
                "     , members.phone " +
                "     , members.user_status " +
                "     , team_data.org_id " +
                "     , team_data.org_name " +
                "     , case when members.org_id = team_data.org_id " +
                "            then team_data.hierarchy " +
                "        end member_hierarchy " +
                "  from members " +
                "  inner join ( " +
                "      select org_id " +
                "           , org_name " +
                "           , concat(org_id, '/') as hierarchy " +
                "        from organization " +
                "       where store_id = :store_id " +
                "         and parent_org_id = 0 " +
                "         and del_yn = 'N' " +
                " " +
                "       union " +
                " " +
                "      select child.org_id " +
                "           , child.org_name " +
                "           , concat(parent.org_id, '/', child.org_id, '/') as hierarchy " +
                "        from organization child " +
                "        inner join organization parent " +
                "        on child.store_id = :store_id " +
                "            and child.del_yn = 'N' " +
                "            and parent.del_yn = 'N' " +
                "            and parent.parent_org_id = 0 " +
                "            and child.parent_org_id = parent.org_id " +
                " " +
                "       union " +
                " " +
                "      select child.org_id " +
                "           , child.org_name " +
                "           , concat(parent.hierarchy, child.org_id, '/') as hierarchy " +
                "        from organization child " +
                "        inner join (select child.org_id " +
                "                         , child.parent_org_id " +
                "                         , child.org_name " +
                "                         , concat(parent.org_id, '/', child.org_id, '/') as hierarchy " +
                "                      from organization child " +
                "                      inner join organization parent " +
                "                      on parent.parent_org_id = 0 " +
                "                          and child.parent_org_id = parent.org_id " +
                "                     where child.store_id = :store_id " +
                "                       and child.del_yn = 'N' " +
                "                       and parent.del_yn = 'N') as parent " +
                "        on parent.org_id = child.parent_org_id " +
                "            and child.store_id = :store_id " +
                "            and child.del_yn = 'N' " +
                "            and child.del_yn = 'N' " +
                "  ) as team_data " +
                "  on members.store_id = :store_id " +
                "      and members.user_status != 9 " +
                "      and members.org_id = team_data.org_id " +
                " order by hierarchy, members.username + '' ");


        Query query = em.createNativeQuery(sb.toString(), "OrgnztMemberList")
                .setParameter("store_id", store_id);

        return query.getResultList();
    }

    private List<MemberRole> searchOrgnztMemberRoleList(long store_id){

        QMemberRole memberRole = QMemberRole.memberRole;
        QMember member = QMember.member;

        JPQLQuery<MemberRole> query = getQuerydsl().createQuery();
        query.from(memberRole)
                .innerJoin(member)
                .on(
                        member.storeId.eq(store_id)
                        .and(member.userStatus.ne("9"))
                        .and(member.seq.eq(memberRole.seq))
                );

        return query.fetch();
    }
}












