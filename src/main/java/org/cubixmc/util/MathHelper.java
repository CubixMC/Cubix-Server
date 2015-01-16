package org.cubixmc.util;

public class MathHelper {

    /**
     * Convert double to an int and floor if needed.
     *
     * @param value The original double value
     * @return Integer value, never more than the original
     */
    public static int floor(double value) {
        int i = (int) value;
        return i > value ? i - 1 : i;
    }
}
