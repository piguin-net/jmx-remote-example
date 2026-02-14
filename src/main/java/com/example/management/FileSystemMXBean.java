package com.example.management;

import java.util.List;

public interface FileSystemMXBean {
    public static final String MXBEAN_NAME = String.format(
        "%s:name=%s",
        FileSystemMXBean.class.getPackageName(),
        FileSystemMXBean.class.getSimpleName()
    );
    public List<String> getRootDirectories();
    public Long getPartitionTotalSpace(String pathname);
    public Long getPartitionFreeSpace(String pathname);
    public Long getPartitionUsableSpace(String pathname);
    public Long getSpaceUsageInDirectory(String pathname);
    public Long getNumberOfFilesInDirectory(String pathname);
}
