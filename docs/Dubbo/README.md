# Dubbo

### 1. 角色

| 角色 | 说明 |
| :-- | :-- |
| Provider | 暴露服务的服务提供方 |
| Consumer | 调用远程服务的服务消费方 |
| Registry | 服务注册与发现的注册中心 |
| Monitor | 统计服务的调用次数和调用时间的监控中心 |
| Container | 服务运行容器 |


### 2. 调用关系

1. Container 负责启动，加载，运行 Provider。
2. Provider 在启动时，向 Registry 注册自己提供的服务。
3. Consumer 在启动时，向 Registry 订阅自己所需的服务。
4. Registry 返回 Provider 地址列表给 Consumer，如果有变更，Registry 将基于长连接推送变更数据给 Consumer。
5. Consumer 基于负载均衡算法，从 Provider 地址列表中选一台 Provider 进行调用，如果调用失败，再选另一台调用。
6. Provider 和 Consumer，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到 Monitor。

#### 重点

- Registry 负责服务地址的注册与查找，相当于目录服务，Provider 和 Consumer 只在启动时与 Registry 交互，Registry 不转发请求，压力较小。
- Registry、Provider、Consumer 三者之间均为长连接，Monitor 除外。
- Registry 通过长连接感知 Provider 的存在，Provider 宕机，Registry 立即推送事件通知 Consumer。
- Registry 和 Monitor 全部宕机，不影响已运行的 Provider 和 Consumer，Consumer 在本地缓存了 Provider 列表。
- Registry 和 Monitor 都是可选的，Provider 可以直连 Consumer。
- Provider 无状态，任意一台宕掉后，不影响使用。
- Provider 全部宕掉后，Consumer 应用将无法使用，并无限次重连等待 Provider 恢复。
- Monitor 负责统计各服务调用次数，调用时间等，统计先在内存汇总后每分钟一次发送到监控中心服务器，并以报表展。


### 3. 分层架构

| 层次 | 作用 |
| :--: | :-- |
| 1. Service | 业务层，由服务提供者和消费者来实现业务逻辑。 |
| 2. Config | 配置层，主要是对 dubbo 进行各种配置的。 |
| 3. Proxy | 代理层，服务提供者还是消费者都会生成一个代理类，使得服务接口透明化，代理层做远程调用和返回结果。 |
| 4. Registry | 服务注册层，负责服务的注册和发现 |
| 5. Cluster | 集群层，负责选取具体调用的节点（路由和负载均衡），处理特殊的调用要求和负责远程调用失败的容错措施。 |
| 6. Monitor | 监控层，RPC 接口的调用次数和调用时间进行监控。 |
| 7. Protocol | 远程调用层，主要是封装 RPC 调用，主要负责管理 Invoker，Invoker 代表一个抽象封装了的执行体。 |
| 8. Exchange | 信息交换层，封装请求响应模式，同步转异步。 |
| 9. Transport | 网络传输层，抽象 Mina 和 Netty 为统一接口。 |
| 10. Serialize | 数据序列化层，用于网络传输。 |
