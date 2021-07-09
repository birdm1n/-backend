package com.daema.core.sms.dto;

import com.daema.core.sms.domain.TaskUpdateBoard;
import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskUpdateCommentDto
{

    private Long taskUpdateId;
    private SmsEnum.TaskState taskState;
    private String reason;
    private Long taskUpdateBoardId;

}
