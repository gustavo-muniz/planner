package com.munizdev.planner.trip;

import com.munizdev.planner.activity.Activity;
import com.munizdev.planner.activity.ActivityRequestPayload;
import com.munizdev.planner.activity.ActivityResponse;
import com.munizdev.planner.participant.Participant;
import com.munizdev.planner.participant.ParticipantCreateResponse;
import com.munizdev.planner.participant.ParticipantData;
import com.munizdev.planner.participant.ParticipanteRequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = service.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {
        Optional<Trip> trip = service.updateTrip(id, payload);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = service.confirmTrip(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipanteRequestPayload payload) {
        Optional<ParticipantCreateResponse> participant = service.inviteParticipant(id, payload);

        return participant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id) {
        List<ParticipantData> participants = service.getAllParticipants(id);

        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        Optional<ActivityResponse> activity = service.registerActivity(id, payload);

        return activity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
