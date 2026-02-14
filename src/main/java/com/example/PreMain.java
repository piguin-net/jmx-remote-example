package com.example;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.example.management.FileSystemMXBean;
import com.example.management.FileSystemMXBeanImpl;

public class PreMain {
    public static void premain(String agentArgs, Instrumentation inst) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, IOException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        FileSystemMXBean bean = new FileSystemMXBeanImpl();
        server.registerMBean(bean, new ObjectName(FileSystemMXBean.MXBEAN_NAME));
    }
}
