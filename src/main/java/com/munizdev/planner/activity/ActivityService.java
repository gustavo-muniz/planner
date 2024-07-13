package com.munizdev.planner.activity;

import com.munizdev.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip) {
        Activity activity = new Activity(payload.title(), payload.occurs_at(), trip);
        this.repository.save(activity);

        return new ActivityResponse(activity.getId(), activity.getTitle(), activity.getOccursAt());
    }
}
