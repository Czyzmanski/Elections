package elections.model.district;

public class DistrictsToMerge {

    private final Integer first;
    private final Integer second;

    public DistrictsToMerge(Integer first, Integer second) {
        this.first = first;
        this.second = second;
    }

    public Integer getFirst() {
        return first;
    }

    public Integer getSecond() {
        return second;
    }

}
