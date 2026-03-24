package app.sort;

public abstract class NamedSort implements SortAlgo {
    private final String name;

    protected NamedSort(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
