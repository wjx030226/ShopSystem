import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class GoodsManager {
    Scanner reader;
    int id;
    //删除商品
    public void delete(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要删除商品编号：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        System.out.print("删除后商品信息无法恢复，确定要删除该商品信息(Y/N)？");
        reader = new Scanner(System.in);
        String judge= reader.nextLine();
        if(judge.equals("Y")){
            for(Goods product:goods) {
                if(product.ID ==id){
                    goods.remove(product);
                    System.out.println("商品删除成功！");
                    return;
                }
            }
            System.out.println("未找到指定商品！");
        }
        else
            System.out.println("商品修改失败！");
    }
    //添加商品
    public void add(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要添加的商品编号：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        System.out.print("请输入想要添加的商品名称：");
        reader = new Scanner(System.in);
        String name = reader.nextLine();
        System.out.print("请输入想要添加的商品价格：");
        reader = new Scanner(System.in);
        double price = reader.nextDouble();
        System.out.print("请输入想要添加的商品数量：");
        reader = new Scanner(System.in);
        int num = reader.nextInt();
        for (Goods product : goods) {
            if (product.ID == id) {
                System.out.println("商品编号已存在，添加失败！");
                return;
            }
        }
        Goods product = new Goods(id, name, price, num, 0,false);
        goods.add(product);
        System.out.println("商品添加成功！");
    }
    //修改商品信息
    public void change(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要修改的商品编号：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        for(Goods product:goods) {
            if(product.ID == id) {
                System.out.print("请输入想要添加的商品名称：");
                reader = new Scanner(System.in);
                String name = reader.nextLine();
                System.out.print("请输入想要添加的商品价格：");
                reader = new Scanner(System.in);
                double price = reader.nextDouble();
                System.out.print("请输入想要添加的商品数量：");
                reader = new Scanner(System.in);
                int num = reader.nextInt();
                product.name = name;
                product.price = price;
                product.num = num;
                System.out.println("商品修改成功！");
                return;
            }
        }
        System.out.println("想要修改的商品不存在！");
    }
    //查找商品信息
    public void find(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要查找的商品编号：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        for(Goods product:goods) {
            if(product.ID == id) {
                System.out.println("要查找的商品的信息为：");
                System.out.println("商品名称:"+product.name+"  价格:"+product.price+"  数量:"+product.num);
                return;
            }
        }
        System.out.println("想要查找的商品不存在!");
    }
    //显示所有商品信息
    public void print(ArrayList<Goods> goods)
    {
        System.out.println("商品编号       名称        价格       数量");
        System.out.println("--------------------------------------");
        for(Goods product:goods)
            System.out.println(product.ID+"       "+product.name+"       "+product.price+"       "+product.num);
    }
    //读取文件
    public void readFile(String path, ArrayList<Goods> goods)
    {
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path), "GBK"));
            String line;
            String[] row;
            input.readLine();
            while((line = input.readLine()) != null){
                row = line.split(",");
                int id = Integer.parseInt(row[0]);
                String name = row[1];
                double price = Double.parseDouble(row[2]);
                int num = Integer.parseInt(row[3]);
                Goods tmp = new Goods(id, name, price, num, 0, false);
                goods.add(tmp);
            }
            input.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    //将商品信息写入文件中
    public void writeFile(String path, ArrayList<Goods> goods)
    {
        try{
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),"GBK"));
            output.write("商品编号,名称,价格,数量");
            output.newLine();
            for(Goods product : goods){
                output.write(product.ID+",");
                output.write(product.name+",");
                output.write(product.price+",");
                output.write(product.num+",");
                output.newLine();
            }
            output.flush();
            output.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

