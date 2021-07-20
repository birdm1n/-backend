package com.daema.rest.sms.service.impl;

import com.daema.core.scm.domain.appform.AppForm;
import com.daema.core.scm.domain.taskboard.TaskBoard;
import com.daema.core.scm.dto.request.MemoReqDto;
import com.daema.core.scm.repository.impl.TaskBoardRepositoryImpl;
import com.daema.core.scm.repository.util.AppFormRepository;
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
    private final AppFormRepository appFormRepository;

    @Transactional
    @Override
    public ResponseCodeEnum insertMemos(MemoReqDto memoReqDto) {

        AppForm appForm = appFormRepository.findById(memoReqDto.getAppFormId()).orElseGet(null);
        TaskBoard taskBoard = taskBoardRepository.findById(appForm.getTaskBoard().getTaskBoardId()).orElseGet(null);

        TaskBoard.update(taskBoard, memoReqDto);

    return ResponseCodeEnum.OK;
    }
}
