package app.core;

import app.sort.SortAlgo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class Visualizer {
    private final Random rng = new Random();
    private int[] arr = new int[0];
    private List<Op> ops = List.of();
    private int opIndex = 0;

    public void init(int n) {
        arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;
        shuffle();
        clearOps();
    }

    public void shuffle() {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
        clearOps();
    }

    public void prepare(SortAlgo algo) {
        Ops o = new Ops();
        int[] copy = Arrays.copyOf(arr, arr.length);
        algo.generate(copy, o);
        ops = o.list();
        opIndex = 0;
    }

    public boolean step(int steps) {
        for (int s = 0; s < steps && opIndex < ops.size(); s++) {
            apply(ops.get(opIndex++));
        }
        return opIndex >= ops.size();
    }

    public int[] array() {
        return arr;
    }

    public int opIndex() {
        return opIndex;
    }

    public int opTotal() {
        return ops.size();
    }

    public void clearOps() {
        ops = List.of();
        opIndex = 0;
    }

    private void apply(Op op) {
        if (op.t() == OpType.SWAP) {
            int i = op.i(), j = op.j();
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        } else {
            arr[op.i()] = op.v();
        }
    }
}
