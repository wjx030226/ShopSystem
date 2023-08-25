package org.example;

import javax.swing.*;
import java.sql.*;

public class MyGoodsDatabase {
    private static final String DB_URL = "jdbc:sqlite:goods.db";
    public static Connection connections;
    String sql;
    public MyGoodsDatabase() {
        try{
            connections = DriverManager.getConnection(DB_URL);
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    public void initializeDatabase(){
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
            System.err.println("初始化数据库失败:"+e.getMessage());
        }
    }
    public void delete(int id)
    {
        JPanel goodsPane=new JPanel();
        int order=JOptionPane.showConfirmDialog(goodsPane,"确定要删除该商品信息！","提示", JOptionPane.YES_NO_OPTION);
        if(order==JOptionPane.YES_OPTION){
            sql = "SELECT * FROM Goods";
            try {
                Statement statement = connections.createStatement();
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    if (result.getInt("id") == id) {
                        sql = "DELETE FROM Goods WHERE id=?;";
                        try{
                            PreparedStatement pre = connections.prepareStatement(sql);
                            pre.setInt(1,id);
                            pre.executeUpdate();
                            JOptionPane.showMessageDialog(null, "商品已删除！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "未找到指定商品！"+e.getMessage(),"警告", JOptionPane.ERROR_MESSAGE);
                        }
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "该商品不存在，删除失败！","警告", JOptionPane.ERROR_MESSAGE);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "未找到指定商品！"+e.getMessage(),"警告", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void add(int id, String name, int num, double price)
    {
        sql = "INSERT INTO Goods (id, name, price, num, buyNum, isShopped) VALUES (?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement pre = connections.prepareStatement(sql);
            pre.setInt(1, id);
            pre.setString(2, name);
            pre.setDouble(3, price);
            pre.setInt(4, num);
            pre.setInt(5, 0);
            pre.setBoolean(6,false);
            pre.executeUpdate();
            JOptionPane.showMessageDialog(null, "商品添加成功！","提示",JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"商品添加失败"+e.getMessage(), "警告",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void change(int id, String name, int num, double price)
    {
        sql = "SELECT * FROM Goods";
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == id) {
                    sql = "UPDATE Goods SET name = ?, price = ?, num = ? WHERE id = ?;";
                    try{
                        PreparedStatement pre = connections.prepareStatement(sql);
                        pre.setString(1, name);
                        pre.setDouble(2, price);
                        pre.setInt(3, num);
                        pre.setInt(4, id);
                        pre.executeUpdate();
                        JOptionPane.showMessageDialog(null, "商品修改成功！","提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (SQLException e){
                        JOptionPane.showMessageDialog(null,"商品修改失败"+e.getMessage(), "警告",JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null,"该商品不存在，修改失败", "警告",JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"商品修改失败"+e.getMessage(), "警告",JOptionPane.ERROR_MESSAGE);
        }

    }
    public int update(Object[][] goodsTable)
    {
        sql = "SELECT * FROM Goods";
        Object[] tmp;
        int count = 0;
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                tmp = new Object[]{result.getInt("id"), result.getString("name"), result.getDouble("price"), result.getInt("num")};
                goodsTable[count] = tmp;
                count += 1;
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return count;
    }
    public void find(int id)
    {
        sql = "SELECT * FROM Goods";
        try{
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getInt("id") == id){
                    JOptionPane.showMessageDialog(null,"商品名称:"+result.getString("name")+ "  价格:"+
                            result.getDouble("price")+ "  数量:"+result.getInt("num"),"要查找的商品的信息为：", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null,"想要查找的商品不存在！", "警告",JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"商品查找失败"+ e.getMessage(), "警告",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void closeConnection(){
        try{
            connections.close();
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
}

