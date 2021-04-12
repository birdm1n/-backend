package com.daema.dto;

import com.daema.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberMgmtDto {

	private long seq;
	private String name;
	private String email;
	private String address;
	private String phone;
	private LocalDateTime regiDatetime;
	private LocalDateTime updDatetime;
	private long storeId;
	private long orgId;
	/**
	 * 1-신규(승인전), 2-정상(승인완료), 9-삭제
	 */
	private String userStatus;

	public static MemberMgmtDto from (Member member) {
		return MemberMgmtDto.builder()
				.seq(member.getSeq())
				.name(member.getName())
				.email(member.getEmail())
				.address(member.getAddress())
				.phone(member.getPhone())
				.storeId(member.getStoreId())
				.orgId(member.getOrgId())
				.userStatus(member.getUserStatus())
				.regiDatetime(member.getRegiDatetime())
				.updDatetime(member.getUpdDatetime())
			.build();
	}
}
