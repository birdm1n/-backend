package com.daema.dto;

import com.daema.domain.User2;
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
		public UserMgmtDto user;
		public List<String[]> openStoreMap;

		public OpenStoreUserMap(User2 user2, List<String[]> filterOpenStoreInfo) {
			user = UserMgmtDto.from(user2);
			openStoreMap = filterOpenStoreInfo;
		}
	}

}
