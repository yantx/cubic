package com.matrix.proxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cubic.proxy.common.module.DataResult;
import com.cubic.proxy.common.thread.parser.DumpParserFactory;
import com.matrix.proxy.entity.ThreadDump;
import com.matrix.proxy.mapper.ThreadDumpMapper;
import com.matrix.proxy.util.GzipUtils;
import com.matrix.proxy.vo.ThreadDumpVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 处理线程栈信息展示
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
@Service
public class ThreadDumpServiceImpl implements ThreadDumpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadDumpServiceImpl.class);

    @Resource
    private ThreadDumpMapper threadDumpMapper;


    /**
     * 获取应用实例线程栈信息
     *
     * @param time
     * @param appId
     * @return
     */
    @Override
    public String getThreadDumpByAppId(String appId, String time) {
        QueryWrapper<ThreadDump> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id", appId).apply("to_char(create_time,'YYYY-MM-DD HH24:MI') = '" + time + " '");
        ThreadDump threadDump = threadDumpMapper.selectOne(wrapper);
        return threadDump == null ? "" : GzipUtils.decompress(new String(Base64.getDecoder().decode(threadDump.getThreadDump()), StandardCharsets.ISO_8859_1));
    }

    /**
     * 获取应用线程栈历史信息
     *
     * @return
     */
    @Override
    public DataResult getHistoryByAppId(ThreadDumpVo dumpVo) {

        Page<ThreadDump> page = new Page<>(dumpVo.getPageNum(), dumpVo.getPageSize());

        QueryWrapper<ThreadDump> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id", dumpVo.getAppId());
        wrapper.select("app_id", "instance_name", "instance_id", "create_time", "thread_dump");
        wrapper.orderByDesc("create_time");

        IPage<ThreadDump> datas = threadDumpMapper.selectPage(page, wrapper);
        if (CollectionUtils.isEmpty(datas.getRecords())) {
            return DataResult.success();
        }
        List<ThreadDumpVo> result = new ArrayList<>();
        BeanCopier copier = BeanCopier.create(ThreadDump.class, ThreadDumpVo.class, false);

        datas.getRecords().forEach(user -> {
            ThreadDumpVo dto = new ThreadDumpVo();
            copier.copy(user, dto, null);
            dto.setThreadDump(GzipUtils.decompress(new String(Base64.getDecoder().decode(user.getThreadDump()), StandardCharsets.ISO_8859_1)));
            result.add(dto);
        });

        return DataResult.success(result, datas.getTotal());

    }


    /**
     * 解析线程栈数据
     *
     * @param dumpId 线程栈ID
     * @return
     */
    @Override
    public Map analyzer(Long dumpId) {
        ThreadDump threadDump = threadDumpMapper.selectById(dumpId);

        if (threadDump == null) {
            return new HashMap();
        }
        String dumpFile = GzipUtils.decompress(new String(Base64.getDecoder().decode(threadDump.getThreadDump()), StandardCharsets.ISO_8859_1));
       return DumpParserFactory.get().getDumpParser(dumpFile);
    }


}
