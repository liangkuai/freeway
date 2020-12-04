package org.lock.freeway.server;

import lombok.extern.slf4j.Slf4j;
import org.lock.freeway.request.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liukai
 * @date 2020-12-03
 */
@Slf4j
public class RpcServer {



    public void register(Object service, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("start server ...");

            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("client connected");

                try (ObjectInputStream objInput = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream())) {

                    RpcRequest request = (RpcRequest) objInput.readObject();
                    Method method = service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
                    Object res = method.invoke(service, request.getParameters());
                    objOutput.writeObject(res);
                    objOutput.flush();
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
