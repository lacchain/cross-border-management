package us.lacchain.crossborder.management.controller;

import us.lacchain.crossborder.management.service.IEventService;
import us.lacchain.crossborder.management.service.EventService;
import us.lacchain.crossborder.management.model.UserView;
import us.lacchain.crossborder.management.clients.request.AddUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import us.lacchain.crossborder.management.clients.request.EventRequest;
import us.lacchain.crossborder.management.util.Token;
import us.lacchain.crossborder.management.exception.UserExistsException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.http.HttpStatus;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/api")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private IEventService eventService;

    @Autowired
    private Token token;
    
    @PostMapping("/event")
    public ResponseEntity processEvent(@RequestBody EventRequest requestBody){
        try {
            logger.info("Process Event");
            eventService.processEvent(requestBody);
            return ResponseEntity.ok().build();
        }
        catch  (Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
}