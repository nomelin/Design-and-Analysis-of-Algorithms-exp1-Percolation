package nomelin;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("输入网格大小(边长)(输入-1退出): ");
            int n = scanner.nextInt();
            if (n == -1) {
                break;
            }
            System.out.print("输入测试次数: ");
            int t = scanner.nextInt();

            long startTime = System.nanoTime();

            ps.monteCarlo(n, t);

            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1_000_000_000.0; // 将纳秒转换为秒

            System.out.println("总计运行时间: " + duration + "秒");
            System.out.println("平均运行时间: " + duration / t + "秒");
            System.out.println("均值: " + ps.getMean());
            System.out.println("标准差: " + ps.getStddev());
            System.out.println("置信上界: " + ps.getConfidence_high());
            System.out.println("置信下界: " + ps.getConfidence_low());
            System.out.println();
        }
    }
}