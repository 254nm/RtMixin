package me.txmc.rtmixin.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 254n_m
 * @since 5/12/22/ 8:06 PM
 * This file was created as a part of RtMixin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface At {

    Position pos();
    int line() default -1;

    enum Position {
        HEAD,
        TAIL,
        LINE,
    }
}
