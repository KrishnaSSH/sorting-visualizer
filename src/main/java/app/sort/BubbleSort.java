package app.sort;

import app.core.Ops;

public class BubbleSort extends NamedSort {
    public BubbleSort() { super("Bubble Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) ops.swap(a, j, j + 1);
            }
        }
    }
}
