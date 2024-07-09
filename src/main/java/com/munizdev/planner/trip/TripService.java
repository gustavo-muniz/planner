package com.munizdev.planner.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository repository;

    public Trip createTrip(TripRequestPayload data) {
        Trip trip = new Trip(data);
        this.repository.save(trip);

        return trip;
    }
}
