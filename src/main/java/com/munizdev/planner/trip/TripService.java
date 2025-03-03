package com.munizdev.planner.trip;

import com.munizdev.planner.activity.ActivityRequestPayload;
import com.munizdev.planner.activity.ActivityResponse;
import com.munizdev.planner.activity.ActivityService;
import com.munizdev.planner.link.LinkRequestPayload;
import com.munizdev.planner.link.LinkResponse;
import com.munizdev.planner.link.LinkService;
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
    @Autowired
    private ActivityService activityService;
    @Autowired
    private LinkService linkService;

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

    public Optional<ActivityResponse> registerActivity(UUID id, ActivityRequestPayload payload) {
        Optional<Trip> trip = repository.findById(id);

        ActivityResponse activity = null;
        if (trip.isPresent()) {
            activity = activityService.registerActivity(payload, trip.get());
        }

        return Optional.ofNullable(activity);
    }

    public List<ActivityResponse> getAllActivities(UUID id) {
        List<ActivityResponse> activities = activityService.getAllActivitiesFromTrip(id);

        return activities;
    }

    public Optional<LinkResponse> registerLink(UUID id, LinkRequestPayload payload) {
        Optional<Trip> trip = repository.findById(id);

        LinkResponse link = null;
        if (trip.isPresent()) {
            link = linkService.registerLink(payload, trip.get());
        }

        return Optional.ofNullable(link);
    }

    public List<LinkResponse> getAllLinks(UUID id) {
        List<LinkResponse> links = linkService.getAllLinksFromTrip(id);

        return links;
    }
}
