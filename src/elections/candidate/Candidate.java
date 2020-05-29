package elections.candidate;

import elections.district.District;
import elections.party.Party;

import java.util.Arrays;

public class Candidate {

    private String firstName;
    private String lastName;
    private int[] qualities;
    private District district;
    private Party party;
    private int numberOfTicket;
    private int numberOfVotes;

    public Candidate(String firstName, String lastName, int[] qualities,
                     District district, Party party, int numberOfTicket) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualities = Arrays.copyOf(qualities, qualities.length);
        this.district = district;
        this.party = party;
        this.numberOfTicket = numberOfTicket;
        this.numberOfVotes = 0;
    }

    public boolean belongs(Party party) {
        return this.party.equals(party);
    }

    public void voteFor() {
        numberOfVotes++;
        district.addVote(party);
    }

    public int getQuality(int numberOfQuality) {
        return qualities[numberOfQuality];
    }

}
