package app.sort;

import app.core.Ops;

public class RadixSort extends NamedSort {
    public RadixSort() { super("Radix Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int max = 0;
        for (int v : a) if (v > max) max = v;
        int exp = 1;
        int n = a.length;
        int[] out = new int[n];
        while (max / exp > 0) {
            int[] count = new int[10];
            for (int v : a) count[(v / exp) % 10]++;
            for (int i = 1; i < 10; i++) count[i] += count[i - 1];
            for (int i = n - 1; i >= 0; i--) {
                int v = a[i];
                int d = (v / exp) % 10;
                out[--count[d]] = v;
            }
            for (int i = 0; i < n; i++) ops.set(a, i, out[i]);
            exp *= 10;
        }
    }
}
