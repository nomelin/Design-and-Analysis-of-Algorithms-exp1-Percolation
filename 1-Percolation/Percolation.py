import math
import random
from datetime import datetime

from uf import *


class Percolation:
    def __init__(self, n):
        if n <= 0:
            raise IndexError("网格n应>0")
        self.uf = WeightedQuickUnionUF(n ** 2 + 2)  # n**2为虚拟顶点，n**2+1为虚拟底点
        self.grid = np.zeros(n ** 2, dtype=np.bool_)
        self.n = n

    def __get_index(self, i, j):
        return (i - 1) * self.n + j - 1

    def __get_surrounding(self, i, j) -> []:
        points = []
        if i > 1:  # 上方格点
            points.append((i - 1, j))
        if i < self.n:  # 下方格点
            points.append((i + 1, j))
        if j > 1:  # 左边格点
            points.append((i, j - 1))
        if j < self.n:  # 右边格点
            points.append((i, j + 1))
        return points

    def print_grid(self):
        mapping = {True: '■', False: '□'}
        for i in range(1, self.n + 1):
            for j in range(1, self.n + 1):
                print(mapping[self.is_open(i, j)], end=' ')
            print()

    def open(self, i, j):
        self.grid[self.__get_index(i, j)] = True
        # 连接相邻点
        for x, y in self.__get_surrounding(i, j):
            if self.is_open(x, y):
                self.uf.union(self.__get_index(i, j), self.__get_index(x, y))
        if i == 1:  # 与虚拟顶点相连
            self.uf.union(self.__get_index(i, j), n ** 2)
        if i == self.n:  # 与虚拟底点相连
            self.uf.union(self.__get_index(i, j), n ** 2 + 1)

    def is_open(self, i, j):
        return self.grid[self.__get_index(i, j)]

    def is_full(self, i, j):
        return self.uf.connected(self.__get_index(i, j), n ** 2)

    def percolates(self):
        return self.uf.connected(n ** 2, n ** 2 + 1)


class PercolationStats:
    def __init__(self):
        self.mean = 0.0  # 渗透阈值的样本均值
        self.stddev = 0.0  # 渗透阈值的样本标准差
        self.confidence_low = 0.0  # 返回 95% 置信区间的下界
        self.confidence_high = 0.0  # 返回 95% 置信区间的上界

    def monte_carlo(self, n, t):
        # 对一个 N*N 的网格进行 T 次独立的计算实验
        if t <= 1:
            raise TypeError("t应>1")
        rate = []
        for k in range(t):
            p = Percolation(n)
            print('计算中,轮次:', k + 1, end=': ')
            for x in range(1, n ** 2 + 1):
                # 找到blocked点
                while True:
                    i = random.randint(1, n)
                    j = random.randint(1, n)
                    if not p.is_open(i, j):
                        p.open(i, j)
                        break
                # 简易进度条
                if x % (n ** 2 // 50 + 1) == 0:
                    print('-', end='')
                # 判断连通
                if p.percolates():
                    rate.append(x / (n ** 2))
                    # print("{}个方块，{}次时导通".format(n ** 2, x))
                    # p.print_grid()
                    break
            print('\r', end='')  # 回退光标到开头，刷新进度条
        # 求参数
        self.mean = sum(rate) / t
        li = [(x - self.mean) ** 2 for x in rate]
        self.stddev = math.sqrt(sum(li) / (t - 1))
        self.confidence_low = self.mean - 1.96 * self.stddev / math.sqrt(t)
        self.confidence_high = self.mean + 1.96 * self.stddev / math.sqrt(t)


ps = PercolationStats()
while True:
    n = int(input("输入网格大小(边长):"))
    t = int(input("输入测试次数:"))

    start_time = datetime.now()

    ps.monte_carlo(n, t)

    end_time = datetime.now()
    duration = end_time - start_time
    print("总计运行时间：{}秒\n平均运行时间：{}秒".format(duration.total_seconds(), duration.total_seconds() / t))
    print("均值：", ps.mean)
    print("标准差：", ps.stddev)
    print("置信上界：", ps.confidence_high)
    print("置信下界：", ps.confidence_low)
    print()
