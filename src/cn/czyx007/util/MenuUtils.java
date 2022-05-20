package cn.czyx007.util;

import java.util.Scanner;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/15 - 15:30
 */
public class MenuUtils {
    public static int checkInputNumber(Scanner scanner, int low, int high) {
        String input = scanner.next();
        try {
            int opt = Integer.parseInt(input);
            if (opt >= low && opt <= high)
                return opt;
            else {
                System.out.println("输入无效，只能输入" + low + "-" + high);
            }
        } catch (NumberFormatException e) {
            System.out.println("输入无效，只能输入" + low + "-" + high);
        }
        return -1;
    }
}
