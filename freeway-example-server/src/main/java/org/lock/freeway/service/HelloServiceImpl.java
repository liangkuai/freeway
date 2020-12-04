package org.lock.freeway.service;

import lombok.extern.slf4j.Slf4j;
import org.lock.freeway.api.HelloService;

/**
 * @author liukai
 * @date 2020-12-03
 */
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String msg) {
        log.info(this.getClass().getSimpleName() + ", received");
        String res = "Hello, " + msg;
        log.info(this.getClass().getSimpleName() + ", over");
        return res;
    }

}
