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
    private final Object[] parameters;
    private final Object self;
    private boolean cancel = false;

    /**
     * Will return the method
     */
    public void cancel() {
        cancel = true;
    }
}
