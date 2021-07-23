package com.daema.core.scm.domain.application;

import com.daema.core.base.domain.common.BaseEntity;
import com.daema.core.scm.domain.ApplicationBasic;
import com.daema.core.scm.domain.customer.ApplicationCustomer;
import com.daema.core.scm.domain.delivery.ApplicationDelivery;
import com.daema.core.scm.domain.join.ApplicationJoin;
import com.daema.core.scm.domain.payment.ApplicationPayment;
import com.daema.core.scm.domain.taskboard.ApplicationTaskBoard;
import com.daema.core.scm.dto.ApplicationDto;
import com.daema.core.scm.dto.request.ApplicationCreateDto;
import com.daema.core.scm.dto.request.ApplicationUpdateDto;
import com.daema.core.scm.dto.response.ApplicationReqDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "applId")
@ToString(exclude = {"saleStore", "openingStore", "applRegiUserId", "applicationCustomer", "applicationConsultList",
        "applicationPayment", "applicationJoin", "applicationDelivery", "applicationEtc", "memoList", "taskMemoList"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "application", comment = "신청서")
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appl_id", columnDefinition = "BIGINT UNSIGNED comment '신청서 아이디'")
    private Long applId;

    @Column(name = "delYN", columnDefinition = "char(1) comment '삭제여부'")
    private String delYn;


    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApplicationBasic basic;

    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApplicationTaskBoard board;

    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApplicationPayment payment;

    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApplicationCustomer customer;

    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApplicationJoin join;

    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApplicationDelivery delivery;

    /**
     * 신청서 수정
     *
     * @param application
     * @param applicationUpdateDto
     */
    public static void updateApplication(Application application, ApplicationUpdateDto applicationUpdateDto) {

        ApplicationBasic.update(application.getBasic(), applicationUpdateDto.getApplicationBasicDto());

        ApplicationPayment.update(application.getPayment(), applicationUpdateDto.getApplicationPaymentDto());

        ApplicationJoin.update(application.getJoin(), applicationUpdateDto.getJoinInfoDto());

        ApplicationCustomer.update(application.getCustomer(), applicationUpdateDto.getApplicationCustomerDto());

    }

    /**
     * 신청서 작성
     *
     * @param applicationCreateDto
     * @return
     */

    public static Application create(ApplicationCreateDto applicationCreateDto) {

        Application application = build(applicationCreateDto.getApplicationDto());
        application.customer = ApplicationCustomer.create(application, applicationCreateDto.getApplicationCustomerDto());
        application.basic = ApplicationBasic.create(application, applicationCreateDto.getApplicationBasicDto());
        application.board = ApplicationTaskBoard.create(application, applicationCreateDto.getApplicationTaskBoardDto());
        application.payment = ApplicationPayment.create(application, applicationCreateDto.getApplicationPaymentDto());
        application.join = ApplicationJoin.create(application, applicationCreateDto.getJoinInfoDto());
        application.delivery = ApplicationDelivery.create(application, applicationCreateDto.getApplicationDeliveryDto());

        return application;
        /* AppForm.builder()
                .basicInfo(basicInfo)
                .taskBoard(taskBoard)
                .customer(customer)
                .payment(payment)
                .joinInfo(joinInfo)
                .appFormDelivery(appFormDelivery)
                .build();*/
    }

    private static Application build(ApplicationDto basic) {
        return Application.builder()
                .delYn(basic.getDelYn())
                .build();
    }

    /**
     * 신청서 조회
     *
     * @param application
     * @return
     */
    public static ApplicationReqDto repApplication(Application application) {

        return ApplicationReqDto.builder().applicationBasicDto(ApplicationBasic.From(application.getBasic()))
                .applicationTaskBoardDto(ApplicationTaskBoard.From(application.getBoard()))
                .applicationCustomerDto(ApplicationCustomer.from(application.getCustomer()))
                .applicationPaymentDto(ApplicationPayment.from(application.getPayment()))
                .joinInfoDto(ApplicationJoin.from(application.getJoin()))
                .build();
    }


}