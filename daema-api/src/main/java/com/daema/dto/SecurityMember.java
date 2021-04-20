package com.daema.dto;

import com.daema.domain.Member;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityMember extends User {
    private static final long serialVersionUiD = 1L;

    private long storeId;
    private long memberSeq;
    private Member member;

    public SecurityMember(Member member){
        super(member.getUsername(),"{noop}"+ member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
        setMemberSeq(member.getSeq());
        setStoreId(member.getStoreId());
        setMember(member);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getMemberSeq() {
        return memberSeq;
    }

    public void setMemberSeq(long memberSeq) {
        this.memberSeq = memberSeq;
    }


}
