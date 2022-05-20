package cn.czyx007.service.transaction;

import cn.czyx007.bean.Product;
import cn.czyx007.bean.SaleDetail;
import cn.czyx007.bean.User;
import cn.czyx007.exception.BarCodeException;
import cn.czyx007.exception.PasswordException;
import cn.czyx007.service.dao.impl.ProductDAOImpl;
import cn.czyx007.service.dao.impl.SaleDetailDAOImpl;
import cn.czyx007.service.dao.impl.UserDAOImpl;
import cn.czyx007.util.BarCodeCheck;
import cn.czyx007.util.MenuUtils;
import cn.czyx007.util.PasswordCheck;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/14 - 15:30
 */
public class SystemFunction {
    /**
     * 收银功能
     *
     * @param connection
     * @param user
     */
    public static void cashier(Connection connection, User user) {
        Scanner scanner = new Scanner(System.in);
        String code;
        while (true) {
            System.out.println("请输入商品条形码（6 位数字字符）：");
            code = scanner.next();
            try {
                if (BarCodeCheck.checkFormat(code)) {
                    List<Product> products = new ProductDAOImpl().queryProductByCode(connection, code);
                    if (products.size() == 1) {
                        Product product = products.get(0);
                        while (true) {
                            try {
                                System.out.println("输入商品数量：");
                                int cnt = Integer.parseInt(scanner.next());
                                if (cnt <= 0) throw new NumberFormatException("输入的不是正整数");

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                int insertSaleDetail = new SaleDetailDAOImpl().insertSaleDetail(connection,
                                        new SaleDetail(String.valueOf(System.currentTimeMillis()),
                                                code, product.getProductName(), product.getPrice(),
                                                cnt, user.getUserName(), sdf.parse(sdf.format(System.currentTimeMillis()))));
                                if (insertSaleDetail == 1) {
                                    System.out.println("成功增加一笔销售数据");
                                    System.out.print("按任意键返回主菜单");
                                    System.in.read();
                                    return;
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }else System.out.println("您输入的商品条形码不存在，请确认后重新输入");
                }
            } catch (BarCodeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 查询统计功能
     *
     * @param connection
     * @param user
     */
    public static void query(Connection connection, User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入销售日期（yyyy-MM-dd）:");
        String dateStr = scanner.next();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            String[] dateDetail = dateStr.split("-");

            List<SaleDetail> saleDetails = new SaleDetailDAOImpl().querySaleDetailBySaleTime(connection, dateStr);

            System.out.println(dateDetail[0] + " 年 " + dateDetail[1] + " 月 " + dateDetail[2] + " 日销售如下");
            System.out.println("\t流水号\t\t商品名称\t 单价\t  数量\t 金额\t\t\t时间\t\t\t\t\t收银员\n" +
                    "=============\t======\t ====\t  ====\t ====\t==============================\t======");
            int productNum = 0;
            double saleMoney = 0;
            if (saleDetails != null) {
                for (int i = 0; i < saleDetails.size(); i++) {
                    saleDetails.get(i).showInfoBySaleTime();
                    System.out.println(user.getUserName());
                    productNum += saleDetails.get(i).getCount();
                    saleMoney += saleDetails.get(i).getCount() * saleDetails.get(i).getPrice();
                }
            }
            System.out.println("销售总数：" + saleDetails.size() + " 商品总件：" + productNum + " 销售总金额：" + saleMoney);
            System.out.println("日期：" + dateDetail[0] + " 年 " + dateDetail[1] + " 月 " + dateDetail[2] + " 日");
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (Exception e) {
            System.out.println("你输入的日期格式不正确，请重新输入");
        }
    }

    /**
     * 商品维护功能
     *
     * @param connection
     * @param user
     */
    public static void goodsMaintenance(Connection connection, User user) {
        if (!("管理员".equals(user.getRole()))) {
            System.out.println("当前用户没有执行该项功能的权限");
            return;
        }

        int opt;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            while (true) {
                System.out.print("===超市商品管理维护====\n" +
                        "1、从 excel 中导入数据\n" +
                        "2、从文本文件导入数据\n" +
                        "3、键盘输入\n" +
                        "4、商品查询\n" +
                        "5、返回主菜单\n" +
                        "请选择（1-5）：");
                opt = MenuUtils.checkInputNumber(scanner, 1, 5);
                if (opt != -1) break;
            }
            switch (opt) {
                case 1://从excel中导入数据
                    GoodsMaintenanceMenu.loadDataFromExcel(connection);
                    break;
                case 2://从文本文件导入数据
                    GoodsMaintenanceMenu.loadDataFromText(connection);
                    break;
                case 3://键盘输入数据
                    GoodsMaintenanceMenu.loadDataFromKeyboard(connection);
                    break;
                case 4://商品查询
                    GoodsMaintenanceMenu.productQuery(connection);
                    break;
                case 5://返回主菜单
                    return;
            }
        }
    }

    /**
     * 修改密码功能
     *
     * @param connection
     * @param user
     */
    public static void changePassword(Connection connection, User user) {
        Scanner scanner = new Scanner(System.in);
        UserDAOImpl userDAO = new UserDAOImpl();
        String pwd;
        while (true) {
            System.out.println("请输入当前用户的原密码：");
            pwd = scanner.next();
            if(userDAO.checkUserPassword(connection, user.getUserName(), pwd) == 0){
                System.out.println("原密码输入不正确，请重新输入");
            }else break;
        }
        System.out.println("请设置新的密码：");
        while (true){
            try {
                pwd = scanner.next();
                if(PasswordCheck.checkFormat(pwd)){
                    System.out.println("请输入确认密码：");
                    String ensurePwd = scanner.next();
                    if (pwd.equals(ensurePwd)){
                        userDAO.updateUser(connection,user.getUserName(),ensurePwd);
                        System.out.println("您已成功修改密码，请谨记");
                        System.out.print("按任意键返回主菜单");
                        System.in.read();
                        return;
                    }else throw new PasswordException("两次输入的密码必须一致，请重新输入密码");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * 数据导出功能
     *
     * @param connection
     */
    public static void dataExport(Connection connection) {
        int opt;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            while (true) {
                System.out.print("===超市销售信息导出====\n" +
                        "1、导出到 excel 文件\n" +
                        "2、导出到文本文件\n" +
                        "3、返回主菜单\n" +
                        "请选择（1-3）：");
                opt = MenuUtils.checkInputNumber(scanner, 1, 3);
                if (opt != -1) break;
            }
            switch (opt) {
                case 1://导出到 excel 文件
                    DataExportMenu.exportDataToExcel(connection);
                    break;
                case 2://导出到文本文件
                    DataExportMenu.exportDataToText(connection);
                    break;
                case 3://返回主菜单
                    return;
            }
        }
    }

    /**
     * 退出功能
     */
    public static void exit() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("您确认退出系统吗（y/n）");
            String opt = scanner.next();
            if ("y".equalsIgnoreCase(opt)) {
                System.out.println("欢迎下次继续使用");
                System.exit(0);
            } else if ("n".equalsIgnoreCase(opt))
                return;
        }
    }
}