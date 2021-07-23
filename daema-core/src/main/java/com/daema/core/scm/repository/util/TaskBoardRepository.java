package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.taskboard.ApplicationTaskBoard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskBoardRepository extends JpaRepository<ApplicationTaskBoard, Long> {
}
