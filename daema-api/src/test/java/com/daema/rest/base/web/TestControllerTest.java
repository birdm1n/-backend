package com.daema.rest.base.web;

import com.daema.ApiApplication;
import com.daema.sample.domain.Board;
import com.daema.sample.repository.BoardRepository;
import com.daema.wms.domain.dto.request.InStockRequestDto;
import com.daema.wms.repository.InStockRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = ApiApplication.class)
public class TestControllerTest {

    private Logger logger = LoggerFactory.getLogger(TestControllerTest.class);

    @Autowired
    private InStockRepository inStockRepository;


}