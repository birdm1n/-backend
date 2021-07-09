package com.daema.core.sms.domain;

import com.daema.core.sms.dto.CustomerDto;
import com.daema.core.sms.dto.request.AppFormInquiryDto;
import com.daema.core.sms.dto.request.AppFormReqDto;
import com.daema.core.sms.dto.response.AppFormResponseDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "task_update_board_id")
    private TaskUpdateBoard taskUpdateBoard;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "join_info_id")
    private JoinInfo joinInfo;


    public static AppForm toEntity(AppFormReqDto appFormReqDto){

        Customer customer = Customer.toEntity(appFormReqDto.getCustomerDto());
        BasicInfo basicInfo = BasicInfo.toEntity(appFormReqDto.getBasicInfoDto().getMembers());
        TaskUpdateBoard taskUpdateBoard = TaskUpdateBoard.toEntity();
        Payment payment = Payment.toEntity(appFormReqDto.getPaymentDto());
        JoinInfo joinInfo = JoinInfo.toEntity(appFormReqDto.getJoinInfoDto());

       return AppForm.builder()
                .basicInfo(basicInfo)
                .taskUpdateBoard(taskUpdateBoard)
                .customer(customer)
                .payment(payment)
                .joinInfo(joinInfo)
                .build();
    }

    public static AppFormResponseDto responseFrom(AppForm appForm){

        return AppFormResponseDto.builder()
                .taskUpdateBoardDto(TaskUpdateBoard.From(appForm.getTaskUpdateBoard()))
                .customerDto(Customer.from(appForm.getCustomer()))
                .paymentDto(Payment.from(appForm.getPayment()))
                .joinInfoDto(JoinInfo.from(appForm.getJoinInfo()))
                .build();
    }

/*
    @OneToMany(mappedBy = "appForm", cascade = CascadeType.ALL)
    private List<JoinAddition> joinAddition = new ArrayList<>();
*/


}
