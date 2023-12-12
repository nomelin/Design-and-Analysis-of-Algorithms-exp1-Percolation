package nomelin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PercolationStats {
    private double mean; // 渗透阈值的样本均值
    private double stddev; // 渗透阈值的样本标准差
    private double confidence_low; // 返回 95% 置信区间的下界
    private double confidence_high; // 返回 95% 置信区间的上界

    public void monteCarlo(int n, int t) {
        // 对一个 N*N 的网格进行 T 次独立的计算实验
        if (t <= 1) {
            throw new IllegalArgumentException("t应>1");
        }
        List<Double> rate = new ArrayList<>();
        Random random = new Random();

        for (int k = 0; k < t; k++) {
            Percolation p = new Percolation(n);
            System.out.print("计算中，轮次: " + (k + 1) + ": ");
            for (int x = 1; x <= n * n; x++) {
                // 找到blocked点
                while (true) {
                    int i = random.nextInt(n) + 1;
                    int j = random.nextInt(n) + 1;
                    if (!p.isOpen(i, j)) {
                        p.open(i, j);
                        break;
                    }
                }
                // 简易进度条
                if (x % (n * n / 50 + 1) == 0) {
                    System.out.print("-");
                }
                // 判断连通
                if (p.percolates()) {
                    rate.add((double) x / (n * n));
                    break;
                }
            }
            System.out.print("\r"); // 回退光标到开头，刷新进度条
        }

        // 求参数
        double sum = 0.0;
        for (double r : rate) {
            sum += r;
        }
        mean = sum / t;

        double stddevSum = 0.0;
        for (double r : rate) {
            stddevSum += Math.pow(r - mean, 2);
        }
        stddev = Math.sqrt(stddevSum / (t - 1));

        confidence_low = mean - 1.96 * stddev / Math.sqrt(t);
        confidence_high = mean + 1.96 * stddev / Math.sqrt(t);
    }

    public double getMean() {
        return mean;
    }

    public double getStddev() {
        return stddev;
    }

    public double getConfidence_low() {
        return confidence_low;
    }

    public double getConfidence_high() {
        return confidence_high;
    }
}