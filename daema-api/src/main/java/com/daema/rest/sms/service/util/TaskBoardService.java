package com.daema.rest.sms.service.util;

import com.daema.core.scm.dto.request.MemoReqDto;
import com.daema.rest.common.enums.ResponseCodeEnum;

public interface TaskBoardService {


    ResponseCodeEnum insertMemos(MemoReqDto memoReqDto);
}
