package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.appform.AppForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppFormRepository extends JpaRepository<AppForm, Long> {

}
