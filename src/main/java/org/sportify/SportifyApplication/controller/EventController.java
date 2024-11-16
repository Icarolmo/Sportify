package org.sportify.SportifyApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.sportify.SportifyApplication.domain.Event;
import org.sportify.SportifyApplication.domain.User;
import org.sportify.SportifyApplication.dto.EventDTO;
import org.sportify.SportifyApplication.exception.RequestBodyWithIncorrectDataException;
import org.sportify.SportifyApplication.service.EventService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping(value =  "/event")
@Tag(name = "open-sportfy")
public class EventController {

    @Autowired
    EventService service;

    @Transactional
    @PostMapping(value = "/subscribe/{activityTitle}")
    public ResponseEntity subscribeEvent(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("Authorization") String authorization,
                                      @PathVariable @Validated String activityTitle) {
        log.info("REQUEST => SUBSCRIBE EVENT: user-agent: [{}]; authorization: [{}];", userAgent, authorization);

        Event.ValidateActivityTitle(activityTitle);
        log.info("ACTIVITY TITLE: {}", activityTitle);

        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        service.subscribeEvent(activityTitle, user);
        log.info("user [{}] subscribe in event [{}] with succesfully", user.getEmail(), activityTitle);

        return ResponseEntity.ok().build();
    }


    @Transactional
    @PostMapping(value = "/create")
    public ResponseEntity createEvent(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("Authorization") String authorization,
                                      @ParameterObject @RequestBody EventDTO eventDTO) {
        log.info("REQUEST => CREATE EVENT: user-agent: [{}]; authorization: [{}];", userAgent, authorization);

        Event.IsEmpty(eventDTO);
        log.info("EVENT: {}", Event.PrintEventDTOData(eventDTO));

        Event.ValidateEvent(eventDTO);
        log.info("validate event data with succesfully");

        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        service.createEvent(eventDTO, user);
        log.info("create event [{}] with succesfully", eventDTO.activity_title());

        return ResponseEntity.ok().body(eventDTO);
    }

    @Transactional
    @PostMapping( value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateEvent(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("Authorization") String authorization,
                                      @ParameterObject @RequestBody @Validated EventDTO eventDTO) {
        log.info("REQUEST => UPDATE EVENT: user-agent: [{}]; authorization: [{}];", userAgent, authorization);

        Event.IsEmpty(eventDTO);
        log.info("EVENT: {}", Event.PrintEventDTOData(eventDTO));

        Event.ValidateEvent(eventDTO);
        log.info("validate event data with succesfully");

        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        service.updateEvent(eventDTO, user);
        log.info("updated event [{}] with succesfully", eventDTO.activity_title());

        return ResponseEntity.ok().body(eventDTO);
    }

    @Transactional
    @DeleteMapping( value = "/delete/{activity_title}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEvent(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("Authorization") String authorization,
                                      @PathVariable @Validated String activityTitle) {
        log.info("REQUEST => DELETE EVENT: activity_title [{}]; user-agent: [{}]; authorization: [{}];", activityTitle, userAgent, authorization);

        Event.ValidateActivityTitle(activityTitle);
        log.info("activity_title validate with successfully");

        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        service.deleteEvent(activityTitle, user);
        log.info("event [{}] deleted with successfully", activityTitle);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Recupera e retorna todos os eventos ativos.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lista de todos os eventos criados e ativos no momento podendo ser vazia caso não exista eventos ativos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class)))
    })
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEventAll(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("Authorization") String authorization) {
        log.info("REQUEST => GET ALL EVENTS: user-agent: [{}]; authorization: [{}];", userAgent, authorization);

        var allEvents = service.getAllActiveEvents();

        return ResponseEntity.ok().body(allEvents);
    }

    @Operation(summary = "Recupera e retorna todos os eventos criados pelo usuário.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lista de todos os eventos criados e ativos pelo usuário.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class)))
    })
    @GetMapping(value = "/get/by-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity geEventByUser(@RequestHeader("User-Agent") String userAgent,
                                        @RequestHeader("Authorization") String authorization) {
        log.info("REQUEST => GET ALL EVENTS BY USER: user-agent: [{}]; authorization: [{}];", userAgent, authorization);

        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var allEventsByUser = service.getEventByUser(user);

        return ResponseEntity.ok().body(allEventsByUser);
    }

    @Operation(summary = "Recupera uma lista de eventos filtrados por tipo.", method = "GET")
    @GetMapping(value = "/get/by-type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEventByType(@RequestHeader("User-Agent") String userAgent,
                                         @RequestHeader("Authorization") String authorization,
                                         @PathVariable @Validated String type) {
        log.info("REQUEST => GET EVENTS BY TYPE: type: [{}]; user-agent: [{}]; authorization: [{}];", type, userAgent, authorization);

        if(type.isBlank()){
            throw new RequestBodyWithIncorrectDataException("type: tipo do evento não pode ser nulo ou vazio");
        }
        Collection<EventDTO> allEventsForThisType = service.getEventByType(type);

        return ResponseEntity.ok().body(allEventsForThisType);
    }



}
