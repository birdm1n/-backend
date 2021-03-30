package com.daema.dto;

import com.daema.domain.User2;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMgmtDto {

	private long userId;
	private String email;
	private String userPw;
	private String userName;
	private String userPhone;
	private long storeId;
	private long orgnztId;

	/**
	 * 1-신규(승인전), 2-정상(승인완료), 9-삭제
	 */
	private String userStatus;
	private LocalDateTime lastUpdDateTime;

	public static UserMgmtDto from (User2 user2) {
		return UserMgmtDto.builder()
				.userId(user2.getUserId())
				.email(user2.getEmail())
				/*.userPw(user2.getUserPw())*/
				.userName(user2.getUserName())
				.userPhone(user2.getUserPhone())
				.storeId(user2.getStoreId())
				.orgnztId(user2.getOrgnztId())
				.userStatus(user2.getUserStatus())
				.lastUpdDateTime(user2.getLastUpdDateTime())
			.build();
	}
}
