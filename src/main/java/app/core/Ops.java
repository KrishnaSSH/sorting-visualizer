package app.core;

import java.util.ArrayList;
import java.util.List;

public final class Ops {
    private final ArrayList<Op> ops = new ArrayList<>();

    public void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        ops.add(new Op(OpType.SWAP, i, j, 0));
    }

    public void set(int[] a, int i, int v) {
        a[i] = v;
        ops.add(new Op(OpType.SET, i, 0, v));
    }

    public List<Op> list() {
        return ops;
    }
}
