package me.txmc;

import me.txmc.rtmixin.CallbackInfo;
import me.txmc.rtmixin.mixin.At;
import me.txmc.rtmixin.mixin.Inject;
import me.txmc.rtmixin.mixin.MethodInfo;

import java.util.Arrays;

public class TweakFile2 {
    @Inject(info = @MethodInfo(_class = TestClass.class, name = "test", sig = String.class, rtype = void.class), at = @At(pos = At.Position.HEAD))
    public static void modifyMethod(CallbackInfo ci) {
        System.out.println("Non static method params " + Arrays.toString(ci.getParameters()));
        System.out.println("Non static method self " + ci.getSelf());
        System.out.println("This line was added by TweakFile2");
    }
}
