package me.txmc.rtmixin.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 254n_m
 * @since 5/13/22/ 8:06 PM
 * This file was created as a part of RtMixin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodInfo {
    /**
     * The class to modify
     */
    Class<?> _class();


    /**
     * The name of the method to modify
     */
    String name();

    /**
     * The signature of the method to modify
     */
    Class<?>[] sig() default {};

    /**
     * The return type of the method to modify
     */
    Class<?> rtype();
}
