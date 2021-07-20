package com.daema.core.scm.domain.appform;

import com.daema.core.scm.domain.BasicInfo;
import com.daema.core.scm.domain.customer.Customer;
import com.daema.core.scm.domain.joininfo.JoinInfo;
import com.daema.core.scm.domain.payment.Payment;
import com.daema.core.scm.domain.taskboard.TaskBoard;
import com.daema.core.scm.dto.request.AppFormCreateDto;
import com.daema.core.scm.dto.request.AppFormUpdateDto;
import com.daema.core.scm.dto.response.AppFormRepDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "appFormId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "app_form", comment = "신청서 폼")
public class AppForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_form_id", columnDefinition = "BIGINT UNSIGNED comment '신청서 폼 아이디'")
    private Long appFormId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "basic_info_id")
    private BasicInfo basicInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_board_id")
    private TaskBoard taskBoard;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "join_info_id")
    private JoinInfo joinInfo;

    /**
     * 신청서 수정
     *
     * @param appForm
     * @param appFormUpdateDto
     */
    public static void updateAppForm(AppForm appForm, AppFormUpdateDto appFormUpdateDto) {

        TaskBoard.update(appForm.getTaskBoard(), appFormUpdateDto.getTaskBoardDto());

        BasicInfo.update(appForm.getBasicInfo(), appFormUpdateDto.getBasicInfoDto());

        Payment.update(appForm.getPayment(), appFormUpdateDto.getPaymentDto());

        JoinInfo.update(appForm.getJoinInfo(), appFormUpdateDto.getJoinInfoDto());

        Customer.update(appForm.getCustomer(), appFormUpdateDto.getCustomerDto());

    }

    /**
     * 신청서 작성
     * @param appFormCreateDto
     * @return
     */

    public static AppForm create(AppFormCreateDto appFormCreateDto) {

        Customer customer = Customer.create(appFormCreateDto.getCustomerDto());
        BasicInfo basicInfo = BasicInfo.create(appFormCreateDto.getBasicInfoDto());
        TaskBoard taskBoard = TaskBoard.create(appFormCreateDto.getTaskBoardDto());
        Payment payment = Payment.create(appFormCreateDto.getPaymentDto());
        JoinInfo joinInfo = JoinInfo.create(appFormCreateDto.getJoinInfoDto());

        return AppForm.builder()
                .basicInfo(basicInfo)
                .taskBoard(taskBoard)
                .customer(customer)
                .payment(payment)
                .joinInfo(joinInfo)
                .build();
    }

    /**
     * 신청서 조회
     * @param appForm
     * @return
     */
    public static AppFormRepDto repAppForm(AppForm appForm) {

        return AppFormRepDto.builder()
                .basicInfoDto(BasicInfo.From(appForm.getBasicInfo()))
                .taskBoardDto(TaskBoard.From(appForm.getTaskBoard()))
                .customerDto(Customer.from(appForm.getCustomer()))
                .paymentDto(Payment.from(appForm.getPayment()))
                .joinInfoDto(JoinInfo.from(appForm.getJoinInfo()))
                .build();
    }


}