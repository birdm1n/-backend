package com.daema.rest.base.web;

import com.daema.ApiApplication;
import com.daema.sample.domain.Board;
import com.daema.sample.repository.BoardRepository;
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
public class BoardControllerTest {

    private Logger logger = LoggerFactory.getLogger(BoardControllerTest.class);

    @Autowired
    private BoardRepository boardRepository;

    String baseUrl = "http://localhost:8080/";

    public URI setBaseUri(String link) throws URISyntaxException {
        return new URI(baseUrl + link);
    }

    @Test
    void REST테스트_게시판_조회() throws Exception{
        // 테스트용
        this.REST테스트_게시판_신규등록();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> result = restTemplate.getForEntity(setBaseUri("/v1/api/sample"), List.class);

        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void REST테스트_게시판_신규등록() throws Exception{

        RestTemplate restTemplate = new RestTemplate();

        Board board = new Board();
        board.setBoardNo(boardRepository.count() + 1L);
        board.setTitle("제목");
        board.setWriter("작성자");
        board.setContent(LocalDateTime.now().toString());

        restTemplate.postForEntity(setBaseUri("/v1/api/sample"), board, null);
    }

    @Test
    void BOARD_신규입력() throws Exception{
        Board board = new Board();
        board.setTitle("제목");
        board.setWriter("작성자");
        board.setContent("내용내용내용내용내용내용");

        boardRepository.save(board);

        Optional<Board> new_article = boardRepository.findById(Long.parseLong((String.valueOf(boardRepository.findAll().size()))));

        assertEquals(board.getTitle(), (new_article.get()).getTitle());
    }
}