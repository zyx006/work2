package cn.czyx007.service.dao.impl;

import cn.czyx007.bean.User;
import cn.czyx007.service.dao.UserDAO;
import cn.czyx007.service.dao.base.BaseDAO;

import java.sql.Connection;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 23:39
 */
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
    @Override
    public long checkUserPassword(Connection connection, String userName, String password) {
        //language=MySQL
        String sql = "select password = sha1(?) from sale.tuser where user_name = ?";
        Object value = getValue(connection, sql, password, userName);
        if (value == null)
            return 0;
        return (long) value;
    }

    @Override
    public int insertUser(Connection connection, User user) {
        //language=MySQL
        String sql = "insert into sale.tuser values (?,sha1(?),?,?)";
        return update(connection,sql,user.getUserName(),user.getPassword(),user.getChrName(),user.getRole());
    }

    @Override
    public int deleteUser(Connection connection, String userName) {
        //language=MySQL
        String sql = "delete from sale.tuser where user_name = ?";
        return update(connection,sql,userName);
    }

    @Override
    public int updateUser(Connection connection, String userName, String newPassword) {
        //language=MySQL
        String sql = "update sale.tuser set password = sha1(?) where user_name = ?";
        return update(connection,sql,newPassword,userName);
    }

    @Override
    public List<User> queryUser(Connection connection, String... userNames) {
        //language=MySQL
        StringBuilder sql = new StringBuilder("select user_name userName, password, chr_name chrName, role from sale.tuser");
        if (userNames.length > 0){
            sql.append(" where user_name in (");
            for (int i = 0; i < userNames.length; i++) {
                sql.append("?");
                if (i < userNames.length-1)
                    sql.append(",");
            }
            sql.append(")");
        }
        return search(connection,sql.toString(), (Object[]) userNames);
    }
}
