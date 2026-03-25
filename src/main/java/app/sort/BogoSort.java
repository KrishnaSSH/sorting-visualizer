package app.sort;

import app.core.Ops;

import java.util.Random;

public class BogoSort extends NamedSort {
    private static final int MAX_SHUFFLES = 50000;
    private final Random rng = new Random();

    public BogoSort() { super("Bogo Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int shuffles = 0;
        while (!isSorted(a) && shuffles < MAX_SHUFFLES) {
            shuffle(a, ops);
            shuffles++;
        }
        if (!isSorted(a)) SortUtils.insertion(a, ops, 0, a.length);
    }

    private boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }

    private void shuffle(int[] a, Ops ops) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            ops.swap(a, i, j);
        }
    }
}
