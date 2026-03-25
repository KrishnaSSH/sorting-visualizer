package app.sort;

import app.core.Ops;

public class BinaryInsertionSort extends NamedSort {
    public BinaryInsertionSort() { super("Binary Insertion Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int lo = 0;
            int hi = i;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (a[mid] <= key) lo = mid + 1; else hi = mid;
            }
            for (int j = i; j > lo; j--) {
                ops.set(a, j, a[j - 1]);
            }
            ops.set(a, lo, key);
        }
    }
}
