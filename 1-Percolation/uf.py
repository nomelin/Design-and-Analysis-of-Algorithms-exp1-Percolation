import numpy as np


class WeightedQuickUnionUF:
    """带路径压缩的加权quick-union并查集"""

    def __init__(self, n):
        self.parent = np.arange(0, n, dtype=np.int32)  # 指向parent，初始指向自己，0到n-1
        self.size = np.ones(n, dtype=np.int32)  # 树的大小

    def find(self, p):
        root = p
        while root != self.parent[root]:
            root = self.parent[root]  # 根节点指向自己
        while p != root:  # 路径压缩,扁平化
            next_p = self.parent[p]
            self.parent[p] = root
            p = next_p
        return root

    def connected(self, p, q):
        return self.find(p) == self.find(q)

    def union(self, p, q):
        root_p = self.find(p)
        root_q = self.find(q)
        if root_p == root_q:
            return
        if self.size[root_p] < self.size[root_q]:
            self.parent[root_p] = root_q
            self.size[root_q] += self.size[root_p]
        else:
            self.parent[root_q] = root_p
            self.size[root_p] += self.size[root_q]


class QuickFindUF:
    def __init__(self, n):
        self.parent = np.arange(0, n, dtype=np.int32)

    def union(self, p, q):
        root_p = self.find(p)
        root_q = self.find(q)
        if root_p != root_q:
            for i in range(len(self.parent)):
                if self.parent[i] == root_p:
                    self.parent[i] = root_q

    def connected(self, p, q):
        return self.find(p) == self.find(q)

    def find(self, p):
        return self.parent[p]
