package me.txmc.rtmixin.attach;

import me.txmc.rtmixin.Utils;

import java.io.File;
import java.lang.instrument.Instrumentation;

/**
 * @author 254n_m
 * @since 5/10/22/ 10:13 PM
 * This file was created as a part of RtMixin
 */
public interface Attacher {
    void attach(String pid) throws Throwable;
    Instrumentation getInstrumentation();

    default File getSelf(Class<?> klass) throws Throwable {
        return Utils.getSelf(klass);
    }
}
