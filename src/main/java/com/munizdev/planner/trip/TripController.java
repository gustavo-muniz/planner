package com.munizdev.planner.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService service;

    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody TripRequestPayload payload) {
        Trip obj = service.createTrip(payload);

        return ResponseEntity.ok().body(obj);
    }
}
