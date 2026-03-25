package app.sort;

import app.core.Ops;

public class StoogeSort extends NamedSort {
    public StoogeSort() { super("Stooge Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        if (a.length > 1) stooge(a, ops, 0, a.length - 1);
    }

    private void stooge(int[] a, Ops ops, int i, int j) {
        if (a[i] > a[j]) ops.swap(a, i, j);
        if (j - i + 1 <= 2) return;
        int t = (j - i + 1) / 3;
        stooge(a, ops, i, j - t);
        stooge(a, ops, i + t, j);
        stooge(a, ops, i, j - t);
    }
}
