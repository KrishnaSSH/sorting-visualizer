package app.sort;

import java.util.List;

public final class Sorts {
    private Sorts() {}

    public static final List<SortAlgo> ALL = List.of(
            new BubbleSort(),
            new SelectionSort(),
            new InsertionSort(),
            new MergeSort(),
            new QuickSort(),
            new HeapSort(),
            new CountingSort(),
            new RadixSort(),
            new BucketSort(),
            new TimSort(),
            new IntroSort(),
            new ShellSort(),
            new CocktailShakerSort(),
            new CombSort(),
            new GnomeSort(),
            new OddEvenSort(),
            new PigeonholeSort(),
            new CycleSort(),
            new BitonicSort(),
            new ExternalMergeSort(),
            new PancakeSort()
    );
}
