package com.daema.rest.base.web;

import com.daema.ApiApplication;
import com.daema.core.wms.repository.InStockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = ApiApplication.class)
public class TestControllerTest {

    private Logger logger = LoggerFactory.getLogger(TestControllerTest.class);

    @Autowired
    private InStockRepository inStockRepository;


}