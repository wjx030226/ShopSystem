package org.example;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class MyGoodsDatabase {
    private static final String DB_URL = "jdbc:sqlite:goods.db";
    public static Connection connections;
    Scanner reader;
    public int goodsId;
    String sql;
    //初始化数据库
    public void initializeDatabase(){
        try{
            connections = DriverManager.getConnection(DB_URL);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try{
            Statement statement = connections.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Goods"+
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "name TEXT NOT NULL,price REAL NOT NULL, "+
                    "num INTEGER NOT NULL,buyNum INTEGER NOT NULL,isShopped BOOLEAN NOT NULL);";
            statement.executeUpdate(sql);
            sql = "UPDATE Goods SET isShopped = ?;";
            try {
                PreparedStatement pre = connections.prepareStatement(sql);
                pre.setBoolean(1, false);
                pre.executeUpdate();
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "警告", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("初始化数据库成功！");
        }
        catch (SQLException e){
            System.out.println("初始化数据库失败:"+e.getMessage());
        }
    }
    //删除商品
    public void delete()
    {
        System.out.print("请输入想要删除商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        System.out.print("删除后商品信息无法恢复，确定要删除该用户信息(Y/N)？");
        reader = new Scanner(System.in);
        String judge= reader.nextLine();
        if(judge.equals("Y")){
            sql = "SELECT * FROM Goods";
            try {
                Statement statement = connections.createStatement();
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    if (result.getInt("id") == goodsId) {
                        sql = "DELETE FROM Goods WHERE id=?;";
                        try{
                            PreparedStatement pre = connections.prepareStatement(sql);
                            pre.setInt(1,goodsId);
                            pre.executeUpdate();
                            System.out.println("商品删除成功！");
                        }
                        catch (SQLException e) {
                            System.out.println("未找到指定商品！"+e.getMessage());
                        }
                        return;
                    }
                }
                System.out.println("未找到指定商品！");
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else
            System.out.println("商品删除失败！");
    }
    //添加商品
    public void add()
    {
        System.out.print("请输入想要添加的商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        System.out.print("请输入想要添加的商品名称：");
        reader = new Scanner(System.in);
        String name = reader.nextLine();
        System.out.print("请输入想要添加的商品价格：");
        reader = new Scanner(System.in);
        double price = reader.nextDouble();
        System.out.print("请输入想要添加的商品数量：");
        reader = new Scanner(System.in);
        int num = reader.nextInt();
        sql = "INSERT INTO Goods (id, name, price, num, buyNum, isShopped) VALUES (?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement pre = connections.prepareStatement(sql);
            pre.setInt(1, goodsId);
            pre.setString(2, name);
            pre.setDouble(3, price);
            pre.setInt(4, num);
            pre.setInt(5, 0);
            pre.setBoolean(6,false);
            pre.executeUpdate();
            System.out.println("商品添加成功！");
        }
        catch (SQLException e){
                System.out.println("商品添加失败"+e.getMessage());
        }
    }
    //修改商品信息
    public void change()
    {
        System.out.print("请输入想要修改的商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        System.out.print("请输入想要修改的商品名称：");
        reader = new Scanner(System.in);
        String name = reader.nextLine();
        System.out.print("请输入想要修改的商品价格：");
        reader = new Scanner(System.in);
        double price = reader.nextDouble();
        System.out.print("请输入想要修改的商品数量：");
        reader = new Scanner(System.in);
        int num = reader.nextInt();
        sql = "SELECT * FROM Goods";
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == goodsId) {
                    sql = "UPDATE Goods SET name = ?, price = ?, num = ? WHERE id = ?;";
                    try{
                        PreparedStatement pre = connections.prepareStatement(sql);
                        pre.setString(1, name);
                        pre.setDouble(2, price);
                        pre.setInt(3, num);
                        pre.setInt(4, goodsId);
                        pre.executeUpdate();
                        System.out.println("商品修改成功！");
                    }
                    catch (SQLException e){
                        System.out.println("商品修改失败"+e.getMessage());
                    }
                    return;
                }
            }
            System.out.println("未找到指定商品！");
        }
        catch (SQLException e){
            System.out.println("商品修改失败"+e.getMessage());
        }
    }
    //查找商品信息
    public void find()
    {
        System.out.print("请输入想要查找的商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        sql = "SELECT * FROM Goods";
        try{
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getInt("id") == goodsId){
                    System.out.println("商品查找成功！");
                    System.out.println("要查找的商品的信息为：");
                    System.out.println("商品名称:"+result.getString("name")+
                            "，价格:"+result.getDouble("price")+",数量:"+result.getInt("num"));
                    return;
                }
            }
            System.out.println("想要查找的商品不存在！");
        }
        catch (SQLException e){
            System.out.println("商品查找失败"+e.getMessage());
        }
    }
    //显示所有商品信息
    public void print()
    {
        sql = "SELECT * FROM Goods";
        try{
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            System.out.println("商品编号       名称        价格       数量");
            System.out.println("--------------------------------------");
            while(result.next()){
                System.out.println(result.getInt("id")+"       "+result.getString("name")+
                        "       "+result.getDouble("price")+"       "+result.getInt("num"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void closeConnection(){
        try{
            connections.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
