package com.edu.smartstudentcard.controller;

import com.edu.smartstudentcard.model.Event;
import com.edu.smartstudentcard.repository.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private IEventRepository eventRepository;

    //Add event
    @PostMapping("")
    public Event addEvent(@RequestBody Event event){
        return eventRepository.save(event);
    }

    //Get event by Id
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id){
        return eventRepository.findById(id)
                .orElseThrow(()->new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    //Get all events
    @GetMapping("")
    public List<Event> getALlEvents(){
        return eventRepository.findAll();
    }

    //Update event by Id
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEventById(@PathVariable Long id, @RequestBody Event event){
        Optional<Event> EventData = eventRepository.findById(id);

        if(EventData.isPresent()){
            Event _event = EventData.get();
            _event.setId(event.getId());
            _event.setEventName(event.getEventName());
            _event.setDescription(event.getDescription());

            return new ResponseEntity<>(eventRepository.save(_event),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete event by ID
    @DeleteMapping("/{id}")
    public void deleteEventById(@PathVariable Long id){
        eventRepository.deleteById(id);
    }

}
