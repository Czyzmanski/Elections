package elections.model.district;

public class PairToMerge {

    private final Integer first;
    private final Integer second;

    public PairToMerge(Integer first, Integer second) {
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
