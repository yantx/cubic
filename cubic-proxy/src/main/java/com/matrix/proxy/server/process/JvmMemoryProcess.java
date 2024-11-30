package com.matrix.proxy.server.process;

import com.cubic.proxy.common.constant.CommandCode;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.cubic.serialization.agent.v1.JVMMemoryMetric;
import com.cubic.serialization.agent.v1.JVMThreadMetric;
import com.google.protobuf.InvalidProtocolBufferException;
import com.matrix.proxy.entity.ThreadDump;
import com.matrix.proxy.mapper.ThreadDumpMapper;
import com.matrix.proxy.util.GzipUtils;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * 接受app jvm内存信息并处理
 *
 * @Author yantx
 * @Date 2020/11/24
 * @Version V1.0
 **/
@Service
public class JvmMemoryProcess extends DefaultMessageProcess{

	private static final Logger logger = LoggerFactory.getLogger(JvmMemoryProcess.class);

	@Resource
	private ServerConnectionStore connectionStore;
	@Resource
	private ThreadDumpMapper threadDumpMapper;

	public JvmMemoryProcess() {
	}

	@Override
	public Integer code() {
		return CommandCode.JVM_MEMORY.getCode();
	}

	@Override
	public void process(ChannelHandlerContext ctx, CommonMessage message) {
		JVMMemoryMetric memoryMetric;
		try {
			memoryMetric = JVMMemoryMetric.parseFrom(message.getMsgContent());
		} catch (InvalidProtocolBufferException e) {
			logger.error("CommonMessage 反序列化失败：", e);
			return;
		}
		String appId = message.getInstanceName() + "_" + message.getInstanceUuid();
		connectionStore.register(appId, ctx.channel());
		// 数据持久化
		insertMemoryInfo(memoryMetric, appId);
		logger.debug("MemoryMetrics insert success！appId：{} ,channel :{}", appId, ctx.channel());
	}

	private void insertMemoryInfo(JVMMemoryMetric memoryMetric, String appId) {
		// TODO 数据持久化 待实现
	}
}
