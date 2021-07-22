package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.taskboard.TaskBoard;
import com.daema.core.scm.dto.TaskBoardDto;
import com.daema.core.scm.dto.request.TaskBoardReqDto;

public interface CustomTaskBoardRepository {


    TaskBoard getTaskBoard(TaskBoardReqDto taskBoardReqDto);


}
