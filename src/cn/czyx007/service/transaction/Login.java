package cn.czyx007.service.transaction;

import cn.czyx007.bean.User;
import cn.czyx007.service.dao.impl.UserDAOImpl;

import java.sql.Connection;
import java.util.Scanner;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/14 - 12:15
 */
public class Login {
    private static String userName;
    private static String password;

    public static void getUserNameAndPassword(Scanner scanner){
        System.out.print("请输入用户名：");
        userName = scanner.next();
        System.out.print("请输入密码：");
        password = scanner.next();
    }

    public static User login(Connection connection){
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎使用超市收银系统，请登陆：");
        getUserNameAndPassword(scanner);

        int loginCnt = 0;
        while (true) {
            if (loginSystem(connection) == 1) {
                System.out.println("登录成功");
                return new UserDAOImpl().queryUser(connection,userName).get(0);
            }else {
                loginCnt++;
                if (loginCnt < 3) {
                    System.out.println("用户名或密码不正确，请重新输入");
                    getUserNameAndPassword(scanner);
                }
                else {
                    System.out.println("最多只能尝试 3 次");
                    return null;
                }
            }
        }
    }

    public static long loginSystem(Connection connection){
        UserDAOImpl userDAO = new UserDAOImpl();
        return userDAO.checkUserPassword(connection,userName,password);
    }
}
