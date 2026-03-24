package app.sort;

import app.core.Ops;

public class CocktailShakerSort extends NamedSort {
    public CocktailShakerSort() { super("Cocktail Shaker Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        boolean swapped = true;
        int start = 0, end = a.length - 1;
        while (swapped) {
            swapped = false;
            for (int i = start; i < end; i++) if (a[i] > a[i + 1]) { ops.swap(a, i, i + 1); swapped = true; }
            if (!swapped) break;
            swapped = false;
            end--;
            for (int i = end - 1; i >= start; i--) if (a[i] > a[i + 1]) { ops.swap(a, i, i + 1); swapped = true; }
            start++;
        }
    }
}
