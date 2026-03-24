package app.sort;

import app.core.Ops;

public class SelectionSort extends NamedSort {
    public SelectionSort() { super("Selection Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) if (a[j] < a[min]) min = j;
            ops.swap(a, i, min);
        }
    }
}
