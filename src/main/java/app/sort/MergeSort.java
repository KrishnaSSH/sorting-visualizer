package app.sort;

import app.core.Ops;

public class MergeSort extends NamedSort {
    public MergeSort() { super("Merge Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        SortUtils.mergeSort(a, ops, 0, a.length);
    }
}
