package cn.czyx007.bean;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 23:24
 */
public class Product {
    //商品条形码
    private String barCode;
    //商品名称
    private String productName;
    //单价
    private Double price;
    //供应商
    private String supply;

    public Product() {
    }

    public Product(String barCode, String productName, Double price, String supply) {
        this.barCode = barCode;
        this.productName = productName;
        this.price = price;
        this.supply = supply;
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

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    @Override
    public String toString() {
        return "Product{" +
                "barCode='" + barCode + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", supply='" + supply + '\'' +
                '}';
    }
}
