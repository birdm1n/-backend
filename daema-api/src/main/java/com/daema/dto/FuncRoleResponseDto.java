package com.daema.dto;

import com.daema.domain.FuncMgmt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncRoleResponseDto {

	public List<FuncRoleMgmtDto.RoleMgmtDto> roleList;
	public List<FuncRoleMapDto> funcRoleList = new ArrayList<>();

	public static class FuncRoleMapDto {
		public FuncRoleMgmtDto.FuncMgmtDto func;
		public List<String[]> roleMap;

		public FuncRoleMapDto(FuncMgmt funcMgmt, List<String[]> filterRoleInfo) {
			func = FuncRoleMgmtDto.FuncMgmtDto.from(funcMgmt);
			roleMap = filterRoleInfo;
		}
	}

}
