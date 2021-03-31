package com.daema.repository;

import com.daema.domain.AddService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddServiceRepository extends JpaRepository<AddService, Number> , CustomAddServiceRepository {
}
