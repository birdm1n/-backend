package com.daema.domain;

import com.daema.ApiApplication;
import com.daema.DemoApplication;
import com.daema.core.scm.domain.payment.ApplicationPayment;
import com.daema.core.scm.domain.payment.CardAttribute;
import com.daema.core.scm.repository.util.PaymentRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Replace.ANY - 가짜 디비로 테스트, Replace.NONE - 실제 DB로 테스트
@DataJpaTest  // Repository들을 다 IoC에 등록해 둔다.
@ContextConfiguration(classes = {ApiApplication.class, DemoApplication.class})

public class ApplicationPaymentTest extends TestCase {
    public void contextLoads(){}

    private ApplicationPayment applicationPayment;


    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testSetUP() throws Exception {

       /* em = emf.createEntityManager();*/

        applicationPayment = applicationPayment.builder()
                .paymentId(1L)
                .build();

        applicationPayment.setCardAttribute(new CardAttribute());

        ApplicationPayment applicationPayment1 = paymentRepository.save(applicationPayment);

        System.out.println(applicationPayment1);



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
