package com.matrix.proxy.service;
import com.matrix.proxy.entity.RelyInformation;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JarController
 * @Author 李家山竹
 * @Date 2021/4/23 9:16 下午
 * @Version 1.0
 */
public interface JarService {
    /**
     * 获取应用JAR列表信息
     * @return
     */
    public Map<String, List<RelyInformation>> getJarList(String id);
}
