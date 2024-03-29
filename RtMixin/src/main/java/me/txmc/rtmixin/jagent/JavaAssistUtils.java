package me.txmc.rtmixin.jagent;

import javassist.*;
import javassist.bytecode.Descriptor;
import me.txmc.rtmixin.Utils;
import me.txmc.rtmixin.mixin.At;
import me.txmc.rtmixin.mixin.Inject;
import me.txmc.rtmixin.mixin.MethodInfo;
import me.txmc.rtmixin.mixin.Replace;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author 254n_m
 * @since 5/22/22/ 1:53 AM
 * This file was created as a part of RtMixin
 */
public class JavaAssistUtils {
    public static void injectCode(CtBehavior method, At.Position pos, String code, int line) throws Throwable {
        switch (pos) {
            case HEAD:
                method.insertBefore(code);
                break;
            case TAIL:
                method.insertAfter(code);
                break;
            case LINE:
                if (line == -1)
                    throw new IllegalArgumentException("You must add a line number in @At to use Position.LINE");
                method.insertAt(line, true, code);
                break;
        }
    }
    public static void appendBoiler(CtBehavior behavior, String paramsName, String ciName, StringBuilder sb) throws Throwable {
        int paramsLen = behavior.getParameterTypes().length;
        if (paramsLen > 0) {
            sb.append("java.lang.Object[] ").append(paramsName).append(" = new java.lang.Object[").append(paramsLen).append("];\n");
            for (int i = 0; i < paramsLen; i++) {
                sb.append(paramsName).append("[").append(i).append("] = me.txmc.rtmixin.Utils.fromPrimitive($").append(i + 1).append(");\n");
            }
        }
        String params = (paramsLen == 0) ? "null" : paramsName;
        if (behavior instanceof CtMethod) {
            CtMethod method = (CtMethod) behavior;
            String self = (Modifier.isStatic(method.getModifiers())) ? "null" : "this";
            sb.append("me.txmc.rtmixin.CallbackInfo ").append(ciName).append(" = new me.txmc.rtmixin.CallbackInfo(").append(params).append(", ").append(self).append(");\n");
        } else if (behavior instanceof CtConstructor) {
            sb.append("me.txmc.rtmixin.CallbackInfo ").append(ciName).append(" = new me.txmc.rtmixin.CallbackInfo(").append(params).append(", this);\n");
        }
    }
    public static CtClass[] fromArr(Class<?>[] arr, ClassPool classPool) throws Throwable {
        CtClass[] buf = new CtClass[arr.length];
        for (int i = 0; i < buf.length; i++) buf[i] = classPool.get(arr[i].getName());
        return buf;
    }
}
