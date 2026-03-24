package app.sort;

import app.core.Ops;

public class QuickSort extends NamedSort {
    public QuickSort() { super("Quick Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        if (a.length > 0) SortUtils.quickSort(a, ops, 0, a.length - 1);
    }
}
