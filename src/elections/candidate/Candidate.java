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
    private int ticketNumber;
    private int votesCount;

    public Candidate(String firstName, String lastName, int[] qualities,
                     District district, Party party, int ticketNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualities = Arrays.copyOf(qualities, qualities.length);
        this.district = district;
        this.party = party;
        this.ticketNumber = ticketNumber;
        this.votesCount = 0;
    }

    public boolean belongs(Party party) {
        return this.party.equals(party);
    }

    public void voteFor() {
        votesCount++;
        district.addVote(party);
    }

    public int quality(int qualityNumber) {
        return qualities[qualityNumber];
    }

    public int[] qualities() {
        return Arrays.copyOf(qualities, qualities.length);
    }

}
