package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepoistory extends JpaRepository<Application, Long> {

}
