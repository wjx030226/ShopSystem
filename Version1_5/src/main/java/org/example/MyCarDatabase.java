package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static org.example.MyGoodsDatabase.connections;

public class MyCarDatabase {
    Scanner reader;
    public int goodsId;
    String sql;
    //从购物车中移出商品
    public void car_delete()
    {
        System.out.print("请输入想要从购物车中删除的商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        sql = "SELECT * FROM Goods";
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == goodsId){
                    if (result.getBoolean("isShopped")){
                        sql = "UPDATE Goods SET isShopped = ? WHERE id = ?;";
                        try{
                            PreparedStatement pre = connections.prepareStatement(sql);
                            pre.setBoolean(1, false);
                            pre.setInt(2, goodsId);
                            pre.executeUpdate();
                            System.out.println("商品删除成功！");
                        }
                        catch (SQLException e){
                            System.out.println("商品删除失败"+e.getMessage());
                        }
                    }
                    else System.out.println("商品不在购物车中，删除失败！");
                    return;
                }
            }
            System.out.println("想要删除的商品编号不存在，删除失败！");
        }
        catch (SQLException e){
            System.err.println("商品删除失败"+e.getMessage());
        }
    }
    //将商品添加到购物车中
    public void car_add()
    {
        System.out.print("请输入想要从购物车中添加的商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        sql = "SELECT * FROM Goods";
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == goodsId){
                    if (!result.getBoolean("isShopped")){
                        sql = "UPDATE Goods SET isShopped = ?, buyNum = ? WHERE id = ?;";
                        try{
                            PreparedStatement pre = connections.prepareStatement(sql);
                            pre.setBoolean(1, true);
                            pre.setInt(2, 1);
                            pre.setInt(3, goodsId);
                            pre.executeUpdate();
                            System.out.println("商品添加成功！");
                        }
                        catch (SQLException e){
                            System.out.println("商品添加失败"+e.getMessage());
                        }
                    }
                    else System.out.println("商品已在购物车中，添加失败！");
                    return;
                }
            }
            System.out.println("想要添加的商品编号不存在，添加失败！");
        }
        catch (SQLException e){
            System.err.println("商品添加失败"+e.getMessage());
        }
    }
    //修改购物车中商品信息
    public void car_change()
    {
        System.out.print("请输入想要从购物车中修改的商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        sql = "SELECT * FROM Goods";
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == goodsId){
                    if (result.getBoolean("isShopped")){
                        sql = "UPDATE Goods SET buyNum = ? WHERE id = ?;";
                        try{
                            PreparedStatement pre = connections.prepareStatement(sql);
                            System.out.print("请输入想要从购物车中添加的商品购买数量：");
                            reader = new Scanner(System.in);
                            pre.setInt(1, reader.nextInt());
                            pre.setInt(2, goodsId);
                            pre.executeUpdate();
                            System.out.println("商品修改成功！");
                        }
                        catch (SQLException e){
                            System.out.println("商品修改失败"+e.getMessage());
                        }
                    }
                    else System.out.println("商品不在购物车中，修改失败！");
                    return;
                }
            }
            System.out.println("想要修改的商品编号不存在，修改失败！");
        }
        catch (SQLException e){
            System.err.println("商品删除失败"+e.getMessage());
        }

    }
    //模拟结账
    public void car_account(ArrayList<ShopCars> history, int id)
    {
        double totalPrice = 0;
        int totalNum = 0;
        ArrayList<Goods> shopGoods = new ArrayList<>();
        sql = "SELECT * FROM Goods";
        try{
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getBoolean("isShopped")){
                    totalNum += result.getInt("buyNum");
                    totalPrice += result.getDouble("price") * result.getInt("buyNum");
                    sql = "UPDATE Goods SET num = ? WHERE id = ?;";
                    PreparedStatement pre = connections.prepareStatement(sql);
                    pre.setInt(2,result.getInt("num")-result.getInt("buyNum"));
                    pre.setInt(1,id);
                    pre.executeUpdate();
                    Goods tmp = new Goods(result.getInt("id"), result.getString("name"),result.getDouble("price"),result.getInt("buyNum"));
                    shopGoods.add(tmp);
                }
            }
            Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
            ShopCars shop = new ShopCars(id, dateFormat.format(date), totalNum, totalPrice, shopGoods);
            history.add(shop);
            System.out.println("购物车中商品总数量为"+totalNum+"\n商品总价为"+String.format("%.2f",totalPrice));
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //查看购物历史
    public void car_history(ArrayList<ShopCars> history, int id)
    {
        for(ShopCars total : history){
            if(total.userID == id){
                System.out.println("===============================================");
                System.out.println("购买时间："+total.time);
                System.out.println("商品总数量："+total.tNum+"   商品总价："+String.format("%.2f",total.tPrice));
                System.out.println("商品编号       名称        价格       购买数量");
                System.out.println("-----------------------------------------------");
                for(Goods product: total.goods){
                    System.out.println(product.ID+"          "+product.name+"         "+product.price+"         "+product.buyNum);
                }
            }
        }
    }
    //显示购物车中商品信息
    public void car_print()
    {
        sql = "SELECT * FROM Goods";
        try{
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            System.out.println("购物车中含有的商品信息为：");
            System.out.println("商品编号       名称        价格       购买数量");
            System.out.println("-----------------------------------------");
            while(result.next()){
                if(result.getBoolean("isShopped")){
                    System.out.println(result.getInt("id")+"       "+result.getString("name")+
                            "       "+result.getDouble("price")+"       "+result.getInt("buyNum"));
                }
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //读取文件
    public void readFile(String path, ArrayList<ShopCars> history)
    {
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line, date = "";
            int tid = 0, tNum = 0;
            double tPrice = 0;
            ArrayList<Goods> goods;
            while((line = input.readLine()) != null){
                if(line.equals("===============================================")){
                    line = input.readLine();
                    String[] time = line.split("：");
                    date = time[1];
                    line = input.readLine();
                    String[] split = line.split("：| ");
                    tid = Integer.parseInt(split[1]);
                    tNum = Integer.parseInt(split[3]);
                    tPrice = Double.parseDouble(split[5]);
                    input.readLine();
                }
                else if(line.equals("-----------------------------------------------")){
                    line= input.readLine();
                    goods = new ArrayList<>();
                    while(line != null && !line.equals("===============================================")){
                        String[] split = line.split("          ");
                        int id = Integer.parseInt(split[0]);
                        String name = split[1];
                        double price = Double.parseDouble(split[2]);
                        int num = Integer.parseInt(split[3]);
                        Goods tmp = new Goods(id, name, price, num);
                        goods.add(tmp);
                        line = input.readLine();
                    }
                    ShopCars car = new ShopCars(tid, date, tNum, tPrice,goods);
                    history.add(car);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    //将购物信息写入文件
    public void writeFile(String path, ArrayList<ShopCars> history)
    {
        try{
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
            for(ShopCars total : history){
                output.write("===============================================");
                output.newLine();
                output.write("购物时间："+total.time);
                output.newLine();
                output.write("购物者身份码："+total.userID+" 商品总数量："+total.tNum+" 商品总价："+String.format("%.2f",total.tPrice));
                output.newLine();
                output.write("商品编号       名称           价格         购买数量");
                output.newLine();
                output.write("-----------------------------------------------");
                output.newLine();
                for(Goods product: total.goods){
                    output.write(product.ID+"          "+product.name+"          "+product.price+"          "+product.buyNum);
                    output.newLine();
                }
                output.write("===============================================");
                output.newLine();
            }
            output.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
