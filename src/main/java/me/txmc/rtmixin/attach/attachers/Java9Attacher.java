package me.txmc.rtmixin.attach.attachers;

import me.txmc.rtmixin.RtMixin;
import me.txmc.rtmixin.attach.Attacher;
import me.txmc.rtmixin.jagent.AgentMain;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;

/**
 * @author 254n_m
 * @since 5/14/22/ 1:00 AM
 * This file was created as a part of RtMixin
 */
public class Java9Attacher implements Attacher {
    private Instrumentation inst;

    @Override
    public void attach(String pid) throws Throwable {
        File tempJar = new File(".", "Java9AgentLoader.jar");
        if (tempJar.exists()) tempJar.delete();
        Files.copy(getClass().getClassLoader().getResourceAsStream("Java9Loader-1.0-SNAPSHOT-all.jar"), tempJar.toPath());
        String javaPath = System.getProperty("java.home") + "/bin/java"; //The java in $PATH might be a different version than the one we want
        //Spawn a new java process to attach the java agent to our current JVM because the JVM doesn't allow us to directly attach our own JVM
        Process process = new ProcessBuilder(javaPath, "-jar", tempJar.getAbsolutePath(), pid, getSelf(RtMixin.class).getAbsolutePath()).start();
        process.waitFor();
        tempJar.delete();
        inst = AgentMain.getInst();
    }

    @Override
    public Instrumentation getInstrumentation() {
        return inst;
    }
}
