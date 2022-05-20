package cn.czyx007.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 22:23
 */
public class JDBCUtils {
    /**
     * 获取数据库的连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
        //读取配置文件中的4个基本信息
        Properties pros = new Properties();
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        //加载驱动
        Class.forName(driverClass);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    public static void closeResource(Connection connection){
        if (connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库连接和Statement的操作
     * @param connection
     * @param ps
     */
    public static void closeResource(Connection connection, Statement ps){
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库连接和Statement的操作以及结果集
     * @param connection
     * @param ps
     */
    public static void closeResource(Connection connection, Statement ps, ResultSet rs){
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
