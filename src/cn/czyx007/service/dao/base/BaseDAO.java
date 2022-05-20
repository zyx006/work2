package cn.czyx007.service.dao.base;

import cn.czyx007.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 22:22
 */
public class BaseDAO<T> {
    private Class<T> clazz = null;

    //获取当前BaseDAO的子类继承的父类中的泛型
    {//只能用非静态，静态代码块内不能调用非静态
        //获取当前对象的父类的泛型
        //此处this对象是子类的对象
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        //获取了父类的泛型参数
        Type[] typeArguments = paramType.getActualTypeArguments();
        //泛型的第一个参数
        clazz = (Class<T>) typeArguments[0];
    }

    public static int update(Connection connection, String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //返回增删改操作影响的行数
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps);
        }
        return 0;
    }

    /**
     * 针对不同的表的通用的查询操作，返回所有记录
     * ver2.0 考虑到数据库事务
     *
     * @param sql   要执行的sql语句
     * @param args  填充占位符
     * @return List<T>返回查询结果记录
     */
    public List<T> search(Connection connection, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            resultSet = ps.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            ArrayList<T> results = new ArrayList<>();

            while (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    if (columnValue instanceof BigDecimal){
                        columnValue = Double.parseDouble(columnValue.toString());
                    }
                    if (columnValue instanceof LocalDateTime){
                        columnValue = Date.from(((LocalDateTime) columnValue).atZone(ZoneId.systemDefault()).toInstant());
                    }

                    //获取列的别名（没有起别名时默认就是列名）
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    Field field = t.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
//                    System.out.print(columnLabel + ", " + columnValue + "  ");
//                    System.out.println(columnValue.getClass());
                    field.set(t, columnValue);
                }
                results.add(t);
            }

            return results;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, resultSet);
        }
        return null;
    }

    //用于查询特殊值的通用方法
    public <E> E getValue(Connection connection,String sql,Object ...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,ps,rs);
        }
        return null;
    }
}
