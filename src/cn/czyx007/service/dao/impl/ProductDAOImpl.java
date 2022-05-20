package cn.czyx007.service.dao.impl;

import cn.czyx007.bean.Product;
import cn.czyx007.service.dao.ProductDAO;
import cn.czyx007.service.dao.base.BaseDAO;

import java.sql.Connection;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 23:39
 */
public class ProductDAOImpl extends BaseDAO<Product> implements ProductDAO {
    @Override
    public int insertProduct(Connection connection, Product product) {
        //language=MySQL
        String sql = "insert into sale.tproduct values (?,?,?,?)";
        return update(connection,sql,product.getBarCode(),product.getProductName(),product.getPrice(),product.getSupply());
    }

    @Override
    public int deleteProduct(Connection connection, String barCode) {
        //language=MySQL
        String sql = "delete from sale.tproduct where bar_code = ?";
        return update(connection,sql,barCode);
    }

    @Override
    public List<Product> queryProductByName(Connection connection, String productName) {
        //language=MySQL
        String sql = "select bar_code barCode, product_name productName, price, supply from sale.tproduct where product_name like ?";
        return search(connection,sql,"%" + productName + "%");
    }

    @Override
    public List<Product> queryProductByCode(Connection connection, String... barCodes) {
        //language=MySQL
        StringBuilder sql = new StringBuilder("select bar_code barCode, product_name productName, price, supply from sale.tproduct");
        if (barCodes.length > 0){
            sql.append(" where bar_code in (");
            for (int i = 0; i < barCodes.length; i++) {
                sql.append("?");
                if (i < barCodes.length-1)
                    sql.append(",");
            }
            sql.append(")");
            return search(connection,sql.toString(), (Object[]) barCodes);
        }
        return search(connection,sql.toString());
    }
}
