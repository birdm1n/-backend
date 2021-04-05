package com.daema.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrgnztMemberListDto {

    private long seq;

    private String username;

    private String name;

    private String email;

    private String phone;

    private String userStatus;

    private long orgId;

    private String orgName;

    private String memberHierarchy;

}
