package org.lock.freeway;

import org.lock.freeway.api.HelloService;
import org.lock.freeway.server.RpcServer;
import org.lock.freeway.service.HelloServiceImpl;

/**
 * @author liukai
 * @date 2020-12-03
 */
public class RpcProviderMain {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9999);
    }

}
