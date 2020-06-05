package elections.data;

import elections.model.district.District;
import elections.model.party.Party;

import java.io.*;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataReader {

    private BufferedReader bufferedReader;
    private int districtsNumber;
    private int partiesNumber;
    private int actionsNumber;
    private int qualitiesNumber;
    private Map<Integer, District> numberToDistrict;
    private Map<String, Party> nameToParty;


    public DataReader(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    private void loadVoters() {

    }

    private void loadParties() throws IOException {
        String[] partyNames = bufferedReader.readLine()
                                            .split("\\s");
        String[] partyBudgets = bufferedReader.readLine()
                                              .split("\\s");
        String[] partyTypes = bufferedReader.readLine()
                                            .split("\\s");

        nameToParty = IntStream.range(0, partiesNumber)
                               .mapToObj(i -> Party.newInstance(partyTypes[i], partyNames[i],
                                                                Integer.parseInt(partyBudgets[i])))
                               .collect(Collectors.toMap(Party::getName, Function.identity()));
    }

    private void loadVotersInDistrictsNumbers() throws IOException {
        String[] votersNumbers = bufferedReader.readLine()
                                               .split("\\s");

        numberToDistrict = IntStream.rangeClosed(1, districtsNumber)
                                    .mapToObj(i -> new District(i, Integer.parseInt(votersNumbers[i])))
                                    .collect(Collectors.toMap(District::getNumber, Function.identity()));
    }

    private void loadCandidates() {
        for (int district = 1; district <= districtsNumber; district++) {
            for (int party = 1; party <= partiesNumber; party++) {

            }
        }
    }

}
