package com.matrix.proxy.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 线程池查询条件
 *
 * @author zhanghao
 * @date 2021/4/75:56 下午
 */
@Data
public class ThreadPoolQueryVo {

    int pageNo = 1;

    int pageSize = 20;

    private String instanceId;

    private String instanceName;

    private String threadPoolKey;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
