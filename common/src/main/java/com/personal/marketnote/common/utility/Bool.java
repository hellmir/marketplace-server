package com.personal.marketnote.common.utility;

public enum Bool {
    T,
    F;

    public static Bool from(boolean bool) {
        return bool ? Bool.T : Bool.F;
    }
}
