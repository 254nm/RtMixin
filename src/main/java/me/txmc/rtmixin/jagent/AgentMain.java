package me.txmc.rtmixin.jagent;

import lombok.SneakyThrows;
import me.txmc.rtmixin.RtMixin;
import me.txmc.rtmixin.Utils;
import me.txmc.rtmixin.mixin.Inject;
import me.txmc.rtmixin.mixin.MethodInfo;
import me.txmc.rtmixin.mixin.Replace;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
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

    @SneakyThrows
    public static void agentmain(String agentOps, Instrumentation inst) {
        AgentMain.inst = inst;
        inst.appendToSystemClassLoaderSearch(new JarFile(Utils.getSelf(RtMixin.class)));
        inst.addTransformer(new Transformer(), true);
        Class<?>[] alreadyLoaded = Arrays.stream(inst.getAllLoadedClasses()).filter(Objects::nonNull).filter(inst::isModifiableClass).filter(Utils::isNotJvmClass).toArray(Class<?>[]::new);
        for (Class<?> c : alreadyLoaded) doMixins(c);
    }

    public static void doMixins(Class<?> _class) { //TODO make this method not redo every mixin every time while supporting mixins from multiple tweak classes
        if (!Utils.hasMixins(_class)) return;
        try {
            Method[] methods = Arrays.stream(_class.getDeclaredMethods()).filter(Utils::isMixinMethod).toArray(Method[]::new);
            for (Method method : methods) {
                MethodInfo info = (method.isAnnotationPresent(Inject.class)) ? method.getAnnotation(Inject.class).info() : method.getAnnotation(Replace.class).info();
                if (!mixinCache.containsKey(info._class())) {
                    mixinCache.put(info._class(), new ArrayList<>());
                    mixinCache.get(info._class()).add(method);
                } else mixinCache.get(info._class()).add(method);
            }
            for (Map.Entry<Class<?>, List<Method>> entry : mixinCache.entrySet()) {
                try {
                    AgentMain.methods = entry.getValue().toArray(new Method[0]);
                    AgentMain.beingRedefined = entry.getKey();
                    getInst().retransformClasses(entry.getKey());
                    AgentMain.methods = null;
                    AgentMain.beingRedefined = null;
                } catch (UnmodifiableClassException e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static Instrumentation getInst() {
        return inst;
    }
}
