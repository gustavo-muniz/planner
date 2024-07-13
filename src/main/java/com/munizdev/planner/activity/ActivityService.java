package com.munizdev.planner.activity;

import com.munizdev.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip) {
        Activity activity = new Activity(payload.title(), payload.occurs_at(), trip);
        this.repository.save(activity);

        return new ActivityResponse(activity.getId(), activity.getTitle(), activity.getOccursAt());
    }

    public List<ActivityResponse> getAllActivitiesFromTrip(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(a -> new ActivityResponse(a.getId(), a.getTitle(), a.getOccursAt())).toList();
    }
}
