package me.txmc.rtmixin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 254n_m
 * @since 5/13/22/ 12:12 AM
 * This file was created as a part of RtMixin
 */
@Getter
@RequiredArgsConstructor
public class CallbackInfo {
    /**
     * The parameters that where passed to the method being mixed into
     */
    private final Object[] parameters;
    /**
     * The instance of the object being mixed into
     */
    private final Object self;
    private boolean cancel = false;

    /**
     * Will cause the method being mixed into to stop execution by returning
     */
    public void cancel() {
        cancel = true;
    }
}
