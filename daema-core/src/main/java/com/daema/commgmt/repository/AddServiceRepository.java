package com.daema.commgmt.repository;

import com.daema.commgmt.domain.AddService;
import com.daema.commgmt.repository.custom.CustomAddServiceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddServiceRepository extends JpaRepository<AddService, Number> , CustomAddServiceRepository {
}
