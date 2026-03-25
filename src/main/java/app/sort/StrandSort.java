package app.sort;

import app.core.Ops;

import java.util.ArrayList;
import java.util.List;

public class StrandSort extends NamedSort {
    public StrandSort() { super("Strand Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        List<Integer> input = new ArrayList<>(a.length);
        for (int v : a) input.add(v);
        List<Integer> output = new ArrayList<>(a.length);

        while (!input.isEmpty()) {
            List<Integer> strand = new ArrayList<>();
            for (int i = 0; i < input.size();) {
                int v = input.get(i);
                if (strand.isEmpty() || v >= strand.get(strand.size() - 1)) {
                    strand.add(v);
                    input.remove(i);
                } else {
                    i++;
                }
            }

            List<Integer> merged = new ArrayList<>(output.size() + strand.size());
            int i = 0;
            int j = 0;
            while (i < output.size() && j < strand.size()) {
                if (output.get(i) <= strand.get(j)) merged.add(output.get(i++)); else merged.add(strand.get(j++));
            }
            while (i < output.size()) merged.add(output.get(i++));
            while (j < strand.size()) merged.add(strand.get(j++));
            output = merged;

            int k = 0;
            for (int v : output) ops.set(a, k++, v);
            for (int v : input) ops.set(a, k++, v);
        }
    }
}
