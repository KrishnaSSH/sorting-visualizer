package app.sort;

import app.core.Ops;

public class OddEvenSort extends NamedSort {
    public OddEvenSort() { super("Odd-Even Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        boolean sorted = false;
        int n = a.length;
        while (!sorted) {
            sorted = true;
            for (int i = 1; i < n - 1; i += 2) if (a[i] > a[i + 1]) { ops.swap(a, i, i + 1); sorted = false; }
            for (int i = 0; i < n - 1; i += 2) if (a[i] > a[i + 1]) { ops.swap(a, i, i + 1); sorted = false; }
        }
    }
}
