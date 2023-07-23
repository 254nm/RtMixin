package me.txmc.rtmixin.jagent;

import me.txmc.rtmixin.RtMixin;
import me.txmc.rtmixin.Utils;
import me.txmc.rtmixin.mixin.Mixin;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

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

    public static void doMixins(Class<?> tweakClass) {
        try {
            if (!tweakClass.isAnnotationPresent(Mixin.class)) throw new IllegalArgumentException(String.format("Tweak class %s does not have the @Mixin annotation", tweakClass.getName()));
            Class<?> beingMixed = tweakClass.getAnnotation(Mixin.class).value();
            List<Method> newTweakMethods = Arrays.stream(tweakClass.getDeclaredMethods()).filter(Utils::isMixinMethod).collect(Collectors.toList());
            if (mixinCache.containsKey(beingMixed)) {
                List<Method> currentTweakMethods = mixinCache.get(beingMixed);
                currentTweakMethods.addAll(newTweakMethods);
                mixinCache.put(beingMixed, currentTweakMethods.stream().distinct().collect(Collectors.toList()));
            } else mixinCache.put(beingMixed, new ArrayList<>(newTweakMethods));

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
