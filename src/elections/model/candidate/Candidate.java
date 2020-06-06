package elections.model.candidate;

import elections.simulation.Reusable;
import elections.model.district.District;
import elections.model.party.Party;

import java.util.Arrays;

public class Candidate implements Reusable {

    protected final String firstName;
    protected final String lastName;
    protected final int[] qualities;
    protected final District district;
    protected final Party party;
    protected int ticketNumber;
    protected int votesCount;

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

    @Override
    public void init() {
        votesCount = 0;
    }

}
