package com.daema.repository;

import com.daema.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Number> , CustomOrganizationRepository {
    Organization findByStoreIdAndOrgName(long storeId, String orgName);
}
