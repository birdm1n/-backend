package com.daema.web;

import com.daema.domain.Board;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/list")
    public Board list() {
        return new Board() {
            {
                this.setContent("luther");
            }
        };
    }

    @PostMapping("/post")
    public Board create() {
        return new Board() {
            {
                this.setBoardNo(1L);
                this.setTitle("제목");
                this.setContent("내용");
                this.setWriter("luther");
            }
        };
    }

    @GetMapping("/get")
    public Board read() {

        return new Board() {
            {
                this.setContent("luther");
            }
        };
    }
}
