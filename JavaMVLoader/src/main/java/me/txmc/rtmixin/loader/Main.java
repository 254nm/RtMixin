package me.txmc.rtmixin.loader;

import com.sun.tools.attach.VirtualMachine;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                VirtualMachine vm = VirtualMachine.attach(args[0]);
                vm.loadAgent(args[1]);
                vm.detach();
            } catch (Throwable t) {
                t.printStackTrace();
                System.exit(1);
            }
            System.exit(0);
        }
    }
}