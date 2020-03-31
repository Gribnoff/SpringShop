package ru.gribnoff.springshop.util;

public class UUIDValidator {
    private final static String UUID_MATCH = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";

    public static boolean isUUID(String uuidInStr) throws IllegalArgumentException {
        return uuidInStr.matches(UUID_MATCH);
    }
}
