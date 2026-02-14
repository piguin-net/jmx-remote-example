package com.example.management;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class FileSystemMXBeanImpl implements FileSystemMXBean {
        @Override
        public List<String> getRootDirectories() {
            return Arrays.asList(File.listRoots()).stream().map(x -> x.getAbsolutePath()).toList();
        }

        @Override
        public Long getPartitionTotalSpace(String pathname) {
            File path = new File(pathname);
            if (!path.exists()) return -1l;
            return path.getTotalSpace();
        }

        @Override
        public Long getPartitionFreeSpace(String pathname) {
            File path = new File(pathname);
            if (!path.exists()) return -1l;
            return path.getFreeSpace();
        }

        @Override
        public Long getPartitionUsableSpace(String pathname) {
            File path = new File(pathname);
            if (!path.exists()) return -1l;
            return path.getUsableSpace();
        }

        @Override
        public Long getSpaceUsageInDirectory(String pathname) {
            File path = new File(pathname);
            if (!path.exists()) return -1l;
            try {
                return Files.walk(path.toPath())
                    .map(x -> x.toFile())
                    .filter(x -> x.isFile())
                    .map(x -> x.length())
                    .reduce(0l, (acc, x) -> acc + x);
            } catch (IOException e) {
                return -1l;
            }
        }

        @Override
        public Long getNumberOfFilesInDirectory(String pathname) {
            File path = new File(pathname);
            if (!path.exists()) return -1l;
            try {
                return Files.walk(path.toPath())
                    .map(x -> x.toFile())
                    .filter(x -> x.isFile())
                    .count();
            } catch (IOException e) {
                return -1l;
            }
        }
    }
