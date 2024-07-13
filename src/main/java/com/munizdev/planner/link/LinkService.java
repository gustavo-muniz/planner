package com.munizdev.planner.link;

import com.munizdev.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository repository;

    public LinkResponse registerLink(LinkRequestPayload payload, Trip trip) {
        Link link = new Link(payload.title(), payload.url(), trip);
        this.repository.save(link);

        return new LinkResponse(link.getId(), link.getTitle(), link.getUrl());
    }

    public List<LinkResponse> getAllLinksFromTrip(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(l -> new LinkResponse(l.getId(), l.getTitle(), l.getUrl())).toList();
    }
}
