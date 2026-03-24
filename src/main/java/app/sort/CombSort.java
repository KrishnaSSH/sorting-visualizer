package app.sort;

import app.core.Ops;

public class CombSort extends NamedSort {
    public CombSort() { super("Comb Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int n = a.length;
        int gap = n;
        boolean swapped = true;
        while (gap != 1 || swapped) {
            gap = (gap * 10) / 13;
            if (gap < 1) gap = 1;
            swapped = false;
            for (int i = 0; i + gap < n; i++) {
                if (a[i] > a[i + gap]) { ops.swap(a, i, i + gap); swapped = true; }
            }
        }
    }
}
