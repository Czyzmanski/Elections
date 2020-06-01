package elections.candidate;

import elections.district.District;
import elections.party.Party;

import java.util.Arrays;

public class Candidate {

    private final String firstName;
    private final String lastName;
    private final int[] qualities;
    private final District district;
    private final Party party;
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

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Party getParty() {
        return party;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
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

    @Override
    public String toString() {
        return String.format("%s, party: %s, ticket number: %d, number of votes: %d",
                             getName(), party.toString(), ticketNumber, votesCount);
    }

}
