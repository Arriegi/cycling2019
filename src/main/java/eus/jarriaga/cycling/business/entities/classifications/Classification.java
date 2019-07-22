package eus.jarriaga.cycling.business.entities.classifications;

import eus.jarriaga.cycling.business.entities.Participant;

import java.util.ArrayList;
import java.util.List;

public abstract class Classification {

    protected List<Participant> participantList = new ArrayList<>();

    public void addParticipant(Participant participant) {
        this.participantList.add(participant);
    }

    public void addParticipants(List<Participant> participants) {
        this.participantList = participants;
    }

    abstract void calculatePoints();
}
