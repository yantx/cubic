package com.matrix.proxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matrix.proxy.entity.Information;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName BasicInformationRepository
 * @Author QIANGLU
 * @Date 2020/6/10 11:22 上午
 * @Version 1.0
 */
public interface InformationMapper extends BaseMapper<Information> {


    /**
     * 根据lastHeartbeat查询应用实例列表信息
     *
     * @param lastHeartbeat
     * @return
     */
    List<Information> selectInstanceByLastHeartbeat(LocalDateTime lastHeartbeat);


    /**
     * 根据appId查询应用实例信息
     *
     * @param appId
     * @return
     */
    Information selectInstanceByAppId(String appId);


    /**
     * 根据appId查询jars信息
     *
     * @param appId
     * @return
     */
    Information selectJarsByAppId(String appId);

    /**
     * 根据应用名称查询应用存活实例
     *
     * @param instanceName
     * @param date
     * @return
     */
    List<String> selectInstanceIdByName(String instanceName, LocalDateTime date);
}
