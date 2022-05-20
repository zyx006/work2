package cn.czyx007.service.transaction;

import cn.czyx007.bean.User;
import cn.czyx007.util.MenuUtils;

import java.sql.Connection;
import java.util.Scanner;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/14 - 12:15
 */
public class Menu {
    public static void mainMenu(Connection connection, User user) {
        Scanner scanner = new Scanner(System.in);
        int opt;
        while (true) {
            while (true) {
                System.out.printf("\n===超市收银系统=== \n" +
                        "1、收银\n" +
                        "2、查询统计\n" +
                        "3、商品维护\n" +
                        "4、修改密码\n" +
                        "5、数据导出\n" +
                        "6、退出\n" +
                        "当前收银员：" + user.getChrName() + "\n请选择（1-6）：");
                opt = MenuUtils.checkInputNumber(scanner,1,6);
                if (opt != -1) break;
            }
            switch (opt) {
                case 1:
                    SystemFunction.cashier(connection, user);
                    break;
                case 2:
                    SystemFunction.query(connection,user);
                    break;
                case 3:
                    SystemFunction.goodsMaintenance(connection,user);
                    break;
                case 4:
                    SystemFunction.changePassword(connection,user);
                    break;
                case 5:
                    SystemFunction.dataExport(connection);
                    break;
                case 6:
                    SystemFunction.exit();
                    break;
            }
        }
    }
}
