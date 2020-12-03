# Dubbo 的负载均衡策略

### 1. Random LoadBalance（默认）
加权随机负载均衡

假设有两台服务器 A、B，权重比是 7:3，那么取一个 [1, 10] 之间的随机数，如果是 [1, 7] 选择服务器 A，[8, 10] 就选 B。


### 2. RoundRobin LoadBalance
加权轮询负载均衡

假设有两台服务器 A、B，权重比是 1:3，调用顺序就是：A、B、B、B ...；不过一般会打乱顺序，防止 20:30 这种，一台机器一次性调用几十次，导致其他机器空闲。


### 3. LeastActive LoadBalance
最少活跃数负载均衡

> 活跃数，来一个请求 +1，完成一个请求 -1。活跃数少说明当前处理的请求少，一定程度上也能反映处理速度快。

简单流程，先遍历 invokers 列表，寻找活跃数最小的 Invoker，如果有多个 Invoker 具有相同的最小活跃数，就随机选择。


### 4. ConsistentHash LoadBalance
一致性哈希负载均衡

> 参考：一致性哈希算法

- 相同参数的请求都是发到同一个提供者；
- 当某一台提供者挂掉时，原本发往该提供者的请求，基于虚拟节点，会平摊到其它提供者，不会引起剧烈变动；
- 缺省用 160 份虚拟节点，如果要修改，配置 `<dubbo:parameter key="hash.nodes" value="320" />`。


### 自定义
- 抽象类 `com.alibaba.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance` 实现了 `LoadBalance` 接口；
- 所有负载均衡策略类都继承了抽象类 `AbstractLoadBalance`。