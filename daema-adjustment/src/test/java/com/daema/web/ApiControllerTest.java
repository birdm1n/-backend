package com.daema.web;

import com.daema.config.RestDocConfiguration;
import com.daema.domain.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;


@WebFluxTest(ApiController.class)
@Import(RestDocConfiguration.class)
@AutoConfigureRestDocs
public class ApiControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("API Test")
    void test() {
        final Board responseBoard = webTestClient.post()
                .uri("/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Board.class)
                .consumeWith(document("web-test-person-post", // 디렉토리명(generated-snippets/web-test-person-post)
                        requestHeaders( // RequestHeader 작성
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON_VALUE),
                                headerWithName(HttpHeaders.ACCEPT).description(MediaType.APPLICATION_JSON_VALUE)
                        ),
                        responseFields( // ResponseField 작성
                                fieldWithPath("boardNo").type(JsonFieldType.NUMBER).description("게시번호"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("writer").type(JsonFieldType.STRING).description("글쓴이"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")

                        )))
                .returnResult().getResponseBody();

        assertThat(responseBoard)
                .extracting("writer")
                .isEqualTo("luther");
    }
}
