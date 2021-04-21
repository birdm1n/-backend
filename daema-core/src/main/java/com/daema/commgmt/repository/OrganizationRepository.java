package com.daema.commgmt.repository;

import com.daema.commgmt.domain.Organization;
import com.daema.commgmt.repository.custom.CustomOrganizationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Number> , CustomOrganizationRepository {
    Organization findByStoreIdAndOrgName(long storeId, String orgName);
}
