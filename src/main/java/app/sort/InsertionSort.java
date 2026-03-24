package app.sort;

import app.core.Ops;

public class InsertionSort extends NamedSort {
    public InsertionSort() { super("Insertion Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        SortUtils.insertion(a, ops, 0, a.length);
    }
}
