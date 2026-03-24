package app.sort;

import app.core.Ops;

import java.util.Arrays;

public final class SortUtils {
    private SortUtils() {}

    public static void insertion(int[] a, Ops ops, int l, int r) {
        for (int i = l + 1; i < r; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= l && a[j] > key) {
                ops.set(a, j + 1, a[j]);
                j--;
            }
            ops.set(a, j + 1, key);
        }
    }

    public static void mergeSort(int[] a, Ops ops, int l, int r) {
        if (r - l <= 1) return;
        int m = (l + r) >>> 1;
        mergeSort(a, ops, l, m);
        mergeSort(a, ops, m, r);
        merge(a, ops, l, m, r);
    }

    public static void merge(int[] a, Ops ops, int l, int m, int r) {
        int[] left = Arrays.copyOfRange(a, l, m);
        int[] right = Arrays.copyOfRange(a, m, r);
        int i = 0, j = 0, k = l;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) ops.set(a, k++, left[i++]); else ops.set(a, k++, right[j++]);
        }
        while (i < left.length) ops.set(a, k++, left[i++]);
        while (j < right.length) ops.set(a, k++, right[j++]);
    }

    public static void quickSort(int[] a, Ops ops, int lo, int hi) {
        if (lo >= hi) return;
        int p = partition(a, ops, lo, hi);
        quickSort(a, ops, lo, p - 1);
        quickSort(a, ops, p + 1, hi);
    }

    public static int partition(int[] a, Ops ops, int lo, int hi) {
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j] <= pivot) { ops.swap(a, i, j); i++; }
        }
        ops.swap(a, i, hi);
        return i;
    }

    public static void heapSort(int[] a, Ops ops) {
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(a, ops, n, i);
        for (int i = n - 1; i > 0; i--) {
            ops.swap(a, 0, i);
            heapify(a, ops, i, 0);
        }
    }

    public static void heapify(int[] a, Ops ops, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && a[l] > a[largest]) largest = l;
        if (r < n && a[r] > a[largest]) largest = r;
        if (largest != i) {
            ops.swap(a, i, largest);
            heapify(a, ops, n, largest);
        }
    }

    public static void timSort(int[] a, Ops ops) {
        int n = a.length;
        int run = 32;
        for (int i = 0; i < n; i += run) insertion(a, ops, i, Math.min(i + run, n));
        for (int size = run; size < n; size *= 2) {
            for (int l = 0; l < n; l += 2 * size) {
                int m = Math.min(l + size, n);
                int r = Math.min(l + 2 * size, n);
                if (m < r) merge(a, ops, l, m, r);
            }
        }
    }

    public static void introSort(int[] a, Ops ops) {
        int depth = a.length == 0 ? 0 : 2 * (int) (Math.log(a.length) / Math.log(2));
        intro(a, ops, 0, a.length - 1, depth);
        insertion(a, ops, 0, a.length);
    }

    static void intro(int[] a, Ops ops, int lo, int hi, int depth) {
        if (hi - lo < 16) return;
        if (depth == 0) { heapRange(a, ops, lo, hi); return; }
        int p = partition(a, ops, lo, hi);
        intro(a, ops, lo, p - 1, depth - 1);
        intro(a, ops, p + 1, hi, depth - 1);
    }

    static void heapRange(int[] a, Ops ops, int lo, int hi) {
        int n = hi - lo + 1;
        for (int i = n / 2 - 1; i >= 0; i--) heapifyRange(a, ops, n, i, lo);
        for (int i = n - 1; i > 0; i--) {
            ops.swap(a, lo, lo + i);
            heapifyRange(a, ops, i, 0, lo);
        }
    }

    static void heapifyRange(int[] a, Ops ops, int n, int i, int off) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && a[off + l] > a[off + largest]) largest = l;
        if (r < n && a[off + r] > a[off + largest]) largest = r;
        if (largest != i) {
            ops.swap(a, off + i, off + largest);
            heapifyRange(a, ops, n, largest, off);
        }
    }

    public static void externalMergeSort(int[] a, Ops ops) {
        int n = a.length;
        int run = 16;
        for (int i = 0; i < n; i += run) insertion(a, ops, i, Math.min(i + run, n));
        for (int size = run; size < n; size *= 2) {
            for (int l = 0; l < n; l += 2 * size) {
                int m = Math.min(l + size, n);
                int r = Math.min(l + 2 * size, n);
                if (m < r) merge(a, ops, l, m, r);
            }
        }
    }
}
