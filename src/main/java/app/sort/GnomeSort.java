package app.sort;

import app.core.Ops;

public class GnomeSort extends NamedSort {
    public GnomeSort() { super("Gnome Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        int n = a.length;
        int i = 0;
        while (i < n) {
            if (i == 0 || a[i] >= a[i - 1]) i++; else { ops.swap(a, i, i - 1); i--; }
        }
    }
}
