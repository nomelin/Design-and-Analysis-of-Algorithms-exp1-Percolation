package nomelin;

public class WeightedQuickUnionUF {
    private final int[] parent; // 指向parent，初始指向自己，0到n-1
    private final int[] size; // 树的大小

    public WeightedQuickUnionUF(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    private int find(int p) {
        int root = p;
        while (root != parent[root]) {
            root = parent[root]; // 根节点指向自己
        }
        while (p != root) { // 路径压缩,扁平化
            int nextP = parent[p];
            parent[p] = root;
            p = nextP;
        }
        return root;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return;
        }
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }
}
