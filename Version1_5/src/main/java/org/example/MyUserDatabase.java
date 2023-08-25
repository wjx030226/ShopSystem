package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUserDatabase {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private Connection connection;
    Scanner reader;
    public int userId;
    public String userPassword;
    String sql;
    //初始化数据库
    public void initializeDatabase(){
        try{
            connection = DriverManager.getConnection(DB_URL);
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        try{
            Statement statement = connection.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Users"+
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "name TEXT NOT NULL,password TEXT NOT NULL,"+
                    "email TEXT NOT NULL,isUser BOOLEAN NOT NULL);";
            statement.executeUpdate(sql);
            System.out.println("初始化数据库成功！");        }
        catch (SQLException e){
            System.out.println("初始化数据库失败:"+e.getMessage());
        }
    }
    //用户注册
    public void register(boolean isUser)
    {
        System.out.print("请输入身份码：");
        reader = new Scanner(System.in);
        userId = reader.nextInt();
        System.out.print("请输入账号名称：");
        reader = new Scanner(System.in);
        String name = reader.nextLine();
        System.out.print("请输入邮箱：");
        reader = new Scanner(System.in);
        String email = reader.nextLine();
        System.out.print("请输入账号密码：");
        reader = new Scanner(System.in);
        userPassword = reader.nextLine();
        while(true){
            System.out.print("请再次输入账号密码：");
            reader = new Scanner(System.in);
            if(userPassword.equals(reader.nextLine())){
                if (name.length() <= 5)
                    System.out.println("非法用户名输入，注册失败！");
                else if(JudgePassword(userPassword))
                    System.out.println("非法密码输入，注册失败！");
                else{
                    sql = "INSERT INTO Users (id, name, password, email, isUser) VALUES (?, ?, ?, ?, ?);";
                    try{
                        PreparedStatement pre = connection.prepareStatement(sql);
                        pre.setInt(1, userId);
                        pre.setString(2, name);
                        pre.setString(3, getMD5(userPassword));
                        pre.setString(4, email);
                        pre.setBoolean(5,isUser);
                        pre.executeUpdate();
                        System.out.println("注册成功！");
                    } catch (SQLException e) {
                        System.out.println("注册失败"+e.getMessage());
                    }
                }
                return;
            }
            else System.out.println("两次输入密码不匹配，请重新输入！");
        }
    }
    //用户登录
    public int login(boolean isUser)
    {
        System.out.print("请输入身份码：");
        reader = new Scanner(System.in);
        userId = reader.nextInt();
        System.out.print("请输入账号密码：");
        reader = new Scanner(System.in);
        userPassword = getMD5(reader.nextLine());
        sql = "SELECT * FROM Users";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == userId) {
                    if (result.getBoolean("isUser") == isUser){
                        while (!result.getString("password").equals(userPassword)) {
                            System.out.println("账号名或密码错误，请重新输入！");
                            System.out.print("请输入账号密码：");
                            reader = new Scanner(System.in);
                            userPassword = getMD5(reader.nextLine());
                        }
                        System.out.println("登录成功！");
                        return userId;
                    }
                    else{
                        System.out.println("用户与管理员身份混淆，登录失败！");
                        return 0;
                    }
                }
            }
            System.out.println("身份码验证失败！");
            return 0;
        }
        catch (SQLException e){
            System.out.println("登录失败"+e.getMessage());
        }
        return 0;
    }
    //修改密码
    public void change(int id){
        System.out.print("请输入原来的账号密码：");
        reader = new Scanner(System.in);
        userPassword = getMD5(reader.nextLine());
        sql = "SELECT * FROM Users;";
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getInt("id") == id){
                    while(!result.getString("password").equals(userPassword)){
                        System.out.println("密码错误，请重新输入！");
                        System.out.print("请输入原来的账号密码：");
                        reader = new Scanner(System.in);
                        userPassword = getMD5(reader.nextLine());
                    }
                    System.out.print("请输入新的账号密码：");
                    reader = new Scanner(System.in);
                    userPassword = reader.nextLine();
                    while(JudgePassword(userPassword)){
                        System.out.println("非法密码输入，请重新输入！");
                        System.out.print("请输入新的账号密码：");
                        reader = new Scanner(System.in);
                        userPassword = reader.nextLine();
                    }
                    sql = "UPDATE Users SET password = ? WHERE id = ?;";
                    try {
                        PreparedStatement pre = connection.prepareStatement(sql);
                        pre.setString(1, getMD5(userPassword));
                        pre.setInt(2, id);
                        pre.executeUpdate();
                        System.out.println("密码修改成功！");
                        return;
                    }
                    catch (SQLException e){
                        System.out.println("修改密码失败"+e.getMessage());
                    }
                }
            }
        }
        catch (SQLException e){
            System.err.println("用户查找失败"+e.getMessage());
        }
    }
    //重置密码
    public void resent()
    {
        System.out.print("请输入您的身份码：");
        reader = new Scanner(System.in);
        userId = reader.nextInt();
        sql = "SELECT * FROM Users;";
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getInt("id") == userId){
                    System.out.print("请输入验证的邮箱：");
                    reader = new Scanner(System.in);
                    String email= reader.nextLine();
                    while(!result.getString("email").equals(email)){
                        System.out.println("邮箱验证失败！");
                        System.out.print("请重新输入验证的邮箱：");
                        reader = new Scanner(System.in);
                        email = reader.nextLine();
                    }
                    System.out.println("邮箱验证成功！");
                    System.out.print("请输入新的账号密码：");
                    reader = new Scanner(System.in);
                    userPassword = reader.nextLine();
                    while(JudgePassword(userPassword)){
                        System.out.println("非法密码输入，请重新输入！");
                        System.out.print("请输入新的账号密码：");
                        reader = new Scanner(System.in);
                        userPassword = reader.nextLine();
                    }
                    sql = "UPDATE Users SET password = ? WHERE id = ?;";
                    try {
                        PreparedStatement pre = connection.prepareStatement(sql);
                        pre.setString(1, getMD5(userPassword));
                        pre.setInt(2, userId);
                        pre.executeUpdate();
                        System.out.println("密码重置成功！：");
                        return;
                    }
                    catch (SQLException e){
                        System.out.println("修改密码失败"+e.getMessage());
                    }
                }
            }
            System.out.println("用户不存在，请先注册！");
        }
        catch (SQLException e){
            System.err.println("用户查找失败"+e.getMessage());
        }
    }
    //重置用户密码
    public void modify(){
        System.out.print("请输入想要的修改的用户身份码：");
        reader = new Scanner(System.in);
        userId = reader.nextInt();
        sql = "SELECT * FROM Users;";
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getInt("id") == userId){
                    System.out.print("请输入用户的验证邮箱：");
                    reader = new Scanner(System.in);
                    String email= reader.nextLine();
                    while(!result.getString("email").equals(email)){
                        System.out.println("验证邮箱输入有误，请重新输入！");
                        System.out.print("请输入用户的验证的邮箱：");
                        reader = new Scanner(System.in);
                        email = reader.nextLine();
                    }
                    System.out.print("请输入新的账号密码：");
                    reader = new Scanner(System.in);
                    userPassword = reader.nextLine();
                    while(JudgePassword(userPassword)){
                        System.out.println("非法密码输入，请重新输入！");
                        System.out.print("请输入新的账号密码：");
                        reader = new Scanner(System.in);
                        userPassword = reader.nextLine();
                    }
                    sql = "UPDATE Users SET password = ? WHERE id = ?;";
                    try {
                        PreparedStatement pre = connection.prepareStatement(sql);
                        pre.setString(1, getMD5(userPassword));
                        pre.setInt(2, userId);
                        pre.executeUpdate();
                        System.out.println("密码重置成功！");
                        return;
                    }
                    catch (SQLException e){
                        System.out.println("修改密码失败"+e.getMessage());
                    }
                }
            }
            System.out.println("想要重置密码的用户不存在！");
        }
        catch (SQLException e){
            System.out.println("用户查找失败"+e.getMessage());
        }
    }
    //判断密码是否满足要求，即包含大小写字母、数字以及特殊字符
    public boolean JudgePassword(String password)
    {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }
    //删除用户
    public void delete()
    {
        System.out.print("请输入想要删除用户身份码：");
        reader = new Scanner(System.in);
        userId = reader.nextInt();
        System.out.print("确定要删除该用户信息(Y/N)？");
        reader = new Scanner(System.in);
        String judge= reader.nextLine();
        if(judge.equals("Y")){
            sql = "SELECT * FROM Users";
            try {
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    if (result.getInt("id") == userId) {
                        sql = "DELETE FROM Users WHERE id=?;";
                        try{
                            PreparedStatement pre = connection.prepareStatement(sql);
                            pre.setInt(1,userId);
                            pre.executeUpdate();
                            System.out.println("用户已删除！");
                        }
                        catch (SQLException e) {
                            System.out.println("未找到指定用户！"+e.getMessage());
                        }
                        return;
                    }
                }
                System.out.println("未找到指定用户！");
            }
            catch (SQLException e) {
                System.out.println("用户删除失败："+e.getMessage());
            }
        }
        else
            System.out.println("用户删除失败！");
    }
    //查找用户
    public void find()
    {
        System.out.print("请输入想要查找的用户身份码：");
        reader = new Scanner(System.in);
        userId = reader.nextInt();
        sql = "SELECT * FROM Users";
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getInt("id") == userId && result.getBoolean("isUser")){
                    System.out.println("用户查找成功！");
                    System.out.println("要查找的用户的信息为：");
                    System.out.println("用户身份码:"+result.getInt("id")+"  用户名称:"+result.getString("name")+
                            "  密码:"+result.getString("password")+"  邮箱:"+result.getString("email"));
                    return;
                }
            }
            System.out.println("想要查找的用户不存在！");
        }
        catch (SQLException e){
            System.out.println("用户查找失败"+e.getMessage());
        }
    }
    //显示所有用户信息
    public void print()
    {
        sql = "SELECT * FROM Users";
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            System.out.println("用户身份码      用户名称                      加密密码                              邮箱");
            System.out.println("-----------------------------------------------------------------------------------");
            while(result.next()){
                if(result.getInt("isUser") == 1){
                    System.out.println(result.getInt("id")+"       "+result.getString("name")+
                            "       "+result.getString("password")+"       "+result.getString("email"));
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //利用MD5技术对密码进行加密
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
            System.out.println(e.getMessage());
        }
    }
}
