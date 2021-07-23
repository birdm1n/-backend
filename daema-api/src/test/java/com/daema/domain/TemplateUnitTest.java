package com.daema.domain;

import com.daema.ApiApplication;
import com.daema.DemoApplication;
import com.daema.core.scm.domain.payment.ApplicationPayment;
import com.daema.core.scm.domain.payment.CardAttribute;
import com.daema.core.scm.repository.util.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

// 단위 테스트 (DB 관련된 Bean이 IOC에 등록되면 됨)

@Transactional  // 메서드 실행시 마다 롤백
@AutoConfigureTestDatabase(replace = Replace.NONE) // Replace.ANY - 가짜 디비로 테스트, Replace.NONE - 실제 DB로 테스트
@DataJpaTest  // Repository들을 다 IoC에 등록해 둔다.
@ContextConfiguration(classes = {ApiApplication.class, DemoApplication.class})
public class TemplateUnitTest {
    Logger log = (Logger) LoggerFactory.getLogger(TemplateUnitTest.class);

    private ApplicationPayment applicationPayment;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void setUP() throws Exception {


        applicationPayment = applicationPayment.builder()
                .paymentId(1L)
                .build();

        applicationPayment.setCardAttribute(new CardAttribute());

        paymentRepository.save(applicationPayment);
    }

   /* @Autowired
    private GoodsOptionRepository goodsOptionRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void 테스트용(){
        Goods goods = Goods.builder()
                .goodsId(1L)
                .build();

        // error code exclude
        // GoodsOption goodsOption = goodsOptionRepository.findTopByGoodsAndCapacityAndColorNameAndDelYn(goods, "250G", "레드", "N");
        GoodsOption goodsOption = null;
                log.info("데이터 확인용 = {}", goodsOption);

    }*/

}
