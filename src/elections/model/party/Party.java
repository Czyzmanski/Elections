package elections.model.party;

import elections.model.district.District;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public abstract class Party implements Comparable<Party> {

    public final static String PROFILIGATE_PARTY = "R";
    public final static String ECONOMICAL_PARTY = "S";
    public final static String MIN_ASSESS_SUM_PARTY = "W";
    public final static String MAX_ASSESS_SUM_PARTY = "Z";

    protected String name;
    protected int budget;
    protected int mandatesCount;

    public Party(String name, int budget) {
        this.name = name;
        this.budget = budget;
        this.mandatesCount = 0;
    }

    public String getName() {
        return name;
    }

    public void addMandates(int mandates) {
        mandatesCount += mandates;
    }

    public abstract void conductCampaign(List<Action> actions, List<District> districts);

    @Override
    public int compareTo(Party other) {
        return Comparator.nullsLast(Comparator.comparing(Party::getName))
                         .compare(this, other);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null || getClass() != other.getClass()) {
            return false;
        } else {
            Party party = (Party) other;
            return name.equals(party.name);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s, number of all mandates: %d", name, mandatesCount);
    }

    public static Party newInstance(String type, String name, int budget) {
        switch (type) {
            case PROFILIGATE_PARTY:
                return new ProfiligateParty(name, budget);
            case ECONOMICAL_PARTY:
                return new EconomicalParty(name, budget);
            case MIN_ASSESS_SUM_PARTY:
                return new MinAssessSumParty(name, budget);
            case MAX_ASSESS_SUM_PARTY:
                return new MaxAssessSumParty(name, budget);
            default:
                throw new UnsupportedOperationException("Unsupported party type");
        }
    }

}