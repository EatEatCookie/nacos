/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.naming.cluster.remote.grpc;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.remote.request.Request;
import com.alibaba.nacos.api.remote.response.Response;
import com.alibaba.nacos.common.remote.client.RpcClientFactory;
import com.alibaba.nacos.common.remote.client.grpc.GrpcClient;
import com.alibaba.nacos.naming.cluster.remote.ClusterClient;

/**
 * Grpc cluster client.
 *
 * @author xiweng.yy
 */
public class GrpcClusterClient implements ClusterClient {
    
    private final GrpcClient grpcClient;
    
    public GrpcClusterClient(String targetAddress) {
        this.grpcClient = new GrpcClient(targetAddress);
    }
    
    @Override
    public void start() throws NacosException {
        grpcClient.start();
    }
    
    @Override
    public Response request(Request request) throws NacosException {
        return this.grpcClient.request(request);
    }
}