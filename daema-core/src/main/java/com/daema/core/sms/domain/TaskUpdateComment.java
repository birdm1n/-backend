package com.daema.core.sms.domain;

import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "paymentId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "task_update", comment = "업무수정")
public class TaskUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_update_id")
    private Long taskUpdateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_state", columnDefinition = "varchar(255) comment '업무 상태'")
    SmsEnum.TaskState taskState;

    @Column(name = "reason", columnDefinition = "varchar(255) comment '사유'")
    private String reason;



}
