package com.daema.rest.sms.service.impl;

import com.daema.core.scm.domain.application.Application;
import com.daema.core.scm.domain.taskboard.ApplicationTaskBoard;
import com.daema.core.scm.dto.request.MemoReqDto;
import com.daema.core.scm.repository.util.ApplicationRepoistory;
import com.daema.core.scm.repository.util.TaskBoardRepository;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.sms.service.util.TaskBoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TaskBoardServiceImpl implements TaskBoardService {

    private final TaskBoardRepository taskBoardRepository;
    private final ApplicationRepoistory applicationRepoistory;

    @Transactional
    @Override
    public ResponseCodeEnum insertMemos(MemoReqDto memoReqDto) {

        Application application = applicationRepoistory.findById(memoReqDto.getApplId()).orElseGet(null);
        ApplicationTaskBoard applicationTaskBoard = taskBoardRepository.findById(application.getBoard().getApplId()).orElseGet(null);

        ApplicationTaskBoard.updateTaskMemos(applicationTaskBoard, memoReqDto);

    return ResponseCodeEnum.OK;
    }
}
