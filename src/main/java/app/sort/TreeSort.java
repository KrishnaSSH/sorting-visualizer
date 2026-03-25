package app.sort;

import app.core.Ops;

public class TreeSort extends NamedSort {
    public TreeSort() { super("Tree Sort"); }

    @Override
    public void generate(int[] a, Ops ops) {
        Node root = null;
        for (int v : a) root = insert(root, v);
        int[] idx = {0};
        inorder(root, a, ops, idx);
    }

    private Node insert(Node n, int v) {
        if (n == null) return new Node(v);
        if (v < n.v) n.left = insert(n.left, v); else n.right = insert(n.right, v);
        return n;
    }

    private void inorder(Node n, int[] a, Ops ops, int[] idx) {
        if (n == null) return;
        inorder(n.left, a, ops, idx);
        ops.set(a, idx[0]++, n.v);
        inorder(n.right, a, ops, idx);
    }

    private static final class Node {
        final int v;
        Node left;
        Node right;
        Node(int v) { this.v = v; }
    }
}
