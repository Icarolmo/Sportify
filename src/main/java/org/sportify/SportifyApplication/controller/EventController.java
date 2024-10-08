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
import org.sportify.SportifyApplication.dto.ActivityTitle;
import org.sportify.SportifyApplication.dto.EventDTO;
import org.sportify.SportifyApplication.exception.RequestBodyWithIncorrectDataException;
import org.sportify.SportifyApplication.service.EventService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping(value =  "/event", produces = {"application/json"})
@Tag(name = "open-sportfy")
public class EventController {

    @Autowired
    EventService service;

    @Transactional
    @Operation(summary = "Realiza criação de eventos.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "criação de evento feita com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "400", description = "dados de evento enviado para criação incorretos ou inválidos.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "evento já criado ou evento com mesmo nome já existente.",
                    content = @Content)
    })
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createEvent(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("Authorization") String authorization,
                                      @ParameterObject @RequestBody EventDTO eventDTO) {
        log.info("REQUEST => CREATE EVENT: user-agent: [{}]; authorization: [{}];", userAgent, authorization);

        Event.IsEmpty(eventDTO);
        log.info("EVENT: {}", Event.PrintEventDTOData(eventDTO));

        Event.ValidateEvent(eventDTO);
        log.info("validate event data with succesfully");

        service.createEvent(eventDTO);
        log.info("create event [{}] with succesfully", eventDTO.activity_title());

        return ResponseEntity.ok().body(eventDTO);
    }

    @Transactional
    @Operation(summary = "Realiza a atualização de um evento.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "atualização do evento feita com sucesso.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "400", description = "requisição mal feita ou dado faltante.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "erro do servidor na atualização do evento.",
                    content = @Content)
    })
    @PostMapping( value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateEvent(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("Authorization") String authorization,
                                      @ParameterObject @RequestBody @Validated EventDTO eventDTO) {
        log.info("REQUEST => UPDATE EVENT: user-agent: [{}]; authorization: [{}];", userAgent, authorization);

        Event.IsEmpty(eventDTO);
        log.info("EVENT: {}", Event.PrintEventDTOData(eventDTO));

        Event.ValidateEvent(eventDTO);
        log.info("validate event data with succesfully");

        service.updateEvent(eventDTO);
        log.info("updated event [{}] with succesfully", eventDTO.activity_title());

        return ResponseEntity.ok().body(eventDTO);
    }

    @Transactional
    @Operation(summary = "Realiza a exclusão de um evento.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "exclusão do evento feita com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityTitle.class))),
            @ApiResponse(responseCode = "400", description = "requisição mal feita ou dado faltante.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "erro do servidor na exclusão do evento.",
                    content = @Content)
    })
    @DeleteMapping( value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEvent(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("Authorization") String authorization,
                                      @ParameterObject @RequestBody ActivityTitle activityTitle) {
        log.info("REQUEST => DELETE EVENT: activity_title [{}]; user-agent: [{}]; authorization: [{}];", activityTitle.activity_title(), userAgent, authorization);

        Event.ValidateActivityTitle(activityTitle.activity_title());
        log.info("activity_title validate with successfully");

        service.deleteEvent(activityTitle.activity_title());
        log.info("event [{}] deleted with successfully", activityTitle);

        return ResponseEntity.ok().body("");
    }

    @Operation(summary = "Recupera e retorna todos os eventos criados.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lista de todos os eventos criados e ativos no momento podendo ser vazia caso não exista eventos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class)))
    })
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEventAll(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("Authorization") String authorization) {
        log.info("REQUEST => GET ALL EVENTS: user-agent: [{}]; authorization: [{}];", userAgent, authorization);

        Collection<EventDTO> allEvents = service.getEventAll();

        return ResponseEntity.ok().body(allEvents);
    }

    @Operation(summary = "Recupera uma lista de eventos filtrados por tipo.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "lista de todos os eventos com o tipo passado podendo ser vazia caso não exista eventos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class)))
    })
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
