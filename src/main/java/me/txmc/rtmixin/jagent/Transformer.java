package me.txmc.rtmixin.jagent;

import javassist.*;
import javassist.bytecode.Descriptor;
import me.txmc.rtmixin.Utils;
import me.txmc.rtmixin.mixin.Inject;
import me.txmc.rtmixin.mixin.Replace;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

/**
 * @author 254n_m
 * @since 5/12/22/ 8:26 PM
 * This file was created as a part of RtMixin
 */
public class Transformer implements ClassFileTransformer {
    private final ClassPool clp = ClassPool.getDefault();

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (classBeingRedefined == null || AgentMain.beingRedefined == null) return classfileBuffer;
        if (AgentMain.beingRedefined == classBeingRedefined) {
            try { //TODO Clean up code and add support for the <clinit> block
                clp.appendClassPath(new LoaderClassPath(loader));
                CtClass cc = clp.get(classBeingRedefined.getName());
                for (Method tweakMethod : AgentMain.methods) {
                    String paramsName = Utils.genRandomString(8);
                    String ciName = Utils.genRandomString(8);
                    if (tweakMethod.isAnnotationPresent(Inject.class)) {
                        Inject inject = tweakMethod.getAnnotation(Inject.class);
                        StringBuilder src = new StringBuilder();
                        if (inject.info().name().equals("<init>")) {
                            CtConstructor constructor = cc.getDeclaredConstructor(JavaAssistUtils.fromArr(inject.info().sig(), clp));
                            JavaAssistUtils.appendBoiler(constructor, paramsName, ciName, src);
                            src.append(tweakMethod.getDeclaringClass().getName()).append(".").append(tweakMethod.getName()).append("(").append(ciName).append(");\n");
                            JavaAssistUtils.injectCode(constructor, inject.at().pos(), src.toString(), inject.at().line());
                        } else {
                            String desc = Descriptor.ofMethod(clp.get(inject.info().rtype().getName()), JavaAssistUtils.fromArr(inject.info().sig(), clp));
                            CtMethod method = cc.getMethod(inject.info().name(), desc);
                            JavaAssistUtils.appendBoiler(method, paramsName, ciName, src);
                            src.append(tweakMethod.getDeclaringClass().getName()).append(".").append(tweakMethod.getName()).append("(").append(ciName).append(");\n");
                            String ret = (method.getReturnType() == CtClass.voidType) ? ";" : "null;";
                            src.append("if (").append(ciName).append(".isCancel()) return ").append(ret);
                            JavaAssistUtils.injectCode(method, inject.at().pos(), src.toString(), inject.at().line());
                        }
                    } else if (tweakMethod.isAnnotationPresent(Replace.class)) {
                        Replace replace = tweakMethod.getAnnotation(Replace.class);
                        CtMethod method = (replace.info().sig().length == 0) ? cc.getDeclaredMethod(replace.info().name()) : cc.getDeclaredMethod(replace.info().name(), JavaAssistUtils.fromArr(replace.info().sig(), clp));
                        StringBuilder src = new StringBuilder();
                        src.append("{ \n");
                        JavaAssistUtils.appendBoiler(method, paramsName, ciName, src);
                        if (method.getReturnType() == CtClass.voidType) {
                            src.append(tweakMethod.getDeclaringClass().getName()).append(".").append(tweakMethod.getName()).append("(").append(ciName).append(");\n }");
                        } else {
                            src.append("return ").append(tweakMethod.getDeclaringClass().getName()).append(".").append(tweakMethod.getName()).append("(").append(ciName).append(");\n }");
                        }
                        method.setBody(src.toString());
                    }
                }
                cc.detach();
                return cc.toBytecode();
            } catch (Throwable t) {
                t.printStackTrace();
                System.out.println("[RtMixin] Failed to instrument class " + className);
            }
        }
        return classfileBuffer;
    }
}
