package app.sort;

import app.core.Ops;

import java.util.ArrayList;
import java.util.List;

public class BucketSort extends NamedSort {
    public BucketSort() { super("Bucket Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int n = a.length;
        int max = 1;
        for (int v : a) if (v > max) max = v;
        int buckets = Math.max(4, (int) Math.sqrt(n));
        List<List<Integer>> b = new ArrayList<>();
        for (int i = 0; i < buckets; i++) b.add(new ArrayList<>());
        for (int v : a) {
            int idx = (int) ((long) v * buckets / (max + 1));
            b.get(idx).add(v);
        }
        int k = 0;
        for (List<Integer> bucket : b) {
            bucket.sort(Integer::compareTo);
            for (int v : bucket) ops.set(a, k++, v);
        }
    }
}
