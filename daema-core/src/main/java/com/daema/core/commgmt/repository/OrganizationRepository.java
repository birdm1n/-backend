package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.Organization;
import com.daema.core.commgmt.repository.custom.CustomOrganizationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Number> , CustomOrganizationRepository {
    Organization findByStoreIdAndOrgName(long storeId, String orgName);
}
