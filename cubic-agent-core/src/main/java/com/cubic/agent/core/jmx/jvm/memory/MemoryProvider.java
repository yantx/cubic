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

package com.cubic.agent.core.jmx.jvm.memory;

import com.cubic.agent.core.cmd.jstack.CommandExecutor;
import com.cubic.agent.core.jmx.MBeanAttributeRetriever;
import com.cubic.serialization.agent.v1.JVMMemoryMetric;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Map;

/**
 * jvm线程栈采集
 *
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
public enum MemoryProvider {

    INSTANCE;

    private final MBeanAttributeRetriever mBeanAttributeRetriever;

    MemoryProvider() {
        this.mBeanAttributeRetriever = MBeanAttributeRetriever.getInstance();
    }

    /**
     * 获取内存详细信息
     *
     * @return 内存信息
     */
    public JVMMemoryMetric.Builder getJVMMemory() throws MalformedObjectNameException {
        JVMMemoryMetric.Builder builder = JVMMemoryMetric.newBuilder();
        getJVMHeadMemory(builder);
        getJVMNonHeadMemory(builder);
        return builder;
    }

    private void getJVMHeadMemory(JVMMemoryMetric.Builder builder) throws MalformedObjectNameException {
        Map<String, Object> heapMemoryUsage = mBeanAttributeRetriever.getAttributeValues(new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage");
        builder.setHeapCommitted((Long) heapMemoryUsage.get("committed"))
                .setHeapMax((Long) heapMemoryUsage.get("max"))
                .setHeapUsed((Long) heapMemoryUsage.get("used"))
                .setHeapInit((Long) heapMemoryUsage.get("init"))
                .setHeapFree(builder.getHeapMax() - builder.getHeapUsed());
    }

    private void getJVMNonHeadMemory(JVMMemoryMetric.Builder builder) throws MalformedObjectNameException {
        Map<String, Object> nonHeapMemoryUsage = mBeanAttributeRetriever.getAttributeValues(new ObjectName("java.lang:type=Memory"), "NonHeapMemoryUsage");
        builder.setNonHeapCommitted(((Long) nonHeapMemoryUsage.get("committed")))
                .setNoHeapMax((Long) nonHeapMemoryUsage.get("max"))
                .setNonHeapUsed((Long) nonHeapMemoryUsage.get("used"))
                .setNonHeapInit((Long) nonHeapMemoryUsage.get("init"))
                .setNonHeapFree(builder.getHeapMax() - builder.getHeapUsed());
    }
}
