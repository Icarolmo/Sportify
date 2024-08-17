package org.ifootball.IfootballApplication.controller;

import org.ifootball.IfootballApplication.dto.PingDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

        @GetMapping("/ping")
        public ResponseEntity ping() {
            return ResponseEntity.ok().body(new PingDTO("pong"));

        }
}
