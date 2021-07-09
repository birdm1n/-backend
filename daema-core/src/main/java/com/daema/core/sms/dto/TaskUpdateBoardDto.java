package com.daema.core.sms.dto;

import com.daema.core.sms.domain.TaskUpdateComment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskUpdateBoardDto {

    private Long taskUpdateBoardId;
    private List<Long> taskUpdateCommentIds;
}

