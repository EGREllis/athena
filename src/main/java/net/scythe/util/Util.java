package net.scythe.util;

public class Util {
    public static void unreachableBranch(Object obj) {
        throw new RuntimeException(String.format("This code should never be reached, but it was: %1$s", obj));
    }

    public static void throwUncheckedException(Exception e) {
        throw new RuntimeException(e);
    }
}
