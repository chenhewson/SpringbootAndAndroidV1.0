package springboot06mybatis.Entity;

/**
 * ClassName:    userinfo
 * Package:    Entity
 * Description:
 * Datetime:    2020/3/5   14:26
 * Author:   hewson.chen@foxmail.com
 */
public class UserInfo {
    private int uid;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "userinfo{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

