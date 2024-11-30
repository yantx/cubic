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

package com.cubic.agent.core.jmx.jvm.gc;

import com.cubic.agent.core.jmx.MBeanAttributeRetriever;
import com.cubic.serialization.agent.v1.JVMGCMetric;
import com.cubic.serialization.agent.v1.JVMMemoryMetric;

import javax.management.MBeanInfo;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * jvm线程栈采集
 *
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
public enum GCProvider {

    INSTANCE;

    private final List<GarbageCollectorMXBean> garbageCollectorMXBeans;

    GCProvider() {
        this.garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
    }

    /**
     * 获取GC详细信息
     *
     * @return gc信息
     */
    public List<JVMGCMetric.Builder> getJVMGCMetrics() {
        List<JVMGCMetric.Builder> builders = new ArrayList<>();
        JVMGCMetric.Builder builder = JVMGCMetric.newBuilder();
        for (GarbageCollectorMXBean gcMXBean : garbageCollectorMXBeans) {
            builder.setCollectionCount(gcMXBean.getCollectionCount())
                    .setCollectionTime(gcMXBean.getCollectionTime())
                    .setName(gcMXBean.getName());
            builders.add(builder);
        }
        return builders;
    }
}
