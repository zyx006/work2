package cn.czyx007.bean;

import java.util.Date;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 23:24
 */
public class SaleDetail {
    //流水号
    private String lsh;
    //商品条形码
    private String barCode;
    //商品名称
    private String productName;
    //商品单价
    private Double price;
    //数量
    private Integer count;
    //收银员
    private String operator;
    //销售时间
    private Date saleTime;

    public SaleDetail() {
    }

    public SaleDetail(String lsh, String barCode, String productName, Double price, Integer count, String operator, Date saleTime) {
        this.lsh = lsh;
        this.barCode = barCode;
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.operator = operator;
        this.saleTime = saleTime;
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    @Override
    public String toString() {
        return "SaleDetail{" +
                "lsh='" + lsh + '\'' +
                ", barCode='" + barCode + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", operator='" + operator + '\'' +
                ", saleTime=" + saleTime +
                '}';
    }

    public void showInfoBySaleTime(){
        System.out.print(lsh + "\t" + productName + "\t");
        System.out.printf("% 4.2f\t% 4d\t% 4.2f\t",price,count,price*count);
        System.out.print(saleTime + "\t");
    }
}
