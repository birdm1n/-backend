package com.daema.repository;

import com.daema.domain.User2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User2Repository extends JpaRepository<User2, Long> , CustomUser2Repository {
}
