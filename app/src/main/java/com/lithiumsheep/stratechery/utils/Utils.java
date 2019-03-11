package com.lithiumsheep.stratechery.utils;

public final class Utils {

    public static boolean isEmpty(String... args) {
        for (String s : args) {
            if (s == null || s.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
