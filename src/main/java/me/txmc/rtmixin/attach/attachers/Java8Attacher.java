package me.txmc.rtmixin.attach.attachers;

import com.sun.tools.attach.VirtualMachine;
import me.txmc.rtmixin.RtMixin;
import me.txmc.rtmixin.Utils;
import me.txmc.rtmixin.attach.Attacher;
import me.txmc.rtmixin.jagent.AgentMain;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author 254n_m
 * @since 5/10/22/ 9:52 PM
 * This file was created as a part of RtMixin
 */
public class Java8Attacher implements Attacher {
    private Instrumentation inst;
    @Override
    public void attach(String pid) throws Throwable {
        File toolsJar = Utils.findToolsJar(new File(System.getProperty("java.home")));
        URLClassLoader cl = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addUrl.setAccessible(true);
        addUrl.invoke(cl, toolsJar.toURI().toURL());
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(getSelf(RtMixin.class).getAbsolutePath());
        vm.detach();
        inst = AgentMain.getInst();
    }

    @Override
    public Instrumentation getInstrumentation() {
        return inst;
    }

}
