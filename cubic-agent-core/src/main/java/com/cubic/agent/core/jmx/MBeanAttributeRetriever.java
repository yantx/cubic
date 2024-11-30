/**
 * MBeanAttributeFetcher is responsible for fetching attributes from MBeans
 * registered in the MBeanServer. It provides methods to retrieve attribute values,
 * query MBean names, and get attribute information.
 */
package com.cubic.agent.core.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MBeanAttributeRetriever {
    private static final Logger logger = LoggerFactory.getLogger(MBeanAttributeRetriever.class);
    private final MBeanServer mBeanServer;

    private static volatile MBeanAttributeRetriever instance;

    private MBeanAttributeRetriever(MBeanServer mBeanServer) {
        this.mBeanServer = mBeanServer;
    }

    public static MBeanAttributeRetriever getInstance() {
        if (instance == null) {
            synchronized (MBeanAttributeRetriever.class) {
                if (instance == null) {
                    instance = new MBeanAttributeRetriever(ManagementFactory.getPlatformMBeanServer());
                }
            }
        }
        return instance;
    }
    public boolean isMBeanRegistered(ObjectName objectName) {
        return mBeanServer.isRegistered(objectName);
    }
    public Map<String, Object> getAttributeValues(ObjectName objectName, String... attributeNames) {
        Map<String, Object> result = Collections.emptyMap();
        for (String attributeName : attributeNames) {
            try {
                if (mBeanServer.isRegistered(objectName)) {
                    if (result.isEmpty()) {
                        result = new HashMap<>();
                    }
                    Object attributeValue = mBeanServer.getAttribute(objectName, attributeName);
                    if (attributeValue instanceof CompositeData) {
                        // 如果是 CompositeData，提取值
                        CompositeData compositeData = (CompositeData) attributeValue;
                        for (String key : compositeData.getCompositeType().keySet()) {
                            result.put(key, compositeData.get(key));
                        }
                    } else {
                        // 如果不是 CompositeData，直接返回值
                        result.put(attributeName, attributeValue);
                    }
                } else {
                    logger.warn("MBean not registered: {}", objectName);
                }
            } catch (Exception e) {
                logger.error("Error getting attribute value for {}, attribute: {}", objectName, attributeName, e);
            }
        }
        return result;
    }

    public Set<ObjectName> queryNames(String domain, String key) {
        try {
            return mBeanServer.queryNames(new ObjectName(domain + ":*"), null);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
    }

    public MBeanAttributeInfo[] getAttributeInfos(ObjectName mbeanName) {
        try {
            MBeanAttributeInfo[] attributeInfos = mBeanServer.getMBeanInfo(mbeanName).getAttributes();
            return attributeInfos;
        } catch (ReflectionException | InstanceNotFoundException | IntrospectionException e) {
            logger.error("Error getting attribute names for domain: {}", mbeanName.getDomain(), e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws MalformedObjectNameException {
        MBeanAttributeRetriever mBeanAttributeFetcher = MBeanAttributeRetriever.getInstance();
        // 查询所有MBean 以及属性
//        Set<ObjectName> objectNames = mBeanAttributeFetcher.queryNames("java.lang", null);
//        objectNames.forEach(objectName -> {
//            System.out.println(objectName);
//            MBeanAttributeInfo[] attributeInfos = mBeanAttributeFetcher.getAttributeInfos(objectName);
//            for (MBeanAttributeInfo attributeInfo : attributeInfos) {
//                System.out.println(objectName + ", attribute=" + attributeInfo.getName() + ", description=" + attributeInfo.getDescription());
//            }
//        });
        Map<String, Object> result = mBeanAttributeFetcher
                .getAttributeValues(new ObjectName("java.lang:type=GarbageCollector,name=PS Scavenge"), "CollectionCount");
        result.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    /**
     *java.lang:type=MemoryPool,name=Metaspace
     *Name：表示内存池的名称，这里是Metaspace。
     *Type：表示内存池的类型。
     *Usage：表示当前Metaspace内存池的使用量。
     *PeakUsage：表示Metaspace内存池的峰值使用量。
     *MemoryManagerNames：表示管理这个内存池的内存管理器名称。
     *UsageThreshold：表示Metaspace内存池的使用阈值。
     *UsageThresholdExceeded：表示Metaspace内存池的使用量是否超过了阈值。
     *UsageThresholdCount：表示Metaspace内存池的使用量超出阈值的次数。
     *UsageThresholdSupported：表示是否支持设置使用阈值。
     *CollectionUsageThreshold：表示垃圾收集使用阈值。
     *CollectionUsageThresholdExceeded：表示垃圾收集使用量是否超过了阈值。
     *CollectionUsageThresholdCount：表示垃圾收集使用量超出阈值的次数。
     *CollectionUsage：表示垃圾收集的使用量。
     *CollectionUsageThresholdSupported：表示是否支持设置垃圾收集使用阈值。
     *Valid：表示这个内存池是否有效。
     *ObjectName：表示这个内存池的对象名称。
     *<br>
     *java.lang:type=MemoryPool,name=PS Old Gen
     *Usage：表示当前“PS Old Gen”内存池的使用量。
     *PeakUsage：表示“PS Old Gen”内存池的峰值使用量。
     *MemoryManagerNames：表示管理这个内存池的内存管理器名称。
     *UsageThreshold：表示“PS Old Gen”内存池的使用阈值。
     *UsageThresholdExceeded：表示“PS Old Gen”内存池的使用量是否超过了阈值。
     *UsageThresholdCount：表示“PS Old Gen”内存池的使用量超出阈值的次数。
     *UsageThresholdSupported：表示是否支持设置使用阈值。
     *CollectionUsageThreshold：表示垃圾收集使用阈值。
     *CollectionUsageThresholdExceeded：表示垃圾收集使用量是否超过了阈值。
     *CollectionUsageThresholdCount：表示垃圾收集使用量超出阈值的次数。
     *CollectionUsage：表示垃圾收集的使用量。
     *CollectionUsageThresholdSupported：表示是否支持设置垃圾收集使用阈值。
     *<br>
     *java.lang:type=GarbageCollector,name=PS Scavenge
     *LastGcInfo：表示“PS Scavenge”垃圾收集器最后一次垃圾回收的详细信息。
     *CollectionCount：表示“PS Scavenge”垃圾收集器执行的垃圾回收次数。
     *CollectionTime：表示“PS Scavenge”垃圾收集器执行垃圾回收所花费的总时间。
     *Name：表示垃圾收集器的名称，这里是“PS Scavenge”。
     *MemoryPoolNames：表示这个垃圾收集器管理的内存池的名称列表。
     *<br>
     *java.lang:type=MemoryPool,name=PS Eden Space
     *Usage：表示当前“PS Eden Space”内存池的使用量。
     *PeakUsage：表示“PS Eden Space”内存池的峰值使用量。
     *MemoryManagerNames：表示管理这个内存池的内存管理器名称。
     *UsageThreshold：表示“PS Eden Space”内存池的使用阈值。
     *UsageThresholdExceeded：表示“PS Eden Space”内存池的使用量是否超过了阈值。
     *UsageThresholdCount：表示“PS Eden Space”内存池的使用量超出阈值的次数。
     *UsageThresholdSupported：表示是否支持设置使用阈值。
     *CollectionUsageThreshold：表示垃圾收集使用阈值。
     *CollectionUsageThresholdExceeded：表示垃圾收集使用量是否超过了阈值。
     *CollectionUsageThresholdCount：表示垃圾收集使用量超出阈值的次数。
     *CollectionUsage：表示垃圾收集的使用量。
     *CollectionUsageThresholdSupported：表示是否支持设置垃圾收集使用阈值。
     *<br>
     *java.lang:type=Runtime
     *Name：表示Java运行时环境的名称。
     *ClassPath：表示Java类加载器搜索类的路径。
     *SpecName：表示Java虚拟机规范的名称。
     *SpecVendor：表示Java虚拟机规范的提供商。
     *SpecVersion：表示Java虚拟机规范的版本。
     *ManagementSpecVersion：表示Java虚拟机管理规范的版本。
     *InputArguments：表示启动Java虚拟机时的输入参数。
     *StartTime：表示Java虚拟机的启动时间。
     *BootClassPathSupported：表示是否支持设置引导类路径。
     *VmName：表示Java虚拟机的名称。
     *VmVendor：表示Java虚拟机的提供商。
     *VmVersion：表示Java虚拟机的版本。
     *LibraryPath：表示Java虚拟机加载库文件的路径。
     *BootClassPath：表示Java虚拟机启动时搜索加载类的路径。
     *Uptime：表示Java虚拟机运行的总时间。
     *SystemProperties：表示Java系统属性。
     *<br>
     *java.lang:type=Threading
     *CurrentThreadAllocatedBytes：表示当前线程分配的字节数。
     *ThreadAllocatedMemoryEnabled：表示是否启用了线程分配内存监控。
     *ThreadAllocatedMemorySupported：表示是否支持线程分配内存监控。
     *ThreadCount：表示当前活动的线程数量。
     *TotalStartedThreadCount：表示启动的线程总数。
     *AllThreadIds：表示所有线程的ID。
     *ThreadContentionMonitoringEnabled：表示是否启用了线程争用监控。
     *CurrentThreadCpuTime：表示当前线程的CPU时间。
     *CurrentThreadUserTime：表示当前线程的用户时间。
     *ThreadCpuTimeSupported：表示是否支持线程CPU时间监控。
     *ThreadCpuTimeEnabled：表示是否启用了线程CPU时间监控。
     *ThreadContentionMonitoringSupported：表示是否支持线程争用监控。
     *CurrentThreadCpuTimeSupported：表示是否支持当前线程CPU时间监控。
     *ObjectMonitorUsageSupported：表示是否支持对象监视器使用情况监控。
     *SynchronizerUsageSupported：表示是否支持同步器使用情况监控。
     *PeakThreadCount：表示达到的峰值线程数量。
     *DaemonThreadCount：表示守护线程的数量。
     *ObjectName：表示线程相关的管理对象的名称。
     * <br>
     *java.lang:type=OperatingSystem
     *OpenFileDescriptorCount：表示当前打开的文件描述符数量。
     *MaxFileDescriptorCount：表示系统允许的最大文件描述符数量。
     *CommittedVirtualMemorySize：表示Java虚拟机进程承诺使用的虚拟内存大小，即虚拟内存中已分配但尚未使用或被实际数据填充的部分。
     *TotalSwapSpaceSize：表示总的交换空间大小，即磁盘上用于虚拟内存的那部分空间。
     *FreeSwapSpaceSize：表示空闲的交换空间大小。
     *ProcessCpuTime：表示Java虚拟机进程使用的CPU时间。
     *FreePhysicalMemorySize：表示空闲的物理内存大小。
     *TotalPhysicalMemorySize：表示总的物理内存大小。
     *SystemCpuLoad：表示系统CPU负载。
     *ProcessCpuLoad：表示Java虚拟机进程的CPU负载。
     *Name：表示操作系统的名称。
     *Version：表示操作系统的版本。
     *Arch：表示操作系统的架构，例如x86或x64。
     *SystemLoadAverage：表示系统的平均负载，即在某个时间窗口内，系统需要处理的平均进程数。
     *AvailableProcessors：表示可用的处理器数量。
     *<br>
     *java.lang:type=MemoryPool,name=Code Cache
     *Usage：表示当前“Code Cache”内存池的使用量。
     *PeakUsage：表示“Code Cache”内存池的峰值使用量。
     *MemoryManagerNames：表示管理这个内存池的内存管理器名称。
     *UsageThreshold：表示“Code Cache”内存池的使用阈值。
     *UsageThresholdExceeded：表示“Code Cache”内存池的使用量是否超过了阈值。
     *UsageThresholdCount：表示“Code Cache”内存池的使用量超出阈值的次数。
     *UsageThresholdSupported：表示是否支持设置使用阈值。
     *CollectionUsageThreshold：表示垃圾收集使用阈值。
     *CollectionUsageThresholdExceeded：表示垃圾收集使用量是否超过了阈值。
     *CollectionUsageThresholdCount：表示垃圾收集使用量超出阈值的次数。
     *CollectionUsage：表示垃圾收集的使用量。
     *CollectionUsageThresholdSupported：表示是否支持设置垃圾收集使用阈值。
     *<br>
     *java.lang:type=Compilation
     *TotalCompilationTime：表示Java虚拟机启动以来，JIT编译器编译方法所花费的总时间。
     *CompilationTimeMonitoringSupported：表示是否支持监控编译时间。
     *<br>
     *java.lang:type=MemoryManager,name=CodeCacheManager
     *MemoryPoolNames：表示这个内存管理器管理的内存池的名称列表。
     *<br>
     *java.lang:type=MemoryPool,name=Compressed Class Space
     *Usage：表示当前“Compressed Class Space”内存池的使用量。
     *PeakUsage：表示“Compressed Class Space”内存池的峰值使用量。
     *MemoryManagerNames：表示管理这个内存池的内存管理器名称。
     *UsageThreshold：表示“Compressed Class Space”内存池的使用阈值。
     *UsageThresholdExceeded：表示“Compressed Class Space”内存池的使用量是否超过了阈值。
     *UsageThresholdCount：表示“Compressed Class Space”内存池的使用量超出阈值的次数。
     *UsageThresholdSupported：表示是否支持设置使用阈值。
     *CollectionUsageThreshold：表示垃圾收集使用阈值。
     *CollectionUsageThresholdExceeded：表示垃圾收集使用量是否超过了阈值。
     *CollectionUsageThresholdCount：表示垃圾收集使用量超出阈值的次数。
     *CollectionUsage：表示垃圾收集的使用量。
     *CollectionUsageThresholdSupported：表示是否支持设置垃圾收集使用阈值。
     *<br>
     *java.lang:type=Memory
     *ObjectPendingFinalizationCount：表示等待最终化（finalize）的对象数量。
     *HeapMemoryUsage：表示堆内存的使用情况，包括初始化、已用、空闲和最大内存。
     *NonHeapMemoryUsage：表示非堆内存的使用情况，包括初始化、已用、空闲和最大内存。
     *<br>
     *java.lang:type=MemoryPool,name=PS Survivor Space
     *Usage：表示当前“PS Survivor Space”内存池的使用量。
     *PeakUsage：表示“PS Survivor Space”内存池的峰值使用量。
     *MemoryManagerNames：表示管理这个内存池的内存管理器名称。
     *UsageThreshold：表示“PS Survivor Space”内存池的使用阈值。
     *UsageThresholdExceeded：表示“PS Survivor Space”内存池的使用量是否超过了阈值。
     *UsageThresholdCount：表示“PS Survivor Space”内存池的使用量超出阈值的次数。
     *UsageThresholdSupported：表示是否支持设置使用阈值。
     *CollectionUsageThreshold：表示垃圾收集使用阈值。
     *CollectionUsageThresholdExceeded：表示垃圾收集使用量是否超过了阈值。
     *CollectionUsageThresholdCount：表示垃圾收集使用量超出阈值的次数。
     *CollectionUsage：表示垃圾收集的使用量。
     *CollectionUsageThresholdSupported：表示是否支持设置垃圾收集使用阈值。
     *<br>
     *java.lang:type=ClassLoading
     *TotalLoadedClassCount：表示Java虚拟机启动以来总共加载的类的数量。
     *LoadedClassCount：表示当前被加载的类的数量。
     *UnloadedClassCount：表示已经被卸载的类的数量。
     *<br>
     *java.lang:type=MemoryManager,name=Metaspace Manager
     *MemoryPoolNames：表示这个内存管理器管理的内存池的名称列表，通常包括“Metaspace”和“Compressed Class Space”.
     *<br>
     *java.lang:type=GarbageCollector,name=PS MarkSweep
     *LastGcInfo：表示“PS MarkSweep”垃圾收集器最后一次垃圾回收的详细信息，包括回收开始和结束时间、耗时等。
     *CollectionCount：表示“PS MarkSweep”垃圾收集器执行的垃圾回收次数。
     *CollectionTime：表示“PS MarkSweep”垃圾收集器执行垃圾回收所花费的总时间。
     *Name：表示垃圾收集器的名称，这里是“PS MarkSweep”。
     *MemoryPoolNames：表示这个垃圾收集器管理的内存池的名称列表，通常包括“PS Old Gen”。
     */
}
