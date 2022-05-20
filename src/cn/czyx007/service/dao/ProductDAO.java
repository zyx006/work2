package cn.czyx007.service.dao;

import cn.czyx007.bean.Product;

import java.sql.Connection;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 23:38
 */
public interface ProductDAO {
    /**
     * 添加一个新商品
     * @param connection
     * @param product
     * @return
     */
    int insertProduct(Connection connection, Product product);

    /**
     * 根据商品条形码删除一个商品
     * @param connection
     * @param barCode
     * @return
     */
    int deleteProduct(Connection connection,String barCode);

    /**
     * 根据商品名称模糊查询商品数据
     * @param connection
     * @param productName
     * @return
     */
     List<Product> queryProductByName(Connection connection, String productName);

    /**
     * 根据商品条形码查询商品表，若可选参数为0则返回所有商品
     * @param connection
     * @param barCodes
     * @return
     */
    List<Product> queryProductByCode(Connection connection, String ...barCodes);
}
