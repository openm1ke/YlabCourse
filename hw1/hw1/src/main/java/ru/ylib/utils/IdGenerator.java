package ru.ylib.utils;

public class IdGenerator {
    private static long carId = 1;
    private static long userId = 1;

    public static synchronized long generateCarId() {
        return carId++;
    }

    public static synchronized long generateUserId() {
        return userId++;
    }
}
