/*
 * The MIT License
 *
 * Copyright 2025 Joerg.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.arteam.simplejsonrpc.server.serviceresolver;

import com.github.arteam.simplejsonrpc.server.JsonRpcServer;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON-RPC service resolver for multiple services encoded in
 * JSON request method name.
 * Expect naming "service.method" dot separated string with only one
 * dot service name depth.
 */
public class DefaultServiceResolver implements JsonRpcServer.ServiceResolver {

    private final Map<String, Object> services = new HashMap<>();
    
    /**
     * Return resolved service object to given service address.
     * This implementation works on simple string mapping.
     * @param serviceAddress extracted from request method.
     * @return service object, or null if service is not known
     */
    @Override
    public Object getService(String serviceAddress) {
        return services.get(serviceAddress);
    }

    /**
     * Register a service object with given serviceAddress
     * @param serviceAddress for registering the service
     * @param service object to be registered
     * @return this for fluent usage
     */
    public DefaultServiceResolver add(String serviceAddress, Object service) {
        services.put(serviceAddress, service);
        return this;
    }
    
    @Override
    public String extractMethod(String method) {
        //expect "service.method", dot separated string with only one dot.
        int lastIndexOfDot = method.lastIndexOf('.');
        if (-1 != lastIndexOfDot) {
            return method.substring(lastIndexOfDot + 1);
        } else {
            return method;
        }
    }

    @Override
    public String extractServiceAddress(String method) {
        //expect "service.method", dot separated string with only one dot.
        int lastIndexOfDot = method.lastIndexOf('.');
        if (-1 != lastIndexOfDot) {
            return method.substring(0, lastIndexOfDot);
        } else {
            return null;
        }
    }
    
}
