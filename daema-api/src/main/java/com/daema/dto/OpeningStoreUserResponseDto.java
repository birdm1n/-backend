package com.daema.dto;

import com.daema.domain.Member;
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

		public OpenStoreUserMap(Member member, List<String[]> filterOpenStoreInfo) {
			user = MemberMgmtDto.from(member);
			openStoreMap = filterOpenStoreInfo;
		}
	}

}
