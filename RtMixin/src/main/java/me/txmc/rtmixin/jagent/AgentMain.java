package me.txmc.rtmixin.jagent;

import me.txmc.rtmixin.RtMixin;
import me.txmc.rtmixin.Utils;
import me.txmc.rtmixin.mixin.Inject;
import me.txmc.rtmixin.mixin.MethodInfo;
import me.txmc.rtmixin.mixin.Replace;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.jar.JarFile;

/**
 * @author 254n_m
 * @since 5/10/22/ 10:26 PM
 * This file was created as a part of RtMixin
 */
public class AgentMain {
    private static final HashMap<Class<?>, List<Method>> mixinCache = new HashMap<>();
    protected static Method[] methods;
    protected static Class<?> beingRedefined;
    private static Instrumentation inst;

    public static void premain(String agentOps, Instrumentation inst) {
    }


    public static void agentmain(String agentOps, Instrumentation inst) {
        try {
            AgentMain.inst = inst;
            inst.appendToSystemClassLoaderSearch(new JarFile(Utils.getSelf(RtMixin.class)));
            inst.addTransformer(new Transformer(), true);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void doMixins(Class<?> tweakClass) { //TODO make this method not redo every mixin every time while supporting mixins from multiple tweak classes & multiple different classes being tweaked
        if (!Utils.hasMixins(tweakClass)) return;
        try {
            Method[] tweakMethods = Arrays.stream(tweakClass.getDeclaredMethods()).filter(Utils::isMixinMethod).toArray(Method[]::new);
            for (Method tweakMethod : tweakMethods) {
                MethodInfo info = (tweakMethod.isAnnotationPresent(Inject.class)) ? tweakMethod.getAnnotation(Inject.class).info() : tweakMethod.getAnnotation(Replace.class).info();
                if (!mixinCache.containsKey(info._class())) {
                    mixinCache.put(info._class(), new ArrayList<>());
                    mixinCache.get(info._class()).add(tweakMethod);
                } else {
                    List<Method> clMethods = mixinCache.get(info._class());
                    if (!clMethods.contains(tweakMethod)) clMethods.add(tweakMethod);
                }
            }
            for (Map.Entry<Class<?>, List<Method>> entry : mixinCache.entrySet()) {
                AgentMain.methods = entry.getValue().toArray(new Method[0]);
                AgentMain.beingRedefined = entry.getKey();
                getInst().retransformClasses(entry.getKey());
                AgentMain.methods = null;
                AgentMain.beingRedefined = null;
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static Instrumentation getInst() {
        return inst;
    }
}
