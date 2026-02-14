package com.example;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.management.JMX;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.example.management.FileSystemMXBean;
import com.sun.management.OperatingSystemMXBean;

import jdk.jfr.consumer.RecordedEvent;
import jdk.management.jfr.EventTypeInfo;
import jdk.management.jfr.FlightRecorderMXBean;
import jdk.management.jfr.RemoteRecordingStream;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        for (String arg: args) {
            try (
                JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(arg));
                RemoteRecordingStream stream = new RemoteRecordingStream(connector.getMBeanServerConnection());
            ) {
                ThreadMXBean thread = JMX.newMXBeanProxy(
                    connector.getMBeanServerConnection(),
                    new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME),
                    ThreadMXBean.class
                );
                System.out.print("ThreadMXBean.getThreadCount(): ");
                System.out.println(thread.getThreadCount());
                System.out.print("ThreadMXBean.getPeakThreadCount(): ");
                System.out.println(thread.getPeakThreadCount());
                System.out.print("ThreadMXBean.getTotalStartedThreadCount(): ");
                System.out.println(thread.getTotalStartedThreadCount());
                System.out.print("ThreadMXBean.getDaemonThreadCount(): ");
                System.out.println(thread.getDaemonThreadCount());
                System.out.print("ThreadMXBean.getCurrentThreadCpuTime(): ");
                System.out.println(thread.getCurrentThreadCpuTime());
                System.out.print("ThreadMXBean.getCurrentThreadUserTime(): ");
                System.out.println(thread.getCurrentThreadUserTime());
                long[] ids = thread.getAllThreadIds();
                for (long id: ids) {
                    System.out.print(String.format("ThreadMXBean.getThreadCpuTime(%d): ", id));
                    System.out.println(thread.getThreadCpuTime(id));
                    System.out.print(String.format("ThreadMXBean.getThreadUserTime(%d): ", id));
                    System.out.println(thread.getThreadUserTime(id));
                    ThreadInfo info = thread.getThreadInfo(id);
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getThreadId(): ", id));
                    System.out.println(info.getThreadId());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getThreadName(): ", id));
                    System.out.println(info.getThreadName());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getThreadState(): ", id));
                    System.out.println(info.getThreadState());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getBlockedTime(): ", id));
                    System.out.println(info.getBlockedTime());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getBlockedCount(): ", id));
                    System.out.println(info.getBlockedCount());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getWaitedTime(): ", id));
                    System.out.println(info.getWaitedTime());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getWaitedCount(): ", id));
                    System.out.println(info.getWaitedCount());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getLockInfo(): ", id));
                    System.out.println(info.getLockInfo());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getLockName(): ", id));
                    System.out.println(info.getLockName());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getLockOwnerId(): ", id));
                    System.out.println(info.getLockOwnerId());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getLockOwnerName(): ", id));
                    System.out.println(info.getLockOwnerName());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getStackTrace(): ", id));
                    System.out.println(info.getStackTrace());
                    System.out.print(String.format("ThreadMXBean.getThreadInfo(%d).getPriority(): ", id));
                    System.out.println(info.getPriority());
                }

                MemoryMXBean memory = JMX.newMXBeanProxy(
                    connector.getMBeanServerConnection(),
                    new ObjectName(ManagementFactory.MEMORY_MXBEAN_NAME),
                    MemoryMXBean.class
                );
                System.out.print("MemoryMXBean.getHeapMemoryUsage().getInit(): ");
                System.out.println(memory.getHeapMemoryUsage().getInit());
                System.out.print("MemoryMXBean.getHeapMemoryUsage().getUsed(): ");
                System.out.println(memory.getHeapMemoryUsage().getUsed());
                System.out.print("MemoryMXBean.getHeapMemoryUsage().getCommitted(): ");
                System.out.println(memory.getHeapMemoryUsage().getCommitted());
                System.out.print("MemoryMXBean.getHeapMemoryUsage().getMax(): ");
                System.out.println(memory.getHeapMemoryUsage().getMax());
                System.out.print("MemoryMXBean.getNonHeapMemoryUsage().getInit(): ");
                System.out.println(memory.getNonHeapMemoryUsage().getInit());
                System.out.print("MemoryMXBean.getNonHeapMemoryUsage().getUsed(): ");
                System.out.println(memory.getNonHeapMemoryUsage().getUsed());
                System.out.print("MemoryMXBean.getNonHeapMemoryUsage().getCommitted(): ");
                System.out.println(memory.getNonHeapMemoryUsage().getCommitted());
                System.out.print("MemoryMXBean.getNonHeapMemoryUsage().getMax(): ");
                System.out.println(memory.getNonHeapMemoryUsage().getMax());

                RuntimeMXBean runtime = JMX.newMXBeanProxy(
                    connector.getMBeanServerConnection(),
                    new ObjectName(ManagementFactory.RUNTIME_MXBEAN_NAME),
                    RuntimeMXBean.class
                );
                if (runtime.isBootClassPathSupported()) {
                    System.out.print("RuntimeMXBean.getBootClassPath(): ");
                    System.out.println(runtime.getBootClassPath());
                }
                System.out.print("RuntimeMXBean.getClassPath(): ");
                System.out.println(runtime.getClassPath());
                System.out.print("RuntimeMXBean.getLibraryPath(): ");
                System.out.println(runtime.getLibraryPath());
                System.out.print("RuntimeMXBean.getManagementSpecVersion(): ");
                System.out.println(runtime.getManagementSpecVersion());
                System.out.print("RuntimeMXBean.getName(): ");
                System.out.println(runtime.getName());
                System.out.print("RuntimeMXBean.getPid(): ");
                System.out.println(runtime.getPid());
                System.out.print("RuntimeMXBean.getSpecName(): ");
                System.out.println(runtime.getSpecName());
                System.out.print("RuntimeMXBean.getSpecVendor(): ");
                System.out.println(runtime.getSpecVendor());
                System.out.print("RuntimeMXBean.getSpecVersion(): ");
                System.out.println(runtime.getSpecVersion());
                System.out.print("RuntimeMXBean.getStartTime(): ");
                System.out.println(runtime.getStartTime());
                System.out.print("RuntimeMXBean.getUptime(): ");
                System.out.println(runtime.getUptime());
                System.out.print("RuntimeMXBean.getVmName(): ");
                System.out.println(runtime.getVmName());
                System.out.print("RuntimeMXBean.getVmVendor(): ");
                System.out.println(runtime.getVmVendor());
                System.out.print("RuntimeMXBean.getVmVersion(): ");
                System.out.println(runtime.getVmVersion());
                System.out.print("RuntimeMXBean.getInputArguments(): ");
                System.out.println(runtime.getInputArguments());
                System.out.print("RuntimeMXBean.getSystemProperties(): ");
                System.out.println(runtime.getSystemProperties());

                OperatingSystemMXBean os = JMX.newMXBeanProxy(
                    connector.getMBeanServerConnection(),
                    new ObjectName(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME),
                    OperatingSystemMXBean.class
                );
                System.out.print("OperatingSystemMXBean.getArch(): ");
                System.out.println(os.getArch());
                System.out.print("OperatingSystemMXBean.getAvailableProcessors(): ");
                System.out.println(os.getAvailableProcessors());
                System.out.print("OperatingSystemMXBean.getName(): ");
                System.out.println(os.getName());
                System.out.print("OperatingSystemMXBean.getSystemLoadAverage(): ");
                System.out.println(os.getSystemLoadAverage());
                System.out.print("OperatingSystemMXBean.getVersion(): ");
                System.out.println(os.getVersion());
                System.out.print("OperatingSystemMXBean.getCommittedVirtualMemorySize(): ");
                System.out.println(os.getCommittedVirtualMemorySize());
                System.out.print("OperatingSystemMXBean.getCpuLoad(): ");
                System.out.println(os.getCpuLoad());
                System.out.print("OperatingSystemMXBean.getFreeMemorySize(): ");
                System.out.println(os.getFreeMemorySize());
                System.out.print("OperatingSystemMXBean.getFreeSwapSpaceSize(): ");
                System.out.println(os.getFreeSwapSpaceSize());
                System.out.print("OperatingSystemMXBean.getProcessCpuLoad(): ");
                System.out.println(os.getProcessCpuLoad());
                System.out.print("OperatingSystemMXBean.getProcessCpuTime(): ");
                System.out.println(os.getProcessCpuTime());
                System.out.print("OperatingSystemMXBean.getTotalMemorySize(): ");
                System.out.println(os.getTotalMemorySize());
                System.out.print("OperatingSystemMXBean.getTotalSwapSpaceSize(): ");
                System.out.println(os.getTotalSwapSpaceSize());

                FileSystemMXBean fs = JMX.newMXBeanProxy(
                    connector.getMBeanServerConnection(),
                    new ObjectName(FileSystemMXBean.MXBEAN_NAME),
                    FileSystemMXBean.class
                );
                for (String root: fs.getRootDirectories()) {
                    System.out.print(String.format("FileSystemMXBean.getPartitionTotalSpace(%s): ", root));
                    System.out.println(fs.getPartitionTotalSpace(root));
                    System.out.print(String.format("FileSystemMXBean.getPartitionFreeSpace(%s): ", root));
                    System.out.println(fs.getPartitionFreeSpace(root));
                    System.out.print(String.format("FileSystemMXBean.getPartitionUsableSpace(%s): ", root));
                    System.out.println(fs.getPartitionUsableSpace(root));
                }
                System.out.print(String.format("FileSystemMXBean.getSpaceUsageInDirectory(%s): ", "."));
                System.out.println(fs.getSpaceUsageInDirectory("."));
                System.out.print(String.format("FileSystemMXBean.getNumberOfFilesInDirectory(%s): ", "."));
                System.out.println(fs.getNumberOfFilesInDirectory("."));

                FlightRecorderMXBean jfr = JMX.newMXBeanProxy(
                    connector.getMBeanServerConnection(),
                    new ObjectName(FlightRecorderMXBean.MXBEAN_NAME),
                    FlightRecorderMXBean.class
                );

                for (EventTypeInfo event: jfr.getEventTypes().stream().sorted((a, b) -> a.getName().compareTo(b.getName())).toList()) {
                    stream.enable(event.getName());
                    System.out.println(String.format("RemoteRecordingStream.enable(%s)", event.getName()));
                }

                Map<String, Integer> count = new HashMap<>();

                stream.onEvent((RecordedEvent event) -> {
                    String key = event.getEventType().getName();
                    if (!count.containsKey(key)) count.put(key, 0);
                    count.put(key, count.get(key) + 1);
                    System.out.println(
                        event.toString().lines().map(
                            line -> line.trim()
                        ).collect(
                            Collectors.joining(" ")
                        )
                    );
                });

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    count.entrySet().stream().sorted((a, b) -> a.getValue() - b.getValue()).forEach(entry -> {
                        System.out.println(String.format("%s: %d", entry.getKey(), entry.getValue()));
                    });
                }));

                stream.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
