package com.daema.core.commgmt.repository;

import com.daema.core.commgmt.domain.AddService;
import com.daema.core.commgmt.repository.custom.CustomAddServiceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddServiceRepository extends JpaRepository<AddService, Number> , CustomAddServiceRepository {
}
