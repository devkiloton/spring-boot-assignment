package com.grocery.payaut.util;

public class MathUtils {

    public static Double roundTwoDecimals(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
