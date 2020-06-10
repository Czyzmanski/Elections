package elections;

import elections.dataloader.DataLoader;
import elections.model.district.District;
import elections.model.district.PairToMerge;
import elections.model.seats.HareNiemeyerMethod;
import elections.model.seats.DHondtMethod;
import elections.model.seats.SeatsAllocationMethod;
import elections.model.seats.SainteLagueMethod;
import elections.model.party.Action;
import elections.model.party.Party;
import elections.simulation.Simulation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            inputStream = args.length == 0 ? System.in : new FileInputStream(args[0]);
        } catch (FileNotFoundException e) {
            System.err.println("The file does not exist, is a directory or cannot be opened.");
            System.exit(-1);
        }

        try (DataLoader dataLoader = new DataLoader(inputStream)) {
            dataLoader.loadData();

            List<Party> parties = dataLoader.parties();
            List<Action> actions = dataLoader.actions();
            List<PairToMerge> districtsPairsToMerge = dataLoader.districtsPairsToMerge();
            Map<Integer, District> numberToDistrict = dataLoader.numberToDistrict();

            Simulation simulation = new Simulation(parties, actions,
                                                   districtsPairsToMerge, numberToDistrict);
            SeatsAllocationMethod[] allocationMethods = {new DHondtMethod(),
                                                         new SainteLagueMethod(),
                                                         new HareNiemeyerMethod()};

            for (SeatsAllocationMethod allocationMethod : allocationMethods) {
                simulation.init();
                simulation.conductSimulation(allocationMethod);
            }
        } catch (IOException e) {
            System.err.println("An IO error has occurred when loading data.");
            System.exit(-1);
        }
    }

}
