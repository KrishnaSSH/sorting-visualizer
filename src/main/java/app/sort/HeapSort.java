package app.sort;

import app.core.Ops;

public class HeapSort extends NamedSort {
    public HeapSort() { super("Heap Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        SortUtils.heapSort(a, ops);
    }
}
