package com.daema.dto;

import com.daema.domain.Organization;
import com.daema.domain.dto.OrgnztListDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationMgmtDto {

	private long orgId;
	private long parentOrgId;
	private String orgName;
	private long storeId;
	private String delYn;
	private int depth;
	private String hierarchy;

	/**
	 * 조직도 JS 에 맞춰 바인딩
	 */
	private long id;
	private String name;

	@JsonInclude(Include.NON_NULL)
	private List<OrganizationMgmtDto> children;

	public static OrganizationMgmtDto from (Organization organization) {
		return OrganizationMgmtDto.builder()
				.orgId(organization.getOrgId())
				.parentOrgId(organization.getParentOrgId())
				.orgName(organization.getOrgName())
				.storeId(organization.getStoreId())
				.delYn(organization.getDelYn())
			.build();
	}

	public static OrganizationMgmtDto dtoToDto (OrgnztListDto orgnztListDto) {
		return OrganizationMgmtDto.builder()
				.depth(orgnztListDto.getDepth())
				.id(orgnztListDto.getOrgId())
				.parentOrgId(orgnztListDto.getParentOrgId())
				.name(orgnztListDto.getOrgName())
				.hierarchy(orgnztListDto.getHierarchy())
			.build();
	}
}
