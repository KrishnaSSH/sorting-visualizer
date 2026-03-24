package app.sort;

import app.core.Ops;

public class PancakeSort extends NamedSort {
    public PancakeSort() { super("Pancake Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        for (int size = a.length; size > 1; size--) {
            int maxIdx = 0;
            for (int i = 1; i < size; i++) if (a[i] > a[maxIdx]) maxIdx = i;
            if (maxIdx == size - 1) continue;
            if (maxIdx > 0) flip(a, ops, maxIdx);
            flip(a, ops, size - 1);
        }
    }

    void flip(int[] a, Ops ops, int end) {
        int i = 0, j = end;
        while (i < j) {
            ops.swap(a, i, j);
            i++;
            j--;
        }
    }
}
