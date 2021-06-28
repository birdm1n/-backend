package com.daema.core.commgmt.dto;

import com.daema.core.base.domain.Members;
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
	private String phone1;
	private String phone2;
	private String phone3;
	private LocalDateTime regiDatetime;
	private LocalDateTime updDatetime;
	private long storeId;
	private long orgId;
	/**
	 * 1-신규(승인전), 2-정상(승인완료), 9-삭제
	 */
	private String userStatus;

	public static MemberMgmtDto from (Members member) {
		return MemberMgmtDto.builder()
				.seq(member.getSeq())
				.name(member.getName())
				.email(member.getEmail())
				.address(member.getAddress())
				.phone(member.getPhone())
				.phone1(member.getPhone1())
				.phone2(member.getPhone2())
				.phone3(member.getPhone3())
				.storeId(member.getStoreId())
				.orgId(member.getOrgId())
				.userStatus(member.getUserStatus())
				.regiDatetime(member.getRegiDatetime())
				.updDatetime(member.getUpdDatetime())
			.build();
	}
}
