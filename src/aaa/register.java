package aaa;

import java.util.ArrayList;
import java.util.Scanner;

public class register {
    public static void register(ArrayList<user> list) {
        user u = new user();
        System.out.println("进入注册页面");
        while ( true){
            System.out.println("请输入用户名");
            Scanner sc = new Scanner(System.in);
            String name = sc.next();
            boolean a=checklength(16,3,name);
            if (!a) {
                System.out.println("用户名长度不符合要求");
                continue;
            }
            boolean b = checkusername(name);
            if (!b) {
                System.out.println("用户名只能是字母加数字的组合,不能是纯数字");
                continue;
            }
            boolean c = unique(list,name);
            if (!c)  {
                System.out.println("用户名已存在");
                continue;
            }
            u.setName(name);
            break;
        }
        while ( true){
            System.out.println("请输入密码");
            Scanner sc = new Scanner(System.in);
            String password1 = sc.next();
            boolean a=checklength(8,3,password1);
            if (!a){
                System.out.println("密码长度不符合要求");
                continue;
            }
            boolean b = checkpassword(password1);
            if (!b) {
                System.out.println("密码只能是字母加数字的组合，不能有其他字母");
                continue;
            }
            System.out.println("请再次输入密码");
            String password2 = sc.next();
            if (!password1.equals(password2)) {
                System.out.println("两次密码不一致");
                continue;
            }
            u.setPassword(password1);
            break;
        }
        list.add(u);
        System.out.println("用户"+u.getName()+"注册成功");
    }
}
