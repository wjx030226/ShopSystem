package org.example;

public class Goods {
    int ID;  //商品编号
    String name;  //商品名称
    double price;  //商品价格
    int buyNum;  //商品购买件数
    public Goods(int ID, String name, double price, int buyNum) {
        this.name = name;
        this.ID = ID;
        this.price = price;
        this.buyNum = buyNum;
    }
}