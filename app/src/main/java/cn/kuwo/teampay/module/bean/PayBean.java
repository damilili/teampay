package cn.kuwo.teampay.module.bean;

/**
 * Created by cdm on 2018/10/23.
 * 订单
 */

public class PayBean {

    private int id;//
    private float price;//价格
    private int count = 1;//数量
    private float discount;//优惠价格

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

}
