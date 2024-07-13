package com.munizdev.planner.participant;

import com.munizdev.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository repository;

    public void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip) {
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        this.repository.saveAll(participants);
    }

    public ParticipantCreateResponse registerParticipantToTrip(String email, Trip trip) {
        Participant participant = new Participant(email, trip);

        this.repository.save(participant);

        return new ParticipantCreateResponse(participant.getId());
    }

    public Optional<Participant> confirmParticipant(UUID id, ParticipanteRequestPayload payload) {
        Optional<Participant> participant = repository.findById(id);

        if (participant.isPresent()) {
            Participant rawParticipant = participant.get();
            rawParticipant.setIsConfirmed(true);
            rawParticipant.setName(payload.name());

            this.repository.save(rawParticipant);
        }

        return participant;
    }
}
