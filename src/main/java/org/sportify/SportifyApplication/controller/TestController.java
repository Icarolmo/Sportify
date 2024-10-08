package org.sportify.SportifyApplication.controller;

import org.sportify.SportifyApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

        @Autowired
        private UserRepository repository;

        @GetMapping("/ping")
        public ResponseEntity ping() {
            return ResponseEntity.ok().build();
        }
}
