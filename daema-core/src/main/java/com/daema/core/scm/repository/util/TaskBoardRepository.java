package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.taskboard.TaskBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TaskBoardRepository extends JpaRepository<TaskBoard, Long>, CustomTaskBoardRepository {
}
