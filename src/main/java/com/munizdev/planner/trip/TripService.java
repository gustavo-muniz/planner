package com.munizdev.planner.trip;

import com.munizdev.planner.participant.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository repository;
    @Autowired
    private ParticipantService participantService;

    public Trip createTrip(TripRequestPayload data) {
        Trip trip = new Trip(data);
        this.repository.save(trip);

        this.participantService.registerParticipantsToTrip(data.emails_to_invite(), trip);

        return trip;
    }

    public Optional<Trip> findById(UUID id) {
        return repository.findById(id);
    }

    public Optional<Trip> updateTrip(UUID id, TripRequestPayload payload) {
        Optional<Trip> trip = repository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setStartsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setDestination(payload.destination());

            this.repository.save(rawTrip);
        }

        return trip;
    }

    public Optional<Trip> confirmTrip(UUID id) {
        Optional<Trip> trip = repository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);

            this.repository.save(rawTrip);
        }

        return trip;
    }

    public Optional<ParticipantCreateResponse> inviteParticipant(UUID id, ParticipanteRequestPayload payload) {
        Optional<Trip> trip = repository.findById(id);

        ParticipantCreateResponse participantCreateResponse = null;
        if (trip.isPresent()) {
            participantCreateResponse = participantService.registerParticipantToTrip(payload.email(), trip.get());
        }

        return Optional.ofNullable(participantCreateResponse);
    }

    public List<ParticipantData> getAllParticipants(UUID id) {
        List<ParticipantData> participants = participantService.getAllParticipantsFromTrip(id);

        return participants;
    }
}
