package com.daema.rest.commgmt.dto;

import com.daema.base.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationMemberDto {


	private long seq;
	private String username;
	private String password;
	private String chgPassword;
	private String name;
	private String email;
	private String address;
	private String phone;
	private String phone1;
	private String phone2;
	private String phone3;
	private UserRole role = UserRole.ROLE_NOT_PERMITTED;
	private LocalDateTime regiDatetime;
	private LocalDateTime updDatetime;
	private long storeId;
	private long orgId;
	private String userStatus;
	private Integer[] roleIds;
}
