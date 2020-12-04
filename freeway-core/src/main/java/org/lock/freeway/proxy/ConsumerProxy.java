package org.lock.freeway.proxy;

import org.lock.freeway.request.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author liukai
 * @date 2020-12-03
 */
public class ConsumerProxy implements InvocationHandler {

    private String host;
    private int port;

    public ConsumerProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest request = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .build();

        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
            objOutput.writeObject(request);
            ObjectInputStream objInput = new ObjectInputStream(socket.getInputStream());
            return objInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, ConsumerProxy.this);
    }

}
