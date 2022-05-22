package me.txmc.rtmixin;

import me.txmc.rtmixin.attach.Attacher;
import me.txmc.rtmixin.attach.attachers.Java8Attacher;
import me.txmc.rtmixin.attach.attachers.Java9Attacher;
import me.txmc.rtmixin.jagent.AgentMain;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.util.Optional;

/**
 * @author 254n_m
 * @since 5/10/22/ 9:49 PM
 * This file was created as a part of RtMixin
 */
public class RtMixin {

    private static Instrumentation inst;

    /**
     * This method will attempt to attach a java agent to the current JVM and expose Instrumentation
     *
     * @return Optional<Instrumentation>
     */
    public static Optional<Instrumentation> attachAgent() {
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        String pid = nameOfRunningVM.substring(0, nameOfRunningVM.indexOf('@'));
        try {
            if (inst == null) {
                return attachAgent(pid);
            } else return Optional.of(inst);
        } catch (Throwable e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * This method will attempt to attach a java agent to the JVM with the provided PID
     *
     * @return Optional<Instrumentation>
     */
    public static Optional<Instrumentation> attachAgent(String pid) throws Throwable {
        Attacher attacher = null;
        if (getVersion() >= 9) {
            attacher = new Java9Attacher();
        } else if (getVersion() <= 8) attacher = new Java8Attacher();
        if (attacher == null) throw new RuntimeException("This version of java is currently unsupported");
        attacher.attach(pid);
        inst = attacher.getInstrumentation();
        return Optional.ofNullable(attacher.getInstrumentation());
    }

    private static int getVersion() {
        String version = System.getProperty("java.version");
        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if (dot != -1) {
                version = version.substring(0, dot);
            }
        }
        return Integer.parseInt(version);
    }

    public static void processMixins(Class<?> _class) {
        AgentMain.doMixins(_class);
    }
}
