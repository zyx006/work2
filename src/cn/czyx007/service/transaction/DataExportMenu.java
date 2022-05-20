package cn.czyx007.service.transaction;

import cn.czyx007.bean.Product;
import cn.czyx007.service.dao.impl.ProductDAOImpl;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/15 - 14:59
 */
public class DataExportMenu {
    public static void exportDataToExcel(Connection connection){
        String[] dateStr = LocalDate.now().toString().split("-");
        String exportExcelName = "saleDetail" + dateStr[0].concat(dateStr[1]).concat(dateStr[2]) + ".xls";
        WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(new File("src/" + exportExcelName));
            WritableSheet ws = wwb.createSheet("商品表", 0);

            ws.addCell(new Label(0,0,"barCode"));
            ws.addCell(new Label(1,0,"productName"));
            ws.addCell(new Label(2,0,"price"));
            ws.addCell(new Label(3,0,"supply"));

            List<Product> products = new ProductDAOImpl().queryProductByCode(connection);
            for (int i = 0; i < products.size(); i++) {
                ws.addCell(new Label(0,i+1,products.get(i).getBarCode()));
                ws.addCell(new Label(1,i+1,products.get(i).getProductName()));
                ws.addCell(new Number(2,i+1,products.get(i).getPrice()));
                ws.addCell(new Label(3,i+1,products.get(i).getSupply()));
            }
            wwb.write();
            System.out.println("成功导出 " + products.size() + " 条销售数据到 excel 文件中");
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (Exception e) {
            System.out.println("数据导入失败！");
            e.printStackTrace();
        }finally {
            if (wwb != null){
                try {
                    wwb.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void exportDataToText(Connection connection){
        String[] dateStr = LocalDate.now().toString().split("-");
        String exportTextName = "saleDetail" + dateStr[0].concat(dateStr[1]).concat(dateStr[2]) + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/" + exportTextName))){
            bw.write("barCode productName price supply\n");
            List<Product> products = new ProductDAOImpl().queryProductByCode(connection);
            for (int i = 0; i < products.size(); i++) {
                bw.write(products.get(i).getBarCode() + " ");
                bw.write(products.get(i).getProductName() + " ");
                bw.write(products.get(i).getPrice() + " ");
                bw.write(products.get(i).getSupply() + "\n");
            }
            bw.flush();
            System.out.println("成功导出 " + products.size() + " 条销售数据到文本文件中");
            System.out.print("按任意键返回主菜单");
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
