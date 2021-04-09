package com.daema.dto;

import com.daema.domain.FuncMgmt;
import com.daema.domain.RoleMgmt;
import lombok.*;

import java.time.LocalDateTime;

public class FuncRoleMgmtDto {

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class FuncMgmtDto {
		private String funcId;
		private int groupId;
		private String groupName;
		private String title;
		private String role;
		private String urlPath;
		private int orderNum;

		public static FuncMgmtDto from (FuncMgmt funcMgmt) {
			return FuncMgmtDto.builder()
					.funcId(funcMgmt.getFuncId())
					.groupId(funcMgmt.getGroupId())
					.groupName(funcMgmt.getGroupName())
					.title(funcMgmt.getTitle())
					.role(funcMgmt.getRole())
					.urlPath(funcMgmt.getUrlPath())
					.orderNum(funcMgmt.getOrderNum())
					.build();
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class RoleMgmtDto {
		private int roleId;
		private String roleName;
		private String necessaryYn= "N";
		private String delYn = "N";
		private long storeId;
		private LocalDateTime regiDateTime;

		public static RoleMgmtDto from (RoleMgmt roleMgmt) {
			return RoleMgmtDto.builder()
					.roleId(roleMgmt.getRoleId())
					.roleName(roleMgmt.getRoleName())
					.necessaryYn(roleMgmt.getNecessaryYn())
					.delYn(roleMgmt.getDelYn())
					.storeId(roleMgmt.getStoreId())
					.regiDateTime(roleMgmt.getRegiDateTime())
					.build();
		}
	}
}
