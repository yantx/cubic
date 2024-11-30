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

import com.cubic.agent.core.boot.CommonService;
import com.cubic.agent.core.boot.DefaultService;
import com.cubic.agent.core.boot.ServiceManager;
import com.cubic.agent.core.cmd.jvm.thread.RunnableWithExceptionProtection;
import com.cubic.agent.core.conf.AgentConfig;
import com.cubic.agent.core.remote.*;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.cubic.serialization.agent.v1.DruidPoolMetric;
import com.cubic.serialization.agent.v1.JVMGCMetric;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * JVM线程栈信息收集发送处理
 *
 * @Author yantx
 * @Date 2020/12/08
 * @Version V1.0
 **/
@DefaultService
public class DruidPoolService implements CommonService {

    private static final Logger logger = LoggerFactory.getLogger(DruidPoolService.class);

    private volatile ScheduledFuture<?> sendMetricFuture;
    private Sender sender;
    private volatile AgentNettyClient client;

    @Override
    public void prepare() {
        sender = new Sender();
        ServiceManager.INSTANCE.findService(AgentClientManager.class).addListener(sender);
    }

    @Override
    public void start() {
        sendMetricFuture = new ScheduledThreadPoolExecutor(1, new DefaultThreadFactory("DruidPoolService-"))
                .scheduleAtFixedRate(new RunnableWithExceptionProtection(sender, th -> {
            logger.error("DruidPoolService collect and upload failure.", th);
        }), 0, 1, TimeUnit.MINUTES);
    }

    @Override
    public void shutdown() {
        sendMetricFuture.cancel(true);
    }

    @Override
    public void complete() {

    }

    class Sender implements Runnable, AgentChannelListener {
        private volatile ChannelStatus status = ChannelStatus.DISCONNECT;

        @Override
        public void run() {
            if (status == ChannelStatus.CONNECTION) {
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    DruidPoolMetric.Builder builder = DruidProvider.INSTANCE.getDruidPoolMetrics();
                    builder.setTime(currentTimeMillis);
                    builder.setServiceName(AgentConfig.Agent.SERVICE_NAME);
                    CommonMessage.Builder message = CommonMessage.newBuilder();
                    message.setCode(CommandCode.DRUID_POOL.getCode());
                    message.setBody("druid pool");
                    message.setId("0000");
                    message.setInstanceName(AgentConfig.Agent.SERVICE_NAME);
                    message.setInstanceUuid(AgentConfig.Agent.INSTANCE_UUID);
                    message.setInstanceVersion(AgentConfig.Agent.VERSION);
                    message.setMsgContent(builder.build().toByteString());
                    client.getChannel().writeAndFlush(message.build()).addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            logger.debug("DruidPoolService send successful");
                        } else {
                            logger.error("DruidPoolService send {} fail", AgentConfig.Agent.INSTANCE_UUID);
                        }
                    });
                } catch (Throwable t) {
                    logger.error("send druid pool metrics to Collector fail.", t);
                }
            }
        }

        @Override
        public void statusChanged(ChannelStatus status) {
            if (ChannelStatus.CONNECTION.equals(status)) {
                client = ServiceManager.INSTANCE.findService(AgentClientManager.class).getClient();
            }
            this.status = status;
        }
    }
}
