package nomelin;

import java.util.ArrayList;
import java.util.List;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final boolean[] grid;
    private final int n;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IndexOutOfBoundsException("网格n应>0");
        }
        uf = new WeightedQuickUnionUF(n * n + 2); // n*n为虚拟顶点，n*n+1为虚拟底点
        grid = new boolean[n * n];
        this.n = n;
    }

    private int getIndex(int i, int j) {
        return (i - 1) * n + j - 1;
    }

    private List<Integer> getSurrounding(int i, int j) {
        List<Integer> points = new ArrayList<>();
        if (i > 1) { // 上方格点
            points.add(getIndex(i - 1, j));
        }
        if (i < n) { // 下方格点
            points.add(getIndex(i + 1, j));
        }
        if (j > 1) { // 左边格点
            points.add(getIndex(i, j - 1));
        }
        if (j < n) { // 右边格点
            points.add(getIndex(i, j + 1));
        }
        return points;
    }

    public void printGrid() {
        char[] mapping = {'□', '■'};
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(mapping[isOpen(i, j) ? 1 : 0] + " ");
            }
            System.out.println();
        }
    }

    public void open(int i, int j) {
        grid[getIndex(i, j)] = true;
        // 连接相邻点
        for (int point : getSurrounding(i, j)) {
            int x = point / n + 1;
            int y = point % n + 1;
            if (isOpen(x, y)) {
                uf.union(getIndex(i, j), point);
            }
        }
        if (i == 1) { // 与虚拟顶点相连
            uf.union(getIndex(i, j), n * n);
        }
        if (i == n) { // 与虚拟底点相连
            uf.union(getIndex(i, j), n * n + 1);
        }
    }

    public boolean isOpen(int i, int j) {
        return grid[getIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        return uf.connected(getIndex(i, j), n * n);
    }

    public boolean percolates() {
        return uf.connected(n * n, n * n + 1);
    }
}
