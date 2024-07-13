package com.munizdev.planner.participant;

import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService service;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipanteRequestPayload payload) {
        Optional<Participant> participant = service.confirmParticipant(id, payload);

        return participant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
