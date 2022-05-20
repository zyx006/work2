package cn.czyx007.bean;

/**
 * @author : 张宇轩
 * @createTime : 2022/5/13 - 23:24
 */
public class User {
    //用户登录名
    private String userName;
    //密码 sha1加密存储
    private String password;
    //中文名
    private String chrName;
    //用户角色 管理员/收银员
    private String role;

    public User() {
    }

    public User(String userName, String password, String chrName, String role) {
        this.userName = userName;
        this.password = password;
        this.chrName = chrName;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChrName() {
        return chrName;
    }

    public void setChrName(String chrName) {
        this.chrName = chrName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", chrName='" + chrName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
