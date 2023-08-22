public class Goods {
    int ID;  //商品编号
    String name;  //商品名称
    double price;  //商品价格
    int num;  //商品库存数量
    int buyNum;  //商品购买件数
    boolean isShopped;  //是否在购物车中
    public Goods(int ID, String name, double price,int num, int buyNum, boolean isshopped) {
        this.name = name;
        this.ID = ID;
        this.price = price;
        this.num = num;
        this.buyNum = buyNum;
        this.isShopped = isshopped;
    }
}