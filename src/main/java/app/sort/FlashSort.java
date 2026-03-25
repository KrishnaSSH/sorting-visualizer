package app.sort;

import app.core.Ops;

public class FlashSort extends NamedSort {
    public FlashSort() { super("Flash Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int n = a.length;
        if (n <= 1) return;

        int min = a[0];
        int max = a[0];
        int maxIdx = 0;
        for (int i = 1; i < n; i++) {
            int v = a[i];
            if (v < min) min = v;
            if (v > max) { max = v; maxIdx = i; }
        }
        if (min == max) return;

        int m = Math.max(4, (int) (0.43 * n));
        int[] l = new int[m];
        double c = (double) (m - 1) / (max - min);
        for (int v : a) l[(int) (c * (v - min))]++;
        for (int i = 1; i < m; i++) l[i] += l[i - 1];

        ops.swap(a, maxIdx, 0);
        int move = 0;
        int j = 0;
        int k = m - 1;

        while (move < n - 1) {
            while (j > l[k] - 1) {
                j++;
                k = (int) (c * (a[j] - min));
            }
            int flash = a[j];
            while (j != l[k]) {
                k = (int) (c * (flash - min));
                int dst = --l[k];
                int tmp = a[dst];
                ops.set(a, dst, flash);
                flash = tmp;
                move++;
            }
        }

        SortUtils.insertion(a, ops, 0, n);
    }
}
