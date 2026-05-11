package aaa;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
public class ui {
    public static void main(String[] args) {
        System.out.println("欢迎来到文字格斗游戏");
        ArrayList<user> list = new ArrayList<>();
        while ( true){
        System.out.println("--------------------");
        System.out.println(" 欢迎来到文字格斗游戏");
        System.out.println("   1登录2注册3退出");
        System.out.println("--------------------");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1->login(list);
            case 2->register(list);
            case 3-> exit();
            default ->{
                System.out.println("输入错误");
            }
         }
        }
    }
    public static void login(ArrayList<user>list) {
        System.out.println("进入登录页面");
        System.out.println("验证码为："+gettemporarypassword());
    }
    public static void register(ArrayList<user>list) {
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
    public static void exit() {
        System.out.println("退出游戏");
        System.exit(0);
    }
    public static int[] getcount(String username) {
        int numbercount = 0;
        int lettercount = 0;
        int othercount = 0;
        for (int i = 0; i < username.length(); i++) {
            if (username.charAt( i)>='0' && username.charAt(i)<='9'){
                numbercount++;
            } else if (username.charAt(i)>='a'&&username.charAt(i)<='z'||username.charAt(i)>='A'&&username.charAt(i)<='Z') {
                lettercount++;
            }else {
                othercount++;
            }
        }
            int[] count = {numbercount,lettercount,othercount};
            return count;

    }
    public static boolean checklength(int max,int min,String str) {
        if (str.length() > max || str.length() < min) {
        }
        return true;
    }
    public static boolean checkusername(String username) {
        int arr[]=getcount(username);
        return arr[0]>=0&&arr[1]>0&&arr[2]==0;
        }
        public static boolean checkpassword(String password) {
        int arr[]=getcount(password);
        return arr[0]>0&&arr[1]>0&&arr[2]==0;
    }
        public static boolean unique(ArrayList<user> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            user u =   list.get(i);
            if (u.getName().equals(username)) {
                return false;
            }
        }
        return true;
    }
    public static String gettemporarypassword(){
        ArrayList<Character> list=new ArrayList<>();
        for (int i = 0; i < 26; i++){
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }
        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        for (int i = 0; i < 4; i++){
            int index=r.nextInt(list.size());
            sb.append(list.get(index));
        }
        sb.append(r.nextInt(10));
        char[] array=sb.toString().toCharArray();
        int a=r.nextInt(array.length);
        char tem=array[a];
        array[a]=array[array.length-1];
        array[array.length-1]=tem;
         String code=new  String(array);
        return code;
    }
}






