package me.txmc;

import me.txmc.rtmixin.CallbackInfo;
import me.txmc.rtmixin.mixin.At;
import me.txmc.rtmixin.mixin.Inject;
import me.txmc.rtmixin.mixin.MethodInfo;
import me.txmc.rtmixin.mixin.Replace;

import java.util.Arrays;

/**
 * @author 254n_m
 * @since 5/15/22/ 12:27 AM
 * This file was created as a part of RtMixin
 */
public class TweakFile1 {

    @Inject(info = @MethodInfo(_class = TestClass.class, name = "test", sig = String.class, rtype = void.class), at = @At(pos = At.Position.HEAD))
    public static void modifyMethod(CallbackInfo ci) {
        System.out.println("Non static method params " + Arrays.toString(ci.getParameters()));
        System.out.println("Non static method self " + ci.getSelf());
        System.out.println("This line was added by TweakFile1");
    }

    @Inject(info = @MethodInfo(_class = TestClass.class, name = "<init>", sig = String.class, rtype = void.class), at = @At(pos = At.Position.TAIL))
    public static void modifyConstructor(CallbackInfo ci) {
        System.out.println("Constructor params " + Arrays.toString(ci.getParameters()));
        System.out.println("Constructor self " + ci.getSelf());
        System.out.println("This line was added to the constructor by TweakFile1");
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
    }

    @Inject(info = @MethodInfo(_class = TestClass.class, name = "staticTest", sig = String.class, rtype = void.class), at = @At(pos = At.Position.HEAD))
    public static void modifyStaticMethod(CallbackInfo ci) {
        System.out.println("Static method params " + Arrays.toString(ci.getParameters()));
        System.out.println("Static method self " + ci.getSelf());
        System.out.println("This line was added by TweakFile1");
    }

    @Replace(info = @MethodInfo(_class = TestClass.class, name = "replaceTest", rtype = String.class))
    public static String modifyReplaceTest(CallbackInfo ci) {
        return "If your seeing this method replacing works\n\n\n\n\n";
    }
    @Replace(info = @MethodInfo(_class = TestClass.class, sig = String.class, name = "privateTest", rtype = void.class))
    public static void modifyPrivateTest(CallbackInfo ci) {
        System.out.println("This was called insteds o f private meth");
    }
}
