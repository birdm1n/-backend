package com.daema.core.sms.domain;

import com.daema.core.sms.domain.enums.SmsEnum;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "addtionId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "addition", comment = "부가서비스")
public class Addition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addition_id", columnDefinition = "BIGINT UNSIGNED comment '부가서비스 아이디'")
    private Long additionId;

    @Column(name = "addition_category", columnDefinition = "varchar(255) comment '부가서비스 카테고리'")
    private SmsEnum.AdditionCategory additionCategory;

    @Column(name = "product_name", columnDefinition = "varchar(255) comment '상품명'")
    private String productName;

    @Column(name = "charge", columnDefinition = "INT comment '요금'")
    private int charge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_info_id", columnDefinition = "BIGINT UNSIGNED comment '가입 정보'")
    private JoinInfo joinInfo;
}
