package org.example;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUserDatabase {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private Connection connection;
    String sql;
    public MyUserDatabase(){
        try{
            connection = DriverManager.getConnection(DB_URL);
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    public void initializeDatabase(){
        try{
            Statement statement = connection.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Users"+
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "name TEXT NOT NULL,password TEXT NOT NULL,"+
                    "email TEXT NOT NULL,isUser BOOLEAN NOT NULL);";
            statement.executeUpdate(sql);
            System.out.println("初始化数据库成功！");
        }
        catch (SQLException e){
            System.err.println("初始化数据库失败:"+e.getMessage());
        }
    }
    public void register(int id, String name, String password, String email, boolean isUser)
    {
        sql = "INSERT INTO Users (id, name, password, email, isUser) VALUES (?, ?, ?, ?, ?);";
        try{
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            pre.setString(2, name);
            pre.setString(3, getMD5(password));
            pre.setString(4, email);
            pre.setBoolean(5,isUser);
            pre.executeUpdate();
            JOptionPane.showMessageDialog(null,"注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"注册失败："+e.getMessage(), "警告", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean login(int id, String password, boolean isUser)
    {
        sql = "SELECT * FROM Users";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == id) {
                    if (isUser != result.getBoolean("isUser")){
                        JOptionPane.showMessageDialog(null,"身份匹配失败，请重新输入！", "警告", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    else if(!result.getString("password").equals(getMD5(password))) {
                        JOptionPane.showMessageDialog(null,"账号名或密码错误，请重新输入！", "警告", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"登录成功","提示",JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    }
                }
            }
            JOptionPane.showMessageDialog(null,"未找到指定用户，请重新输入！", "警告", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"登录失败！", "警告", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    public boolean modify(int id, String oldPassword, String newPassword, boolean isUser){
        sql = "SELECT * FROM Users;";
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                if (result.getInt("id") == id) {
                    if (isUser != result.getBoolean("isUser")) {
                        JOptionPane.showMessageDialog(null, "身份不匹配,修改密码失败！", "警告", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    if (!result.getString("password").equals(getMD5(oldPassword))) {
                        JOptionPane.showMessageDialog(null, "密码错误,修改密码失败！", "警告", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    sql = "UPDATE Users SET password = ? WHERE id = ?;";
                    try {
                        PreparedStatement pre = connection.prepareStatement(sql);
                        pre.setString(1, getMD5(newPassword));
                        pre.setInt(2, id);
                        pre.executeUpdate();
                        JOptionPane.showMessageDialog(null, "密码修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "修改密码失败" + e.getMessage(), "警告", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "用户查找失败"+e.getMessage(), "警告",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    public boolean resent(int id, String password, String email)
    {
        sql = "SELECT * FROM Users;";
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getInt("id") == id){
                    if(!result.getString("email").equals(email)){
                        JOptionPane.showMessageDialog(null, "邮箱验证失败！", "警告",JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"邮箱验证成功！","提示", JOptionPane.INFORMATION_MESSAGE);
                        sql = "UPDATE Users SET password = ? WHERE id = ?;";
                        try {
                            PreparedStatement pre = connection.prepareStatement(sql);
                            pre.setString(1, getMD5(password));
                            pre.setInt(2, id);
                            pre.executeUpdate();
                            JOptionPane.showMessageDialog(null,"密码重置成功！","提示", JOptionPane.INFORMATION_MESSAGE);
                            return true;
                        }
                        catch (SQLException e){
                            JOptionPane.showMessageDialog(null, "密码重置失败"+e.getMessage(), "警告",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "用户不存在，请先注册！", "警告",JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "用户查找失败"+e.getMessage(), "警告",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    public boolean JudgePassword(String password)
    {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }
    public void delete(int id)
    {
        JPanel userPane=new JPanel();
        int order=JOptionPane.showConfirmDialog(userPane,"确定要删除该用户信息！","提示", JOptionPane.YES_NO_OPTION);
        if(order==JOptionPane.YES_OPTION){
            sql = "SELECT * FROM Users;";
            try {
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    if (result.getInt("id") == id) {
                        String sql = "DELETE FROM Users WHERE id=?;";
                        try{
                            PreparedStatement pre = connection.prepareStatement(sql);
                            pre.setInt(1,id);
                            pre.executeUpdate();
                            JOptionPane.showMessageDialog(null, "用户已删除！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "未找到指定用户！"+e.getMessage(),"警告", JOptionPane.ERROR_MESSAGE);
                        }
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "该用户不存在，用户删除失败！","警告", JOptionPane.ERROR_MESSAGE);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "未找到指定用户！"+e.getMessage(),"警告", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void find(int id)
    {
        sql = "SELECT * FROM Users";
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getInt("id") == id && result.getBoolean("isUser")){
                    JOptionPane.showMessageDialog(null, "用户身份码:"+result.getInt("id")+"  用户名称:"+
                            result.getString("name")+ "  加密密码:"+result.getString("password")+"  邮箱:"+
                            result.getString("email"),"要查找的用户的信息：", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null,"想要查找的用户不存在！","警告", JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"用户查找失败"+e.getMessage(),"警告", JOptionPane.ERROR_MESSAGE);
        }
    }
    public int update(Object[][] usersTable)
    {
        sql = "SELECT * FROM Users";
        Object[] tmp;
        int count = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getBoolean("isUser")){
                    tmp = new Object[]{result.getInt("id"), result.getString("name"), result.getString("password"), result.getString("email")};
                    usersTable[count] = tmp;
                    count += 1;
                }
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return count;
    }
    public String getMD5(String str)
    {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(str.getBytes());
            byte[] mdBytes = md.digest();
            StringBuilder passwordMD = new StringBuilder();
            for (byte mdByte : mdBytes) {
                int tmp;
                if (mdByte < 0) tmp = 256 + mdByte;
                else tmp = mdByte;
                if (tmp < 16) passwordMD.append("0");
                passwordMD.append(Integer.toString(tmp, 16));
            }
            return passwordMD.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
}




