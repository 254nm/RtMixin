package me.txmc.rtmixin.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 254n_m
 * @since 5/13/22/ 12:18 AM
 * This file was created as a part of RtMixin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Replace {
    MethodInfo info();
}
