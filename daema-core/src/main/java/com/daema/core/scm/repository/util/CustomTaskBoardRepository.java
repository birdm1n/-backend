package com.daema.core.scm.repository.util;

import com.daema.core.scm.domain.taskboard.ApplicationTaskBoard;
import com.daema.core.scm.dto.request.TaskBoardReqDto;

public interface CustomTaskBoardRepository {


    ApplicationTaskBoard getTaskBoard(TaskBoardReqDto taskBoardReqDto);


}
