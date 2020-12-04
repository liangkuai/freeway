package org.lock.freeway;

import org.lock.freeway.api.HelloService;
import org.lock.freeway.proxy.ConsumerProxy;

/**
 * @author liukai
 * @date 2020-12-03
 */
public class RpcConsumerMain {

    public static void main(String[] args) {
        ConsumerProxy proxy = new ConsumerProxy("127.0.0.1", 9999);
        HelloService helloService = proxy.getProxy(HelloService.class);
        System.out.println(helloService.hello("lock"));
    }
}
