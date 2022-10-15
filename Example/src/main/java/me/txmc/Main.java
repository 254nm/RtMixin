package me.txmc;

import me.txmc.rtmixin.RtMixin;
import me.txmc.rtmixin.Utils;

import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

public class Main {
    public static void main(String[] args) {
        try {
            Instrumentation inst = RtMixin.attachAgent().orElseThrow(NullPointerException::new); //Attach the agent and get an instance of Instrumentation
            inst.appendToSystemClassLoaderSearch(new JarFile(Utils.getSelf(Main.class))); //Append this jar to the system class loader to avoid classloading weirdness
            RtMixin.processMixins(TweakFile1.class); //Register the mixins in TweakFile1
//            RtMixin.processMixins(TweakFile2.class); //Register the mixins in TweakFile1
            new Main().init();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void init() {
        TestClass test = new TestClass("This is the constructor test param");
        test.test("This is the non static method test param");
        System.out.println(test.replaceTest());
        TestClass.staticTest("This is the static method test param");
    }
}