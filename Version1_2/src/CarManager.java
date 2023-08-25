import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class CarManager {
    Scanner reader;
    int id;
    //从购物车中移出商品
    public void car_delete(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要从购物车中删除的商品编号：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        for(Goods product:goods) {
            if(product.ID == id){
                if (!product.isShopped)
                    System.out.println("该商品不在购物车中！");
                else{
                    product.isShopped = false;
                    System.out.println("商品删除成功！");
                }
                return;
            }
        }
        System.out.println("想要从购物车中删除的商品不存在！");
    }
    //将商品添加到购物车
    public void car_add(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要从购物车中添加的商品编号：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        for(Goods product:goods) {
            if(product.ID == id){
                product.buyNum = 1;
                if (product.isShopped)
                    System.out.println("商品已添加到了购物车中！");
                else {
                    product.isShopped = true;
                    System.out.println("商品添加成功！");
                }
                return;
            }
        }
        System.out.println("想要从购物车中添加的商品不存在！");
    }
    //修改购物车中商品信息
    public void car_change(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要从购物车中修改的商品编号：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        int num;
        for(Goods product:goods) {
            if(product.ID == id) {
                if (product.isShopped){
                    System.out.print("请输入想要从购物车中添加的商品购买数量：");
                    reader = new Scanner(System.in);
                    num = reader.nextInt();
                    product.buyNum = num;
                    System.out.println("商品修改成功！");
                }
                else
                    System.out.println("该商品不在购物车中！");
                return;
            }
        }
        System.out.println("想要从购物车中修改的商品不存在！");
    }
    //模拟结账
    public void car_account(ArrayList<Goods> goods, ArrayList<ShopCars> history, int id)
    {
        double totalPrice = 0;
        int totalNum = 0;
        ArrayList<Goods> shopGoods = new ArrayList<>();
        for(Goods product:goods) {
            if (product.isShopped){
                totalNum += product.buyNum;
                product.num -= product.buyNum;
                totalPrice += product.price * product.buyNum;
                Goods tmp = new Goods(product.ID, product.name, product.price, product.num, product.buyNum, true);
                shopGoods.add(tmp);
            }
        }
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        ShopCars shop = new ShopCars(id, dateFormat.format(date), totalNum, totalPrice, shopGoods);
        history.add(shop);
        System.out.println("购物车中商品总数量为"+totalNum+"\n商品总价为"+String.format("%.2f",totalPrice));
    }
    //查看用户购物信息
    public void car_history(ArrayList<ShopCars> history, int id)
    {
        for(ShopCars total : history){
            if(total.userId == id){
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
    public void car_print(ArrayList<Goods> goods)
    {
        System.out.println("购物车中含有的商品信息为：");
        System.out.println("商品编号       名称        价格       购买数量");
        System.out.println("-----------------------------------------");
        for(Goods product:goods) {
            if (product.isShopped)
                System.out.println(product.ID+"          "+product.name+"         "+product.price+"         "+product.buyNum);
        }
    }
    //读取文件
    public void readFile(String path, ArrayList<ShopCars> history)
    {
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path), "GBK"));
            ArrayList<Goods> goods;
            String line, date = "";
            String[] row;
            int tid = 0, tNum = 0;
            double tPrice = 0;
            while((line = input.readLine()) != null){
                row = line.split(",");
                if(row[0].equals("购物时间")) date = row[1];
                else if(row[0].equals("购买者身份码")) tid = Integer.parseInt(row[1]);
                else if(row[0].equals("商品总数量")){
                    tNum = Integer.parseInt(row[1]);
                    tPrice = Double.parseDouble(row[3]);
                    input.readLine();
                }
                else{
                    goods = new ArrayList<>();
                    while(!row[0].equals("购买者身份码")){
                        int id = Integer.parseInt(row[0]);
                        String name = row[1];
                        double price = Double.parseDouble(row[2]);
                        int num = Integer.parseInt(row[3]);
                        Goods tmp = new Goods(id, name, price, 0, num, false);
                        goods.add(tmp);
                        line = input.readLine();
                        if (line == null) break;
                        row = line.split(",");
                    }
                    ShopCars car = new ShopCars(tid, date, tNum, tPrice,goods);
                    history.add(car);
                    if (line != null)
                        tid = Integer.parseInt(row[1]);
                }
            }
            input.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    //将购物信息写入文件
    public void writeFile(String path, ArrayList<ShopCars> history)
    {
        try{
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "GBK"));
            for(ShopCars total : history){
                output.write("购买者身份码,"+total.userId);
                output.newLine();
                output.write("购物时间,"+total.time);
                output.newLine();
                output.write("商品总数量,"+total.tNum+",");
                output.write("商品总价,"+String.format("%.2f",total.tPrice)+",");
                output.newLine();
                output.write("商品编号,");
                output.write("名称,");
                output.write("价格,");
                output.write("购买数量,");
                output.newLine();
                for(Goods product : total.goods){
                    output.write(product.ID+",");
                    output.write(product.name+",");
                    output.write(product.price+",");
                    output.write(product.buyNum+",");
                    output.newLine();
                }
            }
            output.flush();
            output.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
