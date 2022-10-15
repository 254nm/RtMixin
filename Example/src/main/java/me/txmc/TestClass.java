package me.txmc;

/**
 * @author 254n_m
 * @since 5/14/22/ 12:23 AM
 * This file was created as a part of RtMixin
 */
public class TestClass {

    private final String test;
    public TestClass(String test) {
        this.test = test;
    }
    public void test(String test) {
        System.out.println("The test string is " + test);
        System.out.println("Test method");
        privateTest("[PRIVATE] " + test);
    }
    private void privateTest(String test) {
        System.out.println("The test string is " + test);
        System.out.println("Private Test method");
    }

    public String replaceTest() {
        return "If your seeing this method replacing dosnt work";
    }

    public static void staticTest(String test) {
        System.out.println("This is the static test");
    }
}
