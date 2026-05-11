package game;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 用户管理类 - 处理注册和登录业务
 * 知识点：第12章ArrayList集合、第12章String比较、第3章循环判断
 */
public class UserManager {
    // 存储所有注册用户
    private static ArrayList<User> userList = new ArrayList<>();

    /**
     * 注册功能
     * 要求：用户名唯一、密码长度>=6、两次密码一致
     * @return 注册成功返回用户对象，失败返回null
     */
    public static User register() {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== 用户注册 =====");

        // 1. 输入用户名（检查唯一性）
        String username;
        while (true) {
            System.out.print("请输入用户名：");
            username = sc.next();
            if (isUsernameExist(username)) {
                System.out.println("该用户名已被注册，请重新输入！");
            } else {
                break;
            }
        }

        // 2. 输入密码（长度检查）
        String password;
        while (true) {
            System.out.print("请输入密码（至少6位）：");
            password = sc.next();
            if (password.length() < 6) {
                System.out.println("密码长度不足6位，请重新输入！");
            } else {
                break;
            }
        }

        // 3. 确认密码
        String confirmPassword;
        while (true) {
            System.out.print("请再次输入密码：");
            confirmPassword = sc.next();
            if (!password.equals(confirmPassword)) {
                System.out.println("两次密码不一致，请重新确认！");
            } else {
                break;
            }
        }

        // 4. 输入昵称
        System.out.print("请输入游戏昵称：");
        String nickname = sc.next();

        // 5. 创建用户并存储
        User user = new User(username, password, nickname);
        userList.add(user);
        System.out.println("注册成功！欢迎加入游戏，" + nickname + "！");
        return user;
    }

    /**
     * 登录功能
     * 要求：用户名存在、密码正确、验证码正确
     * @return 登录成功返回用户对象，失败返回null
     */
    public static User login() {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== 用户登录 =====");

        // 1. 输入用户名
        System.out.print("请输入用户名：");
        String username = sc.next();

        // 2. 查找用户
        User user = findUserByUsername(username);
        if (user == null) {
            System.out.println("该用户不存在，请先注册！");
            return null;
        }

        // 3. 输入密码（最多3次机会）
        int passwordAttempts = 3;
        boolean passwordCorrect = false;
        while (passwordAttempts > 0) {
            System.out.print("请输入密码（剩余" + passwordAttempts + "次）：");
            String password = sc.next();
            if (password.equals(user.getPassword())) {
                passwordCorrect = true;
                break;
            } else {
                passwordAttempts--;
                if (passwordAttempts > 0) {
                    System.out.println("密码错误，请重试！");
                }
            }
        }
        if (!passwordCorrect) {
            System.out.println("密码错误次数过多，登录失败！");
            return null;
        }

        // 4. 验证码校验（知识点：验证码生成与校验）
        String code = VerificationCode.generate(4);
        System.out.println("【验证码】" + code);
        System.out.print("请输入上方的验证码：");
        String inputCode = sc.next();

        if (!VerificationCode.verify(inputCode, code)) {
            System.out.println("验证码错误，登录失败！");
            return null;
        }

        System.out.println("登录成功！欢迎回来，" + user.getNickname() + "！");
        return user;
    }

    // 检查用户名是否已存在
    private static boolean isUsernameExist(String username) {
        return findUserByUsername(username) != null;
    }

    // 根据用户名查找用户
    private static User findUserByUsername(String username) {
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }
}
