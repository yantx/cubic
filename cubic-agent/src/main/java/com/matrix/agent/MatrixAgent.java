package com.matrix.agent;

import com.matrix.cubic.agent.core.AgentNettyClient;
import com.matrix.cubic.agent.core.conf.CubicConfInitalizer;

import java.lang.instrument.Instrumentation;

/**
 * @ClassName MatrixAgent
 * @Author QIANGLU
 * @Date 2020/5/14 4:40 下午
 * @Version 1.0
 */

public class MatrixAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("add agent");
        CubicConfInitalizer.initConfig();

//        instrumentation.addTransformer(new DefineTransformer(), true);

        AgentNettyClient client = new AgentNettyClient();
        client.start();

        Runtime.getRuntime()
                .addShutdownHook(new Thread(client::destroyAndSync, "cubic agent shutdown thread"));
    }


}
