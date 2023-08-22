import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class GoodsManager {
    Scanner reader;
    public int goodsId;
    //删除商品
    public void delete(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要删除商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        System.out.print("删除后商品信息无法恢复，确定要删除该商品信息(Y/N)？");
        reader = new Scanner(System.in);
        String judge= reader.nextLine();
        if(judge.equals("Y")){
            for(Goods product:goods) {
                if(product.ID ==goodsId){
                    goods.remove(product);
                    System.out.println("商品删除成功！");
                    return;
                }
            }
            System.out.println("未找到指定商品！");
        }
        else
            System.out.println("商品删除失败！");
    }
    //添加商品
    public void add(ArrayList<Goods> goods)
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
        for (Goods product : goods) {
            if (product.ID == goodsId) {
                System.out.println("商品编号已存在，添加失败！");
                return;
            }
        }
        Goods product = new Goods(goodsId, name, price, num, 0,false);
        goods.add(product);
        System.out.println("商品添加成功！");
    }
    //修改商品信息
    public void change(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要修改的商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        for(Goods product:goods) {
            if(product.ID == goodsId) {
                System.out.print("请输入想要修改的商品名称：");
                reader = new Scanner(System.in);
                String name = reader.nextLine();
                System.out.print("请输入想要修改的商品价格：");
                reader = new Scanner(System.in);
                double price = reader.nextDouble();
                System.out.print("请输入想要修改的商品数量：");
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
        goodsId = reader.nextInt();
        for(Goods product:goods) {
            if(product.ID == goodsId) {
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
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line;
            for(int i = 0; i < 2; i++)
                input.readLine();
            while ((line = input.readLine()) != null) {
                String[] split = line.split("        ");
                int id = Integer.parseInt(split[0]);
                String name = split[1];
                double price = Double.parseDouble(split[2]);
                int num = Integer.parseInt(split[3]);
                Goods tmp = new Goods(id, name, price, num, 0, false);
                goods.add(tmp);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    //将商品信息写入文件
    public void writeFile(String path, ArrayList<Goods> goods)
    {
        try{
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
            output.write("商品编号     名称         价格       数量");
            output.newLine();
            output.write("----------------------------------------");
            output.newLine();
            for(Goods product:goods){
                output.write(product.ID+"        "+product.name+"        "+product.price+"        "+product.num);
                output.newLine();
            }
            output.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
