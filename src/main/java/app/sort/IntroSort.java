package app.sort;

import app.core.Ops;

public class IntroSort extends NamedSort {
    public IntroSort() { super("IntroSort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        SortUtils.introSort(a, ops);
    }
}
