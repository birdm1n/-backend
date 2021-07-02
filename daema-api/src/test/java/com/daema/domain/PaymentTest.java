package com.daema.domain;

import com.daema.ApiApplication;
import com.daema.ApiApplicationTest;
import com.daema.DemoApplication;
import com.daema.core.commgmt.domain.Goods;
import com.daema.core.sms.domain.Card;
import com.daema.core.sms.domain.Payment;
import com.daema.core.sms.repository.PaymentRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.rmi.registry.Registry;
import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Replace.ANY - 가짜 디비로 테스트, Replace.NONE - 실제 DB로 테스트
@DataJpaTest  // Repository들을 다 IoC에 등록해 둔다.
@ContextConfiguration(classes = {ApiApplication.class, DemoApplication.class})

public class PaymentTest extends TestCase {
    public void contextLoads(){}

    private Payment payment;


    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testSetUP() throws Exception {

       /* em = emf.createEntityManager();*/

        payment = payment.builder()
                .paymentId(1L)
                .build();

        payment.setCard(new Card());

        Payment payment1 = paymentRepository.save(payment);

        System.out.println(payment1);



/*

        List<Payment> paymentList = paymentRepository.findAll();
        for(Payment payment : paymentList){
            System.out.println(payment);
        }
*/

        /*
        paymentRepository.save(payment);*/
    }

}
