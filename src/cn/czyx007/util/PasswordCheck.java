package cn.czyx007.util;

import cn.czyx007.exception.PasswordException;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/15 - 14:44
 */
public class PasswordCheck {
    private static final String PASSWORD_SECURITY_ERROR = "您的密码不符合复杂性要求（密码长度不少于 6 个字符，至少有一个小写字母，至少有一个大写字母，至少一个数字），请重新输入：";

    public static boolean checkFormat(String pwd) throws PasswordException {
        if (pwd.length() >= 6) {
            int numCnt = 0, lowCnt = 0, upCnt = 0;
            for (int i = 0; i < pwd.length(); i++) {
                if (pwd.charAt(i) >= '0' && pwd.charAt(i) <= '9')
                    numCnt++;
                else if (pwd.charAt(i) >= 'a' && pwd.charAt(i) <= 'z')
                    lowCnt++;
                else if (pwd.charAt(i) >= 'A' && pwd.charAt(i) <= 'Z')
                    upCnt++;
            }
            if (numCnt >= 1 && lowCnt >= 1 && upCnt >= 1)
                return true;
            else throw new PasswordException(PASSWORD_SECURITY_ERROR);
        } else throw new PasswordException(PASSWORD_SECURITY_ERROR);
    }
}
