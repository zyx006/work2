package cn.czyx007;

import cn.czyx007.bean.User;
import cn.czyx007.service.transaction.Login;
import cn.czyx007.service.transaction.Menu;
import cn.czyx007.util.JDBCUtils;

import java.sql.Connection;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 22:29
 */
public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            User login = Login.login(connection);
            if(login != null){
                Menu.mainMenu(connection,login);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection);
        }
    }
}
