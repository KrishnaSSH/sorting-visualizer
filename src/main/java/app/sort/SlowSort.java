package app.sort;

import app.core.Ops;

public class SlowSort extends NamedSort {
    public SlowSort() { super("Slow Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        if (a.length > 1) slowsort(a, ops, 0, a.length - 1);
    }

    private void slowsort(int[] a, Ops ops, int lo, int hi) {
        if (lo >= hi) return;
        int m = (lo + hi) >>> 1;
        slowsort(a, ops, lo, m);
        slowsort(a, ops, m + 1, hi);
        if (a[m] > a[hi]) ops.swap(a, m, hi);
        slowsort(a, ops, lo, hi - 1);
    }
}
