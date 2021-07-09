package com.daema.core.sms.repository;

import com.daema.core.sms.domain.AppForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppFormRepository extends JpaRepository<AppForm, Long> {

}
