package game;

/**
 * 用户类 - JavaBean规范
 * 知识点：第8章面向对象、private封装、get/set方法、构造方法
 */
public class User {
    private String username;
    private String password;
    private String nickname;

    // 无参构造
    public User() {
    }

    // 有参构造
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
