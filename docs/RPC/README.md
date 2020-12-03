# RPC

> Remote Procedure Call，远程过程调用。目的是为了让调用远程方法和调用本地方法一样简单。

> 参考
> - [如何给老婆解释什么是RPC - 柳树的文章 - 知乎](https://zhuanlan.zhihu.com/p/36427583)
> - [谁能用通俗的语言解释一下什么是 RPC 框架？ - 洪春涛的回答 - 知乎](https://www.zhihu.com/question/25536695/answer/221638079)

### 原理

![RPC](/assets/images/RPC/RPC.jpg)

1. 服务消费方（client）以本地调用方式调用服务；
2. client stub 接收到调用后，负责将方法、参数等组装成能够进行网络传输的消息体；
3. client stub 找到服务提供方（server）地址，并将消息发送到服务端；
4. server stub 收到消息后进行解码；
5. server stub 根据解码结果调用本地的服务；
6. 本地服务执行并将结果返回给 server stub；
7. server stub 将返回结果打包成消息并发送至服务消费方（server）；
8. client stub 接收到消息，并进行解码；
9. 服务消费方（client）得到最终结果。


### 为什么要用 RPC，不用 HTTP？

> 参考
> - [微服务调用为啥用RPC框架，http不更简单吗？ - 支付技术那些事的文章 - 知乎](https://zhuanlan.zhihu.com/p/61364466)
> - [既然有 HTTP 请求，为什么还要用 RPC 调用？ - 易哥的回答 - 知乎](https://www.zhihu.com/question/41609070/answer/1030913797)