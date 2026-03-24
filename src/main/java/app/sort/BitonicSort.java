package app.sort;

import app.core.Ops;

public class BitonicSort extends NamedSort {
    public BitonicSort() { super("Bitonic Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        bitonic(a, ops, 0, a.length, true);
    }

    void bitonic(int[] a, Ops ops, int lo, int cnt, boolean dir) {
        if (cnt <= 1) return;
        int k = cnt / 2;
        bitonic(a, ops, lo, k, true);
        bitonic(a, ops, lo + k, k, false);
        bitonicMerge(a, ops, lo, cnt, dir);
    }

    void bitonicMerge(int[] a, Ops ops, int lo, int cnt, boolean dir) {
        if (cnt <= 1) return;
        int k = cnt / 2;
        for (int i = lo; i < lo + k; i++) {
            if ((a[i] > a[i + k]) == dir) ops.swap(a, i, i + k);
        }
        bitonicMerge(a, ops, lo, k, dir);
        bitonicMerge(a, ops, lo + k, k, dir);
    }
}
