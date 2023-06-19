package com.example.conferencedemo.controllers;

import com.example.conferencedemo.models.Session;
import com.example.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Optional<Session> get(@PathVariable Long id) {
        return sessionRepository.findById(id);
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED) // The status that we want
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        //TODO: Also need to check for children records before deleting.
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT) //PUT replaces all attributes, PATH allow update some fields. In this case all att needs to be in the request
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        // Because this is a PUT, we expect all attributes to be passed in. A PATCH would only need
        //TODO Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Session existingSession = sessionRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSession, "session_id"); //Take the existing session and copy the existent session there. (ignore the PK, the ID)
        return sessionRepository.saveAndFlush(existingSession);
    }

}
