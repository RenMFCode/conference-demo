package com.example.conferencedemo.controllers;

import com.example.conferencedemo.models.Session;
import com.example.conferencedemo.models.Speaker;
import com.example.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Optional<Speaker> get(@PathVariable Long id) {
        return speakerRepository.findById(id);
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED) // The status that we want
    public Speaker create(@RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        //TODO: Also need to check for children records before deleting.
        speakerRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT) //PUT replace all attributes, PATH allow update some fields. In this case all att needs to be in the request
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        // Because this is a PUT, we expect all attributes to be passed in. A PATCH would only need
        //TODO Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Optional<Speaker> existingSpeaker = speakerRepository.findById(id);
        BeanUtils.copyProperties(speaker, existingSpeaker.get(), "speaker_id"); //Take the existing session and copy the existent session there. (ignore the PK, the ID)
        return speakerRepository.saveAndFlush(speaker);
    }

}
