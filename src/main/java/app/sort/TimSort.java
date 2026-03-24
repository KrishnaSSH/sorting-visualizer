package app.sort;

import app.core.Ops;

public class TimSort extends NamedSort {
    public TimSort() { super("TimSort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        SortUtils.timSort(a, ops);
    }
}
