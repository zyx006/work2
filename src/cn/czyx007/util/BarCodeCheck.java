package cn.czyx007.util;

import cn.czyx007.exception.BarCodeException;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/15 - 12:44
 */
public class BarCodeCheck {
    public static boolean checkFormat(String code) throws BarCodeException{
        if (code.length() == 6) {
            for (int i = 0; i < 6; i++)
                if (!(code.charAt(i) >= '0' && code.charAt(i) <= '9'))
                    throw new BarCodeException("输入的条形码不是纯数字");
            return true;
        }else throw new BarCodeException("输入的条形码长度不正确");
    }
}
