package com.daema.repository;

import com.daema.domain.MemberRole;
import com.daema.domain.Organization;
import com.daema.domain.QMember;
import com.daema.domain.QMemberRole;
import com.daema.domain.dto.OrgnztListDto;
import com.daema.domain.dto.OrgnztMemberListDto;
import com.daema.domain.dto.common.SearchParamDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

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
    public HashMap<String, List> getOrgnztAndMemberList(SearchParamDto requestDto) {

        HashMap<String, List> retMap = new HashMap<>();

        retMap.put("orgnztList", searchOrgnztList(requestDto));
        retMap.put("memberList", searchOrgnztMemberList(requestDto));
        retMap.put("memberRoleList", searchOrgnztMemberRoleList(requestDto));

        return retMap;
    }

    private List<OrgnztListDto> searchOrgnztList(SearchParamDto requestDto){

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
                .setParameter("store_id", requestDto.getStoreId());

        return query.getResultList();
    }
    
    private List<OrgnztMemberListDto> searchOrgnztMemberList(SearchParamDto requestDto){

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
                "      and members.org_id = team_data.org_id ");

        if(StringUtils.hasText(requestDto.getEmail())){
            sb.append(" and members.email like '%" + requestDto.getEmail() + "%'");
        }
        if(StringUtils.hasText(requestDto.getName())){
            sb.append(" and members.name like '%" + requestDto.getName() + "%'");
        }
        if(StringUtils.hasText(requestDto.getPhone())){
            sb.append(" and members.phone like '%" + requestDto.getPhone() + "%'");
        }

        sb.append(" order by hierarchy, members.username + '' ");


        Query query = em.createNativeQuery(sb.toString(), "OrgnztMemberList")
                .setParameter("store_id", requestDto.getStoreId());

        return query.getResultList();
    }

    private List<MemberRole> searchOrgnztMemberRoleList(SearchParamDto requestDto){

        QMemberRole memberRole = QMemberRole.memberRole;
        QMember member = QMember.member;

        JPQLQuery<MemberRole> query = getQuerydsl().createQuery();
        query.from(memberRole)
                .innerJoin(member)
                .on(
                        member.storeId.eq(requestDto.getStoreId())
                        .and(member.userStatus.ne("9"))
                        .and(member.seq.eq(memberRole.seq))
                );

        return query.fetch();
    }
}












