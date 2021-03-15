package com.daema.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
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

	public UserMgmtDto() {

	}
}
