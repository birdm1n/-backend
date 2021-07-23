package com.daema.rest.sms.web;

import com.daema.core.scm.dto.request.MemoReqDto;
import com.daema.rest.common.enums.ResponseCodeEnum;
import com.daema.rest.common.handler.ResponseHandler;
import com.daema.rest.common.io.response.CommonResponse;
import com.daema.rest.sms.service.util.TaskBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "판매관리/신청서작성 메모 API", tags = "판매관리/신청서 메모 API")
@RestController
@RequestMapping(value = {"/v1/api/scm/Memo", "/api/scm/Memo" })
public class TaskBoardController {
    private final TaskBoardService taskBoardService;
    private final ResponseHandler responseHandler;

    public TaskBoardController(TaskBoardService taskBoardService, ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        this.taskBoardService = taskBoardService;

    }

    @ApiOperation(value = "메모 작성", notes = "메모 작성 완료 처리 합니다.")
    @PostMapping("/insertTaskMemo")
    public ResponseEntity<CommonResponse<Void>> insertTaskMemo(@ApiParam(value = "메모 작성 처리", required = true) @RequestBody MemoReqDto memoReqDto) {

        ResponseCodeEnum responseCodeEnum = taskBoardService.insertMemos(memoReqDto);

        if (ResponseCodeEnum.OK != responseCodeEnum) {
            return responseHandler.fail(responseCodeEnum.getResultCode(), responseCodeEnum.getResultMsg());
        }
        return responseHandler.ok();
    }


}
