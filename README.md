#### （一）渗透问题（Percolation）

使用合并-查找（union-find）数据结构，编写程序通过蒙特卡罗模拟（Monte Carlo simulation）来估计渗透阈值。

给定由随机分布的绝缘材料和金属材料构成的组合系统：金属材料占多大比例才能使组合系统成为电导体？ 给定一个表面有水的多孔景观（或下面有油），水将在什么条件下能够通过底部排出（或油渗透到表面）？ 科学家已经定义了一个称为渗透（percolation）的抽象过程来模拟这种现象。

**模型**

 我们使用 N×N 网格点来模型化一个渗透系统。 每个格点或是 open 格点或是 blocked 格点。 一个 full site 是一个 open 格点，它可以通过一系列的邻近（左、右、上、下）open 格点连通到顶行的一个 open 格 点。如果在底行中有一个 full site 格点，则称系统是渗透的。（对于绝缘/金属材料的例子，open 格点对应于 金属材料，渗透系统有一条从顶行到底行的金属材料路径，且 full sites 格点导电。对于多孔物质示例，open 格点对应于空格，水可能流过，从而渗透系统使水充满 open 格点，自顶向下流动。）
![image](https://github.com/nomelin/Design-and-Analysis-of-Algorithms-exp1-Percolation/assets/107407124/08dd19c0-8c49-4090-82bb-22e2d1502f8e)

**科学问题**

在一个著名的科学问题中，研究人员对以下问题感兴趣：如果将格点以概率 p 独立地设置为 open 格点（因此以概率 1-p 被设置为 blocked 格点），系统渗透的概率是多少？ 当 p = 0 时，系统不会渗出; 当 p=1 时，系统渗透。下图显示了 20×20 随机网格（左）和 100×100 随机网格（右）的格点空置概率 p 与渗 滤概率。
![image](https://github.com/nomelin/Design-and-Analysis-of-Algorithms-exp1-Percolation/assets/107407124/c5672fa3-fd62-438a-ae3e-97610a47116a)

当 N 足够大时，存在阈值 p*，使得当 p  p*时，随机 N N 网格几乎总是渗透。 尚未得出用于确定渗滤阈值 p*的数学解。你的任务是编写一个计算机程序来估计 p*。

**蒙特卡洛模拟（Monte Carlo simulation）**

要估计渗透阈值，考虑以下计算实验： 初始化所有格点为 blocked。 重复以下操作直到系统渗出： 

- 在所有 blocked 的格点之间随机均匀选择一个格点 (row i, column j)。 

- 设置这个格点(row i, column j)为 open 格点。 

- open 格点的比例提供了系统渗透时渗透阈值的一个估计。

  

例如，如果在 20×20 的网格中，根据以下快照的 open 格点数，那么对渗滤阈值的估计是 204/400 = 0.51， 因为当第 204 个格点被 open 时系统渗透。
![image](https://github.com/nomelin/Design-and-Analysis-of-Algorithms-exp1-Percolation/assets/107407124/e8fbea90-a68d-4856-8ceb-a10238946495)


通过重复该计算实验 T 次并对结果求平均值，我们获得了更准确的渗滤阈值估计。 令 xt是第 t 次计算实 验中 open 格点所占比例。样本均值提供渗滤阈值的一个估计值；样本标准差测量阈值的灵敏性。
![image](https://github.com/nomelin/Design-and-Analysis-of-Algorithms-exp1-Percolation/assets/107407124/11c92ce7-4806-4d8f-8714-abd68bd03c2e)
假设 T 足够大（例如至少 30），以下为渗滤阈值提供 95％置信区间：
![image](https://github.com/nomelin/Design-and-Analysis-of-Algorithms-exp1-Percolation/assets/107407124/1ba5a34d-caf2-4d9a-8bcb-56e24527cd98)

