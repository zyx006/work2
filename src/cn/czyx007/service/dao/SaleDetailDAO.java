package cn.czyx007.service.dao;

import cn.czyx007.bean.SaleDetail;

import java.sql.Connection;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 23:38
 */
public interface SaleDetailDAO {
    /**
     * 添加一个销售明细
     * @param connection
     * @param saleDetail
     * @return
     */
    int insertSaleDetail(Connection connection, SaleDetail saleDetail);

    /**
     * 根据流水号删除一个销售明细
     * @param connection
     * @param lsh
     * @return
     */
    int deleteSaleDetail(Connection connection,String lsh);

    /**
     * 根据流水号查询销售明细表，若可选参数为0则返回所有销售明细
     * @param connection
     * @param lshs
     * @return
     */
    List<SaleDetail> querySaleDetailByLsh(Connection connection, String ...lshs);

    /**
     * 根据销售时间查询销售明细表
     * @param connection
     * @param date
     * @return
     */
    List<SaleDetail> querySaleDetailBySaleTime(Connection connection, String date);
}
