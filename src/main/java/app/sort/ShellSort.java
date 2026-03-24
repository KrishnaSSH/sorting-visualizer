package app.sort;

import app.core.Ops;

public class ShellSort extends NamedSort {
    public ShellSort() { super("Shell Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int n = a.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = a[i];
                int j = i;
                while (j >= gap && a[j - gap] > temp) {
                    ops.set(a, j, a[j - gap]);
                    j -= gap;
                }
                ops.set(a, j, temp);
            }
        }
    }
}
