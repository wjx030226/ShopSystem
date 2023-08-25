package org.example;
import java.util.ArrayList;

public class ShopCars {
    int userID;  //购物用户身份码
    String time; //购买时间
    int tNum;  //商品总数量
    double tPrice;  //商品总价
    ArrayList<Goods> goods;  //商品信息
    public ShopCars(int id, String time, int num, double price, ArrayList<Goods> goods) {
        this.userID = id;
        this.time = time;
        this.tNum = num;
        this.tPrice = price;
        this.goods = goods;
    }
}