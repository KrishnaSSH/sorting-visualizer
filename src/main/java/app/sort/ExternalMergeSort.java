package app.sort;

import app.core.Ops;

public class ExternalMergeSort extends NamedSort {
    public ExternalMergeSort() { super("External Merge Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        SortUtils.externalMergeSort(a, ops);
    }
}
