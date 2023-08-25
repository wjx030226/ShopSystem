package org.example;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static ArrayList<ShopCars> shopHistory = new ArrayList<>();
    public static MyGoodsDatabase goods = new MyGoodsDatabase();
    public static MyUserDatabase users = new MyUserDatabase();
    public static MyCarDatabase cars = new MyCarDatabase();
    public static Login login;
    public static Register register;
    public static GoodsPage goodsPage;
    public static UsersPage usersPage;

    public static void main(String[] args) {
        goods.initializeDatabase();
        users.initializeDatabase();
        cars.readFile("file\\CarsHistory.txt", shopHistory);
        login = new Login();
        login.setSize(600, 500);
        login.setLocationRelativeTo(null);
        login.setTitle("购物管理系统");
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        register = new Register();
        register.setSize(600, 500);
        register.setLocationRelativeTo(null);
        register.setTitle("购物管理系统");
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usersPage = new UsersPage();
        usersPage.setSize(600, 500);
        usersPage.setLocationRelativeTo(null);
        usersPage.setTitle("购物管理系统");
        usersPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        goodsPage = new GoodsPage();
        goodsPage.setSize(600, 500);
        goodsPage.setLocationRelativeTo(null);
        goodsPage.setTitle("购物管理系统");
        goodsPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setVisible(true);
    }
}