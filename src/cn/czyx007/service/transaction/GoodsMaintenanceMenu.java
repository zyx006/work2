package cn.czyx007.service.transaction;

import cn.czyx007.bean.Product;
import cn.czyx007.exception.ProductException;
import cn.czyx007.service.dao.impl.ProductDAOImpl;
import cn.czyx007.util.BarCodeCheck;
import jxl.Sheet;
import jxl.Workbook;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/15 - 10:57
 */
public class GoodsMaintenanceMenu {
    /**
     * 从excel导入数据
     *
     * @param connection
     */
    public static void loadDataFromExcel(Connection connection) {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("src/product.xls"));
            Sheet sheet = workbook.getSheet(0);

            ArrayList<Product> products = new ArrayList<>();
            Product product;
            int rows = sheet.getRows();
            int columns = sheet.getColumns();
            for (int i = 1; i < rows; i++) {
                product = new Product();
                for (int j = 0; j < columns; j++) {
                    String columnLabel = sheet.getCell(j, 0).getContents();
                    Object columnValue = sheet.getCell(j, i).getContents();
                    if ("price".equals(columnLabel))
                        columnValue = Double.parseDouble((String) columnValue);

                    Field field = product.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(product, columnValue);
                }
                products.add(product);
            }

            int cnt = 0;
            ProductDAOImpl productDAO = new ProductDAOImpl();
            for (int i = 0; i < products.size(); i++) {
                List<Product> queryProduct = productDAO.queryProductByCode(connection, products.get(i).getBarCode());
                if (queryProduct.size() == 0) {
                    productDAO.insertProduct(connection, products.get(i));
                    cnt++;
                }
            }
            System.out.println("成功从 excel 文件导入 " + cnt + " 条商品数据");
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (Exception e) {
            System.out.println("数据导入失败！");
            e.printStackTrace();
        }finally {
            if (workbook != null){
                try {
                    workbook.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从txt文件导入数据
     *
     * @param connection
     */
    public static void loadDataFromText(Connection connection) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/product.txt"))) {
            int cnt = 0;
            String line;
            String[] titles = null;
            ArrayList<String> datas = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                cnt++;
                if (cnt == 1) {
                    titles = line.split(" ");
                } else {
                    datas.add(line);
                }
            }

            ArrayList<Product> products = new ArrayList<>();
            Product product;
            for (int i = 0; i < datas.size(); i++) {
                product = new Product();
                String[] rowData = datas.get(i).split(" ");
                for (int j = 0; j < titles.length; j++) {
                    String columnLabel = titles[j];
                    Object columnValue = rowData[j];
                    if ("price".equals(columnLabel))
                        columnValue = Double.parseDouble((String) columnValue);

                    Field field = product.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(product, columnValue);
                }
                products.add(product);
            }

            cnt = 0;
            ProductDAOImpl productDAO = new ProductDAOImpl();
            for (int i = 0; i < products.size(); i++) {
                List<Product> queryProduct = productDAO.queryProductByCode(connection, products.get(i).getBarCode());
                if (queryProduct.size() == 0) {
                    productDAO.insertProduct(connection, products.get(i));
                    cnt++;
                }
            }
            System.out.println("成功从文本文件导入 " + cnt + " 条商品数据");
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (Exception e) {
            System.out.println("数据导入失败！");
        }
    }

    /**
     * 从键盘依次录入商品信息
     * @param connection
     */
    public static void loadDataFromKeyboard(Connection connection) {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("请输入待录入的商品信息(格式为：");
                System.out.println("商品条形码，商品名称，单价，供应商)：");
                String input = scanner.next();
                String[] product = input.split("，");
                if (product.length != 4)
                    throw new ProductException("你输入的数据格式不正确，请重新输入");

                if (BarCodeCheck.checkFormat(product[0])) {
                    List<Product> queryProduct = productDAO.queryProductByCode(connection, product[0]);
                    if (queryProduct.size() != 0)
                        throw new ProductException("条形码不能重复，请重新输入");

                    try {
                        Double price = Double.parseDouble(product[2]);
                        int res = productDAO.insertProduct(connection, new Product(product[0], product[1], price, product[3]));
                        if (res == 1) System.out.println("添加成功");
                        else System.out.println("添加失败");
                    }catch (NumberFormatException e){
                        System.out.println("输入的价格格式不正确");
                    }
                    System.out.println("是否继续录入？(y/n)：");
                    String opt = scanner.next();
                    if ("n".equalsIgnoreCase(opt))
                        return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 商品查询
     * 根据输入的商品名称进行模糊查询
     * @param connection
     */
    public static void productQuery(Connection connection){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入查询的商品名称：");
        String productName = scanner.next();

        List<Product> products = new ProductDAOImpl().queryProductByName(connection, productName);
        System.out.printf("满足条件的记录总共%d条，信息如下：\n",products.size());
        System.out.println("序号 条形码 商品名称  单价\t供应商");
        System.out.println("=== =====  ======= \t ====\t=====");
        if (products.size() > 0)
            for (int i = 0; i < products.size(); i++) {
                System.out.print((i+1) + "\t" +  products.get(i).getBarCode() + "\t" + products.get(i).getProductName() + "\t");
                System.out.printf("%- 7.2f\t",products.get(i).getPrice());
                System.out.println(products.get(i).getSupply());
            }
        System.out.print("按任意键返回主菜单");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
