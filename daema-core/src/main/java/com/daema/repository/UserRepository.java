package com.daema.repository;

import com.daema.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    boolean existsByUserIdAndUserPw(String userId, String userPw);
}
