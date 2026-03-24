package app.sort;

import app.core.Ops;

public class PigeonholeSort extends NamedSort {
    public PigeonholeSort() { super("Pigeonhole Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int min = a.length == 0 ? 0 : a[0];
        int max = min;
        for (int v : a) { if (v < min) min = v; if (v > max) max = v; }
        int size = max - min + 1;
        int[] holes = new int[size];
        for (int v : a) holes[v - min]++;
        int k = 0;
        for (int i = 0; i < size; i++) for (int c = 0; c < holes[i]; c++) ops.set(a, k++, i + min);
    }
}
