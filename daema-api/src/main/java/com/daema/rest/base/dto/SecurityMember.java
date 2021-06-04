package com.daema.rest.base.dto;

import com.daema.base.domain.Members;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class SecurityMember extends User {
    private static final long serialVersionUiD = 1L;

    private long storeId;
    private long memberSeq;
    private Members member;
    private List<String> memberFuncList;

    public SecurityMember(Members member){
        super(member.getUsername(),"{noop}"+ member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
        setMemberSeq(member.getSeq());
        setStoreId(member.getStoreId());
        setMember(member);
    }

    public Members getMember() {
        return member;
    }

    public void setMember(Members member) {
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

    public List<String> getMemberFuncList() {
        return memberFuncList;
    }

    public void setMemberFuncList(List<String> memberFuncList) {
        this.memberFuncList = memberFuncList;
    }
}
