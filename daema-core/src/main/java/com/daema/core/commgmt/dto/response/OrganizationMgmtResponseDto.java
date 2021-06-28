package com.daema.core.commgmt.dto.response;

import com.daema.core.commgmt.dto.FuncRoleMgmtDto;
import com.daema.core.commgmt.dto.OrganizationMgmtDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMgmtResponseDto {

	public List<OrganizationMgmtDto> orgnztList;
	public List<OrgnztMemberAndRoleDto> memberList;
	public List<FuncRoleMgmtDto.RoleMgmtDto> storeRoleList;

	public static class OrgnztMemberAndRoleDto {

		public OrgnztMemberListDto member;
		public List<Long> roleIds;

		public OrgnztMemberAndRoleDto(OrgnztMemberListDto member, List<Long> roleIds) {
			this.member = member;
			this.roleIds = roleIds;
		}
	}
}
