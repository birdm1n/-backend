/*
package com.daema.core.scm.repository.impl;

import com.daema.core.scm.domain.taskboard.TaskBoard;
import com.daema.core.scm.dto.request.TaskBoardReqDto;
import com.daema.core.scm.repository.util.CustomTaskBoardRepository;
import com.daema.core.scm.repository.util.TaskBoardRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TaskBoardRepositoryImpl
 extends QuerydslRepositorySupport
implements TaskBoardRepository {



*
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.

    public TaskBoardRepositoryImpl() {
        super(TaskBoard.
                class);
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public TaskBoard getTaskBoard(TaskBoardReqDto taskBoardReqDto) {

    TaskBoard taskBoard = new TaskBoard();
        return taskBoard;
    }

}
*/
