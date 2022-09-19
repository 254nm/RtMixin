package me.txmc.rtmixin.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 254n_m
 * @since 5/12/22/ 8:02 PM
 * This file was created as a part of RtMixin
 * Will call the method that is being annotated by this at the position specified
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Inject {


    /**
     * @return The information about the method being modified
     */
    MethodInfo info();

    /**
     * @return Metadata about where to place the method call
     */
    At at();
}
