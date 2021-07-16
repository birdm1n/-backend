package com.daema.core.scm.domain.appform;

import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.commgmt.domain.Store;
import com.daema.core.scm.domain.BasicInfo;
import com.daema.core.scm.domain.customer.Customer;
import com.daema.core.scm.domain.enums.ScmEnum;
import com.daema.core.scm.domain.joininfo.JoinInfo;
import com.daema.core.scm.domain.payment.Payment;
import com.daema.core.scm.domain.taskupdateboard.TaskUpdateBoard;
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

    @Column(columnDefinition = "varchar(255) comment '개통 업무 상태'")
    private ScmEnum.TaskState.LogisState logisState;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) comment '상담 업무 상태'")
    private ScmEnum.TaskState.ConsultState consultState;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) comment '물류 업무 상태'")
    private ScmEnum.TaskState.OpeningState openingState;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_store_id", columnDefinition = "BIGINT unsigned comment '영업 관리점 아이디'")
    private Store saleStore;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opening_store_id", columnDefinition = "BIGINT unsigned comment '개통 관리점 아이디'")
    private OpenStore openingStore;

    @Column(name = "parent_sale_store_id", columnDefinition = "BIGINT unsigned comment '상위 영업 관리점 아이디'")
    private Long parentSaleStoreId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "basic_info_id")
    private BasicInfo basicInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_update_board_id")
    private TaskUpdateBoard taskUpdateBoard;

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

       /* AppForm.update(appForm, appFormUpdateDto);*/

        BasicInfo.update(appForm.getBasicInfo(), appFormUpdateDto.getBasicInfoDto());

        Payment.update(appForm.getPayment(), appFormUpdateDto.getPaymentDto());

        JoinInfo.update(appForm.getJoinInfo(), appFormUpdateDto.getJoinInfoDto());

        Customer.update(appForm.getCustomer(), appFormUpdateDto.getCustomerDto());

    }
/*
    public static void update(AppForm appForm, AppFormUpdateDto appFormUpdateDto){
        appForm.set));
    }*/



    public static AppForm toEntity(AppFormUpdateDto appFormUpdateDto) {

        Customer customer = Customer.toEntity(appFormUpdateDto.getCustomerDto());
        BasicInfo basicInfo = BasicInfo.toEntity(appFormUpdateDto.getBasicInfoDto().getMembers());
        TaskUpdateBoard taskUpdateBoard = TaskUpdateBoard.toEntity();
        Payment payment = Payment.toEntity(appFormUpdateDto.getPaymentDto());
        JoinInfo joinInfo = JoinInfo.toEntity(appFormUpdateDto.getJoinInfoDto());

        return AppForm.builder()
                .basicInfo(basicInfo)
                .taskUpdateBoard(taskUpdateBoard)
                .customer(customer)
                .payment(payment)
                .joinInfo(joinInfo)
                .build();
    }

    public static AppFormRepDto responseFrom(AppForm appForm) {

        return AppFormRepDto.builder()
                .taskUpdateBoardDto(TaskUpdateBoard.From(appForm.getTaskUpdateBoard()))
                .customerDto(Customer.from(appForm.getCustomer()))
                .paymentDto(Payment.from(appForm.getPayment()))
                .joinInfoDto(JoinInfo.from(appForm.getJoinInfo()))
                .build();
    }


}