package nomelin;

public class QuickFindUF {
    private final int[] parent;

    public QuickFindUF(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    private int find(int p) {
        return parent[p];
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP != rootQ) {
            for (int i = 0; i < parent.length; i++) {
                if (parent[i] == rootP) {
                    parent[i] = rootQ;
                }
            }
        }
    }
}