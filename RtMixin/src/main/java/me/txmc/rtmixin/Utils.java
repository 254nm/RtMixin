package me.txmc.rtmixin;

import me.txmc.rtmixin.mixin.Inject;
import me.txmc.rtmixin.mixin.Replace;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 254n_m
 * @since 5/13/22/ 8:17 PM
 * This file was created as a part of RtMixin
 */
public class Utils {
    public static File getSelf(Class<?> klass) throws Throwable {
        return new File(klass.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    }

    public static File findToolsJar(File javaHome) {
        File toolsJar = new File(javaHome, "lib/tools.jar");
        if (toolsJar.exists()) {
            return toolsJar;
        }
        if (javaHome.getName().equalsIgnoreCase("jre")) {
            javaHome = javaHome.getParentFile();
            toolsJar = new File(javaHome, "lib/tools.jar");
            if (toolsJar.exists()) {
                return toolsJar;
            }
        }

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            String version = System.getProperty("java.version");
            if (javaHome.getName().matches("jre\\d+") || javaHome.getName().equals("jre" + version)) {
                javaHome = new File(javaHome.getParentFile(), "jdk" + version);
                toolsJar = new File(javaHome, "lib/tools.jar");
                if (toolsJar.exists()) {
                    return toolsJar;
                }
            }
        }
        return null;
    }

    public static boolean isMixinMethod(Method method) {
        if (!Modifier.isStatic(method.getModifiers()) && !Modifier.isPublic(method.getModifiers())) return false;
        if (method.isAnnotationPresent(Inject.class) || method.isAnnotationPresent(Replace.class)) {
            return method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(CallbackInfo.class);
        }
        return false;
    }

    //There are 10 fromPrimitive methods here because for some reason primitives aren't objects even tho everything in java is supposed to be an object
    public static Object fromPrimitive(byte p) {
        return p;
    }

    public static Object fromPrimitive(short p) {
        return p;
    }

    public static Object fromPrimitive(int p) {
        return p;
    }

    public static Object fromPrimitive(long p) {
        return p;
    }

    public static Object fromPrimitive(float p) {
        return p;
    }

    public static Object fromPrimitive(double p) {
        return p;
    }

    public static Object fromPrimitive(char p) {
        return p;
    }

    public static Object fromPrimitive(boolean p) {
        return p;
    }

    public static Object fromPrimitive(Object p) {
        return p;
    }

    public static String genRandomString(int targetStrLen) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder buf = new StringBuilder(targetStrLen);
        for (int i = 0; i < targetStrLen; i++) {
            buf.append((char) random.nextInt(97, 122));
        }
        return buf.toString();
    }

    private static String getDescriptorForClass(Class<?> _class) {
        if (_class.isPrimitive()) {
            if (_class == byte.class) return "B";
            if (_class == char.class) return "C";
            if (_class == double.class) return "D";
            if (_class == float.class) return "F";
            if (_class == int.class) return "I";
            if (_class == long.class) return "J";
            if (_class == short.class) return "S";
            if (_class == boolean.class) return "Z";
            if (_class == void.class) return "V";
        }
        if (_class.isArray()) return _class.getName().replace('.', '/');
        return ('L' + _class.getName() + ';').replace('.', '/');
    }

    public static String getDescriptor(Class<?>[] paramTypes, Class<?> returnType) {
        StringBuilder sb = new StringBuilder("(");
        for (Class<?> c : paramTypes) sb.append(getDescriptorForClass(c));
        sb.append(')');
        return sb.append(getDescriptorForClass(returnType)).toString();
    }
}
