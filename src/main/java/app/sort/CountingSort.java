package app.sort;

import app.core.Ops;

public class CountingSort extends NamedSort {
    public CountingSort() { super("Counting Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int max = 0;
        for (int v : a) if (v > max) max = v;
        int[] count = new int[max + 1];
        for (int v : a) count[v]++;
        int k = 0;
        for (int i = 0; i < count.length; i++) {
            for (int c = 0; c < count[i]; c++) ops.set(a, k++, i);
        }
    }
}
