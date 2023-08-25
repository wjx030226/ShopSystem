package org.example;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.example.MyGoodsDatabase.connections;

public class MyCarDatabase {
    String sql;
    public void car_delete(int id)
    {
        sql = "SELECT * FROM Goods";
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == id){
                    if (result.getBoolean("isShopped")){
                        sql = "UPDATE Goods SET isShopped = ? WHERE id = ?;";
                        try{
                            PreparedStatement pre = connections.prepareStatement(sql);
                            pre.setBoolean(1, false);
                            pre.setInt(2, id);
                            pre.executeUpdate();
                            JOptionPane.showMessageDialog(null, "商品删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch (SQLException e){
                            JOptionPane.showMessageDialog(null, "商品删除失败"+e.getMessage(), "警告", JOptionPane.ERROR_MESSAGE);
                            System.out.println();
                        }
                    }
                    else JOptionPane.showMessageDialog(null, "商品不在购物车中，删除失败！", "警告", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "想要删除的商品编号不存在，删除失败！", "警告", JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "商品删除失败"+e.getMessage(), "警告", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void car_add(int id)
    {
        sql = "SELECT * FROM Goods";
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == id) {
                    if (!result.getBoolean("isShopped")) {
                        sql = "UPDATE Goods SET isShopped = ?, buyNum = ? WHERE id = ?;";
                        try{
                            PreparedStatement pre = connections.prepareStatement(sql);
                            pre.setBoolean(1, true);
                            pre.setInt(2, 1);
                            pre.setInt(3, id);
                            pre.executeUpdate();
                            JOptionPane.showMessageDialog(null, "商品添加成功！","提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch (SQLException e){
                            JOptionPane.showMessageDialog(null, "商品添加失败:"+e.getMessage(),"警告",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else JOptionPane.showMessageDialog(null, "商品已在购物车中，添加失败！","警告",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "想要添加的商品编号不存在，添加失败！","警告",JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "商品添加失败："+e.getMessage(),"警告",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void car_change(int id, int num)
    {
        sql = "SELECT * FROM Goods";
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getInt("id") == id) {
                    if (result.getBoolean("isShopped")) {
                        sql = "UPDATE Goods SET buyNum = ? WHERE id = ?;";
                        try{
                            PreparedStatement pre = connections.prepareStatement(sql);
                            pre.setInt(1,num);
                            pre.setInt(2, id);
                            pre.executeUpdate();
                            JOptionPane.showMessageDialog(null, "商品修改成功！","提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch (SQLException e){
                            JOptionPane.showMessageDialog(null, "商品修改失败！","警告",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else JOptionPane.showMessageDialog(null, "商品不在购物车中，修改失败！","警告",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "商品修改失败！","警告",JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "想要修改的商品编号不存在，修改失败！","警告",JOptionPane.ERROR_MESSAGE);
        }
    }
    public int car_update(Object[][] carsTable)
    {
        sql = "SELECT * FROM Goods";
        Object[] tmp;
        int count = 0;
        try {
            Statement statement = connections.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if (result.getBoolean("isShopped")){
                    tmp = new Object[]{result.getInt("id"), result.getString("name"), result.getDouble("price"), result.getInt("buyNum")};
                    carsTable[count] = tmp;
                    count += 1;
                }
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage(), "警告", JOptionPane.ERROR_MESSAGE);
        }
        return count;
    }
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
            JOptionPane.showMessageDialog(null,"购物车中商品总数量为"+totalNum+"\n商品总价为"+String.format("%.2f",totalPrice), "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage(), "警告", JOptionPane.ERROR_MESSAGE);
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

