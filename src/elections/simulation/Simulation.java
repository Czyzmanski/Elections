package elections.simulation;

import elections.model.district.District;
import elections.model.district.PairToMerge;
import elections.model.seats.SeatsAllocationMethod;
import elections.model.party.Action;
import elections.model.party.Party;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Simulation implements Reusable {

    private final List<Party> parties;
    private final List<Action> actions;
    private final List<District> districts;

    public Simulation(List<Party> parties, List<Action> actions,
                      List<PairToMerge> districtsPairsToMerge,
                      Map<Integer, District> numberToDistrict) {
        this.parties = parties;
        this.actions = actions;

        for (PairToMerge pairToMerge : districtsPairsToMerge) {
            District first = numberToDistrict.remove(pairToMerge.getFirst());
            District second = numberToDistrict.remove(pairToMerge.getSecond());

            District merged = first.merge(second);
            numberToDistrict.put(merged.getNumber(), merged);
        }

        this.districts = numberToDistrict.values()
                                         .stream()
                                         .sorted(Comparator.comparing(District::getNumber))
                                         .collect(Collectors.toCollection(ArrayList::new));
    }

    public void conductSimulation(SeatsAllocationMethod allocationMethod) {
        System.out.println("\n" + allocationMethod);

        parties.forEach(party -> party.conductCampaign(actions, districts));
        districts.forEach(district -> district.conductElections(allocationMethod));

        System.out.println("\nParties with number of seats from all districts:");
        parties.forEach(System.out::println);
    }

    @Override
    public void init() {
        districts.forEach(District::init);
        parties.forEach(Party::init);
    }

}
