package com.daema.dto;

import com.daema.domain.dto.OrgnztMemberListDto;
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
		public List<Integer> roleIds;

		public OrgnztMemberAndRoleDto(OrgnztMemberListDto member, List<Integer> roleIds) {
			this.member = member;
			this.roleIds = roleIds;
		}
	}
}
