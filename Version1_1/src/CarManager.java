import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.io.*;

public class CarManager {
    Scanner reader;
    public int goodsId;
    //将商品移出购物车
    public void car_delete(ArrayList<Goods> goods)
    {
        System.out.print("请输入想要从购物车中删除的商品编号：");
        reader = new Scanner(System.in);
        goodsId = reader.nextInt();
        for(Goods product:goods) {
            if(product.ID == goodsId){
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
        goodsId = reader.nextInt();
        for(Goods product:goods) {
            if(product.ID == goodsId){
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
        goodsId = reader.nextInt();
        int num;
        for(Goods product:goods) {
            if(product.ID == goodsId) {
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
    //查看用户的购物历史
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
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line, date="";
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
                        Goods tmp = new Goods(id, name, price, 0, num, false);
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
    //将用户购物信息写入文件
    public void writeFile(String path, ArrayList<ShopCars> history)
    {
        try{
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
            for(ShopCars total : history){
                output.write("===============================================");
                output.newLine();
                output.write("购物时间："+total.time);
                output.newLine();
                output.write("购物者身份码："+total.userId+" 商品总数量："+total.tNum+" 商品总价："+String.format("%.2f",total.tPrice));
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
