/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.cubic.agent.core.jmx.jvm.dbpool;

import com.cubic.agent.core.jmx.MBeanAttributeRetriever;
import com.cubic.serialization.agent.v1.DruidPoolMetric;
import com.cubic.serialization.agent.v1.JVMGCMetric;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * jvm线程栈采集
 *
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
public enum DruidProvider {

    INSTANCE;

    private final MBeanAttributeRetriever mBeanAttributeFetcher;

    DruidProvider() {
        this.mBeanAttributeFetcher = MBeanAttributeRetriever.getInstance();
    }

    /**
     * druid数据库连接池<br>
     * 连接数	com.alibaba.druid.pool:name=dataSource,type=DruidDataSource,attribute=PoolingCount<br>
     * 活动连接数	com.alibaba.druid.pool:name=dataSource,type=DruidDataSource,attribute=ActiveCount<br>
     * 空闲连接数	IdleCount = PoolingCount - ActiveCount<br>
     * 活动连接数峰值	om.alibaba.druid.pool:name=dataSource,type=DruidDataSource,attribute=ActivePeak<br>
     * 获取的连接数	com.alibaba.druid.pool:name=dataSource,type=DruidDataSource,attribute=ConnectCount<br>
     * 初始连接数	com.alibaba.druid.pool:name=dataSource,type=DruidDataSource,attribute=InitialSize<br>
     * 最大的活跃连接数	com.alibaba.druid.pool:name=dataSource,type=DruidDataSource,attribute=MaxActive<br>
     * 提交数	com.alibaba.druid.pool:name=dataSource,type=DruidDataSource,attribute=CommitCount<br>
     * 等待获取连接的线程数	com.alibaba.druid.pool:name=dataSource,type=DruidDataSource,attribute=WaitThreadCount<br>
     * 阻塞等待线程数量<br>
     * 最大等待获取连接线程数<br>
     * 等待的消费者数量<br>
     * 连接池中数据库连接的数量<br>
     * 连接池峰值<br>
     *
     * @return gc信息
     */
    public DruidPoolMetric.Builder getDruidPoolMetrics() throws MalformedObjectNameException {
        DruidPoolMetric.Builder builder = DruidPoolMetric.newBuilder();
        ObjectName objectName = new ObjectName("com.alibaba.druid.pool:name=dataSource,type=DruidDataSource");
        boolean isRegistered = mBeanAttributeFetcher.isMBeanRegistered(objectName);
        if (!isRegistered){
            objectName = new ObjectName("com.alibaba.druid.spring.boot.autoconfigure:name=dataSource,type=DruidDataSourceWrapper");
        }
        Map<String, Object> result = mBeanAttributeFetcher.getAttributeValues(objectName,
                "PoolingCount","ActiveCount","ActivePeak","ConnectCount","InitialSize","MaxActive","CommitCount","WaitThreadCount");
        builder.setActiveCount(result.containsKey("ActiveCount") ? Integer.parseInt((result.get("ActiveCount").toString())) : 0)
                .setInitCount(result.containsKey("InitialSize") ? Integer.parseInt((result.get("InitialSize").toString())) : 0)
                .setMaxActive(result.containsKey("MaxActive") ? Integer.parseInt((result.get("MaxActive").toString())) : 0)
                .setCommitCount(result.containsKey("CommitCount") ? Integer.parseInt((result.get("CommitCount").toString())) : 0)
                .setActivePeak(result.containsKey("ActivePeak") ? Integer.parseInt((result.get("ActivePeak").toString())) : 0)
                .setConnectCount(result.containsKey("ConnectCount") ? Integer.parseInt((result.get("ConnectCount").toString())) : 0)
                .setPoolingCount(result.containsKey("PoolingCount") ? Integer.parseInt((result.get("PoolingCount").toString())) : 0)
                .setWaitThreadCount(result.containsKey("WaitThreadCount") ? Integer.parseInt((result.get("WaitThreadCount").toString())) : 0)
                .setIdleCount(builder.getPoolingCount() - builder.getActiveCount());
        return builder;
    }
}
