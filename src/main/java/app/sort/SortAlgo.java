package app.sort;

import app.core.Ops;

public interface SortAlgo {
    String name();
    void generate(int[] a, Ops ops);
}
