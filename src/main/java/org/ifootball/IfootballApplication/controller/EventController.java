package org.ifootball.IfootballApplication.controller;

import org.ifootball.IfootballApplication.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    @Autowired
    EventService service;

    @GetMapping("/get/all")
    public ResponseEntity getEventAll() {
        return ResponseEntity.ok().body(service.getEventAll());
    }

    @GetMapping("/get/by-type/{type}")
    public ResponseEntity getEventByType(@PathVariable String type){
        return ResponseEntity.ok().body(service.getEventByType(type));
    }

}
