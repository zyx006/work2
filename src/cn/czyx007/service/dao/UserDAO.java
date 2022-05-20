package cn.czyx007.service.dao;

import cn.czyx007.bean.User;

import java.sql.Connection;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 23:37
 */
public interface UserDAO {
    /**
     * 检查用户名与密码是否匹配，用于登录校验
     * @param connection
     * @param userName
     * @param password
     * @return
     */
    long checkUserPassword(Connection connection,String userName,String password);

    /**
     * 插入一个新用户
     * @param connection
     * @param user
     * @return
     */
    int insertUser(Connection connection, User user);

    /**
     * 根据用户登录名删除一个用户
     * @param connection
     * @param userName
     * @return
     */
    int deleteUser(Connection connection,String userName);

    /**
     * 根据用户登录名修改用户密码
     * @param connection
     * @param userName
     * @param newPassword
     * @return
     */
    int updateUser(Connection connection,String userName,String newPassword);

    /**
     * 根据用户登录名查询用户表，若可选参数为0则返回所有用户
     * @param connection
     * @param userNames
     * @return
     */
    List<User> queryUser(Connection connection,String ...userNames);
}
