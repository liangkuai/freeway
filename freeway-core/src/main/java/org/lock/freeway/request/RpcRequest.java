package org.lock.freeway.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liukai
 * @date 2020-12-03
 */
@Data
@Builder
public class RpcRequest implements Serializable {

    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

}
