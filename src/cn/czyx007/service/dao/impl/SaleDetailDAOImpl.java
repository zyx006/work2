package cn.czyx007.service.dao.impl;

import cn.czyx007.bean.SaleDetail;
import cn.czyx007.service.dao.SaleDetailDAO;
import cn.czyx007.service.dao.base.BaseDAO;

import java.sql.Connection;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 23:39
 */
public class SaleDetailDAOImpl extends BaseDAO<SaleDetail> implements SaleDetailDAO {
    @Override
    public int insertSaleDetail(Connection connection, SaleDetail saleDetail) {
        //language=MySQL
        String sql = "insert into sale.tsaledetail values (?,?,?,?,?,?,?)";
        return update(connection,sql,saleDetail.getLsh(),saleDetail.getBarCode(), saleDetail.getProductName(),
                saleDetail.getPrice(),saleDetail.getCount(), saleDetail.getOperator(), saleDetail.getSaleTime());
    }

    @Override
    public int deleteSaleDetail(Connection connection, String lsh) {
        //language=MySQL
        String sql = "delete from sale.tsaledetail where lsh = ?";
        return update(connection,sql,lsh);
    }

    @Override
    public List<SaleDetail> querySaleDetailByLsh(Connection connection, String... lshs) {
        //language=MySQL
        StringBuilder sql = new StringBuilder("select lsh, bar_code barCode, product_name productName, price, count, operator, sale_time saleTime from sale.tsaledetail");
        if (lshs.length > 0){
            sql.append(" where lsh in (");
            for (int i = 0; i < lshs.length; i++) {
                sql.append("?");
                if (i < lshs.length-1)
                    sql.append(",");
            }
            sql.append(")");
            return search(connection,sql.toString(), (Object[]) lshs);
        }
        return search(connection,sql.toString());
    }

    @Override
    public List<SaleDetail> querySaleDetailBySaleTime(Connection connection, String date) {
        //language=MySQL
        String sql = "SELECT lsh, bar_code barCode, product_name productName, price, count, operator, sale_time saleTime FROM sale.tsaledetail WHERE STR_TO_DATE(sale_time,'%Y-%m-%d') = ?";
        return search(connection,sql,date);
    }
}
