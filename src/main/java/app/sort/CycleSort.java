package app.sort;

import app.core.Ops;

public class CycleSort extends NamedSort {
    public CycleSort() { super("Cycle Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int n = a.length;
        for (int cycleStart = 0; cycleStart <= n - 2; cycleStart++) {
            int item = a[cycleStart];
            int pos = cycleStart;
            for (int i = cycleStart + 1; i < n; i++) if (a[i] < item) pos++;
            if (pos == cycleStart) continue;
            while (item == a[pos]) pos++;
            int temp = a[pos];
            ops.set(a, pos, item);
            item = temp;
            while (pos != cycleStart) {
                pos = cycleStart;
                for (int i = cycleStart + 1; i < n; i++) if (a[i] < item) pos++;
                while (item == a[pos]) pos++;
                temp = a[pos];
                ops.set(a, pos, item);
                item = temp;
            }
        }
    }
}
