package com.daema.core.commgmt.dto.response;

import com.daema.core.base.domain.Members;
import com.daema.core.commgmt.dto.MemberMgmtDto;
import com.daema.core.commgmt.dto.OpeningStoreMgmtDto;
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
public class OpeningStoreUserResponseDto {

	public List<OpeningStoreMgmtDto> openStoreList;
	public List<OpenStoreUserMap> userList = new ArrayList<>();

	public static class OpenStoreUserMap{
		public MemberMgmtDto user;
		public List<String[]> openStoreMap;

		public OpenStoreUserMap(Members member, List<String[]> filterOpenStoreInfo) {
			user = MemberMgmtDto.from(member);
			openStoreMap = filterOpenStoreInfo;
		}
	}

}
