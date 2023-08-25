package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args){
        ArrayList<ShopCars> shopHistory = new ArrayList<>();
        MyGoodsDatabase goods = new MyGoodsDatabase();
        MyUserDatabase users = new MyUserDatabase();
        MyCarDatabase cars = new MyCarDatabase();
        //初始化数据库
        goods.initializeDatabase();
        users.initializeDatabase();
        cars.readFile("file\\CarsHistory.txt", shopHistory);
        while(true){
            System.out.println("============================");
            System.out.println("1.管理员\n2.用户\n3.退出系统");
            System.out.print("请输入您的身份：");
            Scanner reader = new Scanner(System.in);
            int x = reader.nextInt();
            switch (x) {
                case 1 -> {
                    int y, z;
                    int managerID = 0;
                    do {
                        System.out.println("============================");
                        System.out.println("1.注册\n2.登录\n3.密码管理\n4.客户管理\n5.商品管理\n6.退出登录");
                        System.out.print("请输入想要执行的操作：");
                        reader = new Scanner(System.in);
                        y = reader.nextInt();
                        switch (y) {
                            case 1 -> users.register(false);
                            case 2 -> managerID = users.login(false);
                            case 3 -> {
                                if(managerID != 0){
                                    do{
                                        System.out.println("============================");
                                        System.out.println("1.修改自身密码\n2.重置用户密码\n3.回到上一层");
                                        System.out.print("请输入想要执行的密码管理操作：");
                                        reader = new Scanner(System.in);
                                        z = reader.nextInt();
                                        switch (z) {
                                            case 1 -> users.change(managerID);
                                            case 2 -> users.modify();
                                            case 3 -> {}
                                            default -> System.out.println("输入的选择无效，请重新输入！");
                                        }
                                    }while(z !=3);
                                }
                                else System.out.println("您还未登录账号，请先登录！");
                            }
                            case 4 -> {
                                if(managerID != 0){
                                    do{
                                        System.out.println("============================");
                                        System.out.println("1.列出所有客户信息\n2.删除客户信息\n3.查询客户信息\n4.回到上一层");
                                        System.out.print("请输入想要执行的客户管理操作：");
                                        reader = new Scanner(System.in);
                                        z = reader.nextInt();
                                        switch (z) {
                                            case 1 -> users.print();
                                            case 2 -> users.delete();
                                            case 3 -> users.find();
                                            case 4 -> {}
                                            default -> System.out.println("输入的选择无效,请重新输入！");
                                        }
                                    }while(z !=4);
                                }
                                else System.out.println("您还未登录账号，请先登录！");
                            }
                            case 5 -> {
                                if(managerID != 0){
                                    do{
                                        System.out.println("============================");
                                        System.out.println("1.列出所有商品信息\n2.添加商品信息\n3.修改商品信息\n4.删除商品信息\n5.查询商品信息\n6.回到上一层");
                                        System.out.print("请输入想要执行的商品管理操作：");
                                        reader = new Scanner(System.in);
                                        z = reader.nextInt();
                                        switch (z) {
                                            case 1 -> goods.print();
                                            case 2 -> goods.add();
                                            case 3 -> goods.change();
                                            case 4 -> goods.delete();
                                            case 5 -> goods.find();
                                            case 6 -> {}
                                            default -> System.out.println("输入的选择无效，请重新输入！");
                                        }
                                    }while(z != 6);
                                }
                                else System.out.println("您还未登录账号，请先登录！");
                            }
                            case 6 -> managerID = 0;
                            default -> System.out.println("输入的选择无效！");
                        }
                    } while (y != 6);
                }
                case 2 -> {
                    int y, z;
                    int userID = 0;
                    do {
                        System.out.println("============================");
                        System.out.println("1.注册\n2.登录\n3.密码管理\n4.购物\n5.退出登录");
                        System.out.print("请输入想要执行的操作：");
                        reader = new Scanner(System.in);
                        y = reader.nextInt();
                        switch (y) {
                            case 1 -> users.register(true);
                            case 2 -> userID = users.login(true);
                            case 3 -> {
                                do{
                                    System.out.println("============================");
                                    System.out.println("1.修改密码\n2.重置密码\n3.回到上一层");
                                    System.out.print("请输入想要执行的密码管理操作：");
                                    reader = new Scanner(System.in);
                                    z = reader.nextInt();
                                    switch (z) {
                                        case 1 -> {
                                            if(userID != 0)
                                                users.change(userID);
                                            else System.out.println("您还未登录账号，请先登录！");
                                        }
                                        case 2 -> users.resent();
                                        case 3 -> {}
                                        default -> System.out.println("输入的选择无效，请重新输入！");
                                    }
                                }while(z != 3);
                            }
                            case 4 -> {
                                if(userID != 0){
                                    System.out.println("============================");
                                    System.out.println("商场中含有的商品信息如下：");
                                    goods.print();
                                    do{
                                        System.out.println("============================");
                                        System.out.println("1.显示购物车中商品\n2.将商品加入购物车\n3.从购物车中移除商品\n4.修改购物车中商品\n5.模拟结账\n6.查看购物历史\n7.回到上一层");
                                        System.out.print("请输入想要执行的商品管理操作：");
                                        reader = new Scanner(System.in);
                                        z = reader.nextInt();
                                        switch (z) {
                                            case 1 -> cars.car_print();
                                            case 2 -> cars.car_add();
                                            case 3 -> cars.car_delete();
                                            case 4 -> cars.car_change();
                                            case 5 -> cars.car_account(shopHistory, userID);
                                            case 6 -> cars.car_history(shopHistory, userID);
                                            case 7 -> {}
                                            default -> System.out.println("输入的选择无效，请重新输入！");
                                        }
                                    }while(z != 7);
                                }
                                else System.out.println("您还未登录账号，请先登录！");
                            }
                            case 5 -> userID = 0;
                            default -> System.out.println("输入的选择无效，请重新输入！");
                        }
                    }while (y != 5);
                }
                case 3 -> {
                    goods.closeConnection();
                    users.closeConnection();
                    cars.writeFile("file\\CarsHistory.txt", shopHistory);
                    System.exit(1);
                }
                default -> System.out.println("输入的选择无效，请重新输入！");
            }
        }
    }
}