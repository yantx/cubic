package com.matrix.proxy.server.process;

import com.cubic.proxy.common.constant.CommandCode;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.cubic.serialization.agent.v1.JVMGCMetric;
import com.cubic.serialization.agent.v1.JVMMemoryMetric;
import com.google.protobuf.InvalidProtocolBufferException;
import com.matrix.proxy.mapper.ThreadDumpMapper;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 接受app JVM垃圾回收并处理
 *
 * @Author yantx
 * @Date 2020/11/26
 * @Version V1.0
 **/
@Service
public class JvmGCProcess extends DefaultMessageProcess{

	private static final Logger logger = LoggerFactory.getLogger(JvmGCProcess.class);

	@Resource
	private ServerConnectionStore connectionStore;
	@Resource
	private ThreadDumpMapper threadDumpMapper;

	public JvmGCProcess() {
	}

	@Override
	public Integer code() {
		return CommandCode.JVM_GC.getCode();
	}

	@Override
	public void process(ChannelHandlerContext ctx, CommonMessage message) {
		JVMGCMetric jvmgcMetric;
		try {
			jvmgcMetric = JVMGCMetric.parseFrom(message.getMsgContent());
		} catch (InvalidProtocolBufferException e) {
			logger.error("CommonMessage 反序列化失败：", e);
			return;
		}
		String appId = message.getInstanceName() + "_" + message.getInstanceUuid();
		connectionStore.register(appId, ctx.channel());
		// 数据持久化
		insertGCInfo(jvmgcMetric, appId);
		logger.debug("GCMetrics insert success！appId：{} ,channel :{}", appId, ctx.channel());
	}

	private void insertGCInfo(JVMGCMetric jvmgcMetric, String appId) {
		// TODO 数据持久化 待实现
	}
}
