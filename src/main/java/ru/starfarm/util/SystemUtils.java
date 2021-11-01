package ru.starfarm.util;

import lombok.SneakyThrows;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.URI;

public class SystemUtils {

    @SneakyThrows
    public static void openUrl(URI uri) {
        Desktop.getDesktop().browse(uri);
    }

    @SneakyThrows
    public static void openUrl(String url) {
        openUrl(new URI(url));
    }

    public static String[] getSystem() {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
        return new String[] { os.getName(), os.getVersion(), String.valueOf(runtime.availableProcessors()) };
    }

    public static long[] getMemory() {
        Runtime runtime = getRuntime();
        return new long[] { runtime.maxMemory(), runtime.totalMemory(), runtime.freeMemory() };
    }

    public static long getRuntimeUptime() {
        return ManagementFactory.getRuntimeMXBean().getUptime();
    }

    public static Runtime getRuntime() {
        return Runtime.getRuntime();
    }
}
