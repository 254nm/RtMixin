package me.txmc;

import me.txmc.rtmixin.RtMixin;

public class Main {
    public static void main(String[] args) {
        RtMixin.attachAgent().orElseThrow(NullPointerException::new);
        RtMixin.processMixins(TweakFile1.class);
//        RtMixin.processMixins(TweakFile2.class);
        new Main().init();
    }

    private void init() {
        TestClass test = new TestClass("This is the constructor test param");
        test.test("This is the non static method test param");
        System.out.println(test.replaceTest());
        TestClass.staticTest("This is the static method test param");
    }
}