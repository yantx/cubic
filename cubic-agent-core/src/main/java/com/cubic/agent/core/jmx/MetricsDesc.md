### 操作系统（OperatingSystem）

| 序号 | 属性名称                     | 描述                         |
|------|-------------------------|----------------------------|
| 1    | OpenFileDescriptorCount   | 打开的文件描述符数量           |
| 2    | MaxFileDescriptorCount    | 最大文件描述符数量             |
| 3    | CommittedVirtualMemorySize | 承诺的虚拟内存大小           |
| 4    | TotalSwapSpaceSize        | 总交换空间大小               |
| 5    | FreeSwapSpaceSize         | 空闲交换空间大小               |
| 6    | ProcessCpuTime            | 进程CPU时间                   |
| 7    | FreePhysicalMemorySize    | 空闲物理内存大小               |
| 8    | TotalPhysicalMemorySize   | 总物理内存大小                 |
| 9    | SystemCpuLoad             | 系统CPU负载                   |
| 10   | ProcessCpuLoad            | 进程CPU负载                   |
| 11   | Name                     | 操作系统名称                   |
| 12   | Version                  | 操作系统版本                   |
| 13   | Arch                     | 操作系统架构                   |
| 14   | SystemLoadAverage         | 系统平均负载                   |
| 15   | AvailableProcessors       | 可用处理器数量                 |

### 内存（Memory）

| 序号 | 属性名称             | 描述         |
|------|------------------|------------|
| 1    | ObjectPendingFinalizationCount | 待最终化对象数量   |
| 2    | HeapMemoryUsage          | 堆内存使用情况     |
| 3    | NonHeapMemoryUsage       | 非堆内存使用情况     |

### 类加载（ClassLoading）

| 序号 | 属性名称             | 描述         |
|------|------------------|------------|
| 1    | TotalLoadedClassCount | 总加载类数量   |
| 2    | LoadedClassCount  | 当前加载类数量 |
| 3    | UnloadedClassCount | 卸载类数量    |

### 垃圾收集器（GarbageCollector）

#### PS Scavenge

| 序号 | 属性名称             | 描述         |
|------|------------------|------------|
| 1    | LastGcInfo       | 上次GC信息    |
| 2    | CollectionCount  | 收集次数     |
| 3    | CollectionTime   | 收集时间     |
| 4    | Name             | 名称         |
| 5    | MemoryPoolNames  | 内存池名称列表 |

#### PS MarkSweep

| 序号 | 属性名称             | 描述         |
|------|------------------|------------|
| 1    | LastGcInfo       | 上次GC信息    |
| 2    | CollectionCount  | 收集次数     |
| 3    | CollectionTime   | 收集时间     |
| 4    | Name             | 名称         |
| 5    | MemoryPoolNames  | 内存池名称列表 |

### 内存管理器（MemoryManager）

#### Metaspace Manager

| 序号 | 属性名称             | 描述         |
|------|------------------|------------|
| 1    | Name            | 名称         |
| 2    | MemoryPoolNames | 管理的内存池名称 |

#### CodeCacheManager

| 序号 | 属性名称             | 描述         |
|------|------------------|------------|
| 1    | Name            | 名称         |
| 2    | MemoryPoolNames | 管理的内存池名称 |

### 内存池（MemoryPool）

#### Metaspace

| 序号 | 属性名称                | 描述                             |
|------|----------------------|--------------------------------|
| 1    | Usage                | 表示当前Metaspace内存池的使用量       |
| 2    | PeakUsage            | 表示Metaspace内存池的峰值使用量       |
| 3    | MemoryManagerNames   | 表示管理这个内存池的内存管理器名称     |
| 4    | UsageThreshold       | 表示Metaspace内存池的使用阈值         |
| 5    | UsageThresholdExceeded | 表示Metaspace内存池的使用量是否超过了阈值 |
| 6    | UsageThresholdCount  | 表示Metaspace内存池的使用量超出阈值的次数 |
| 7    | UsageThresholdSupported | 表示是否支持设置使用阈值         |
| 8    | CollectionUsageThreshold | 表示垃圾收集使用阈值           |
| 9    | CollectionUsageThresholdExceeded | 表示垃圾收集使用量是否超过了阈值 |
| 10   | CollectionUsageThresholdCount | 表示垃圾收集使用量超出阈值的次数 |
| 11   | CollectionUsage      | 表示垃圾收集的使用量                 |
| 12   | CollectionUsageThresholdSupported | 表示是否支持设置垃圾收集使用阈值   |

#### Compressed Class Space

| 序号 | 属性名称                | 描述         |
|------|----------------------|------------|
| 1    | Name                 | 名称         |
| 2    | Type                 | 类型         |
| 3    | Usage                | 当前使用量    |
| 4    | PeakUsage            | 峰值使用量   |
| 5    | MemoryManagerNames   | 内存管理器名称  |
| 6    | UsageThreshold       | 使用阈值     |
| 7    | UsageThresholdExceeded | 使用阈值是否超出 |
| 8    | UsageThresholdCount  | 使用阈值超出次数 |
| 9    | UsageThresholdSupported | 是否支持使用阈值 |
| 10   | CollectionUsageThreshold | 收集使用阈值 |
| 11   | CollectionUsageThresholdExceeded | 收集使用阈值是否超出 |
| 12   | CollectionUsageThresholdCount | 收集使用阈值超出次数 |
| 13   | CollectionUsage      | 收集使用量    |
| 14   | CollectionUsageThresholdSupported | 是否支持收集使用阈值 |

#### PS Eden Space

| 序号 | 属性名称                | 描述         |
|------|----------------------|------------|
| 1    | Name                 | 名称         |
| 2    | Type                 | 类型         |
| 3    | Usage                | 当前使用量    |
| 4    | PeakUsage            | 峰值使用量   |
| 5    | MemoryManagerNames   | 内存管理器名称  |
| 6    | UsageThreshold       | 使用阈值     |
| 7    | UsageThresholdExceeded | 使用阈值是否超出 |
| 8    | UsageThresholdCount  | 使用阈值超出次数 |
| 9    | UsageThresholdSupported | 是否支持使用阈值 |
| 10   | CollectionUsageThreshold | 收集使用阈值 |
| 11   | CollectionUsageThresholdExceeded | 收集使用阈值是否超出 |
| 12   | CollectionUsageThresholdCount | 收集使用阈值超出次数 |
| 13   | CollectionUsage      | 收集使用量    |
| 14   | CollectionUsageThresholdSupported | 是否支持收集使用阈值 |

#### PS Survivor Space

| 序号 | 属性名称                | 描述         |
|------|----------------------|------------|
| 1    | Name                 | 名称         |
| 2    | Type                 | 类型         |
| 3    | Usage                | 当前使用量    |
| 4    | PeakUsage            | 峰值使用量   |
| 5    | MemoryManagerNames   | 内存管理器名称  |
| 6    | UsageThreshold       | 使用阈值     |
| 7    | UsageThresholdExceeded | 使用阈值是否超出 |
| 8    | UsageThresholdCount  | 使用阈值超出次数 |
| 9    | UsageThresholdSupported | 是否支持使用阈值 |
| 10   | CollectionUsageThreshold | 收集使用阈值 |
| 11   | CollectionUsageThresholdExceeded | 收集使用阈值是否超出 |
| 12   | CollectionUsageThresholdCount | 收集使用阈值超出次数 |
| 13   | CollectionUsage      | 收集使用量    |
| 14   | CollectionUsageThresholdSupported | 是否支持收集使用阈值 |

#### PS Old Gen

| 序号 | 属性名称                | 描述         |
|------|----------------------|------------|
| 1    | Name                 | 名称         |
| 2    | Type                 | 类型         |
| 3    | Usage                | 当前使用量    |
| 4    | PeakUsage            | 峰值使用量   |
| 5    | MemoryManagerNames   | 内存管理器名称  |
| 6    | UsageThreshold       | 使用阈值     |
| 7    | UsageThresholdExceeded | 使用阈值是否超出 |
| 8    | UsageThresholdCount  | 使用阈值超出次数 |
| 9    | UsageThresholdSupported | 是否支持使用阈值 |
| 10   | CollectionUsageThreshold | 收集使用阈值 |
| 11   | CollectionUsageThresholdExceeded | 收集使用阈值是否超出 |
| 12   | CollectionUsageThresholdCount | 收集使用阈值超出次数 |
| 13   | CollectionUsage      | 收集使用量    |
| 14   | CollectionUsageThresholdSupported | 是否支持收集使用阈值 |

#### Code Cache

| 序号 | 属性名称                | 描述         |
|------|----------------------|------------|
| 1    | Name                 | 名称         |
| 2    | Type                 | 类型         |
| 3    | Usage                | 当前使用量    |
| 4    | PeakUsage            | 峰值使用量   |
| 5    | MemoryManagerNames   | 内存管理器名称  |
| 6    | UsageThreshold       | 使用阈值     |
| 7    | UsageThresholdExceeded | 使用阈值是否超出 |
| 8    | UsageThresholdCount  | 使用阈值超出次数 |
| 9    | UsageThresholdSupported | 是否支持使用阈值 |
| 10   | CollectionUsageThreshold | 收集使用阈值 |
| 11   | CollectionUsageThresholdExceeded | 收集使用阈值是否超出 |
| 12   | CollectionUsageThresholdCount | 收集使用阈值超出次数 |
| 13   | CollectionUsage      | 收集使用量    |
| 14   | CollectionUsageThresholdSupported | 是否支持收集使用阈值 |

### 线程 (Threading)

| 序号 | 属性名称                     | 描述                         |
|------|-------------------------|----------------------------|
| 1    | CurrentThreadAllocatedBytes | 当前线程分配的字节数           |
| 2    | ThreadAllocatedMemoryEnabled | 线程分配内存监控是否启用       |
| 3    | ThreadAllocatedMemorySupported | 线程分配内存监控是否支持     |
| 4    | ThreadCount                | 当前线程数量                   |
| 5    | TotalStartedThreadCount     | 启动的线程总数                 |
| 6    | AllThreadIds              | 所有线程ID                    |
| 7    | ThreadContentionMonitoringEnabled | 线程争用监控是否启用       |
| 8    | CurrentThreadCpuTime       | 当前线程CPU时间                 |
| 9    | CurrentThreadUserTime      | 当前线程用户时间                 |
| 10   | ThreadCpuTimeSupported     | 线程CPU时间监控是否支持           |
| 11   | ThreadCpuTimeEnabled       | 线程CPU时间监控是否启用           |
| 12   | ThreadContentionMonitoringSupported | 线程争用监控是否支持   |
| 13   | CurrentThreadCpuTimeSupported | 当前线程CPU时间监控是否支持 |
| 14   | ObjectMonitorUsageSupported | 对象监视器使用情况监控是否支持   |
| 15   | SynchronizerUsageSupported | 同步器使用情况监控是否支持       |
| 16   | PeakThreadCount            | 峰值线程数量                   |
| 17   | DaemonThreadCount          | 守护线程数量                   |

### 运行时（Runtime）

| 序号 | 属性名称             | 描述         |
|------|------------------|------------|
| 1    | Name            | 名称         |
| 2    | ClassPath       | 类路径       |
| 3    | SpecName        | 规范名称     |
| 4    | SpecVendor      | 规范提供商   |
| 5    | SpecVersion     | 规范版本     |
| 6    | ManagementSpecVersion | 管理规范版本 |
| 7    | InputArguments   | 输入参数     |
| 8    | StartTime       | 启动时间     |
| 9    | BootClassPathSupported | 是否支持引导类路径 |
| 10   | VmName          | 虚拟机名称   |
| 11   | VmVendor        | 虚拟机提供商 |
| 12   | VmVersion       | 虚拟机版本   |
| 13   | LibraryPath     | 库路径       |
| 14   | BootClassPath   | 引导类路径   |
| 15   | Uptime          | 系统运行时间   |
| 16   | SystemProperties | 系统属性     |