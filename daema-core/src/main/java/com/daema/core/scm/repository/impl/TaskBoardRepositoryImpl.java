package com.daema.core.scm.repository.impl;

import com.daema.core.scm.domain.taskboard.TaskBoard;
import com.daema.core.scm.repository.util.CustomTaskBoardRepository;
import com.daema.core.scm.repository.util.TaskBoardRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TaskBoardRepositoryImpl extends QuerydslRepositorySupport implements CustomTaskBoardRepository {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public TaskBoardRepositoryImpl(Class<TaskBoard> domainClass) {
        super(domainClass);
    }
}
