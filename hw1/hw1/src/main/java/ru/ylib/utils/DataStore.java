package ru.ylib.utils;

import ru.ylib.models.Car;
import ru.ylib.models.Order;
import ru.ylib.models.User;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<Car> cars = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();
}
