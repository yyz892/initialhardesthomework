package game;

import java.util.Random;

/**
 * 验证码工具类
 * 知识点：第10章static工具类、第4章数组、第3章循环、Random类
 */
public class VerificationCode {

    /**
     * 生成指定位数的随机验证码（大小写字母+数字混合）
     * @param length 验证码长度
     * @return 生成的验证码字符串
     */
    public static String generate(int length) {
        // 字符池：大小写字母 + 数字
        char[] pool = new char[62];
        int index = 0;

        // 大写字母 A-Z
        for (char c = 'A'; c <= 'Z'; c++) {
            pool[index++] = c;
        }
        // 小写字母 a-z
        for (char c = 'a'; c <= 'z'; c++) {
            pool[index++] = c;
        }
        // 数字 0-9
        for (char c = '0'; c <= '9'; c++) {
            pool[index++] = c;
        }

        // 随机抽取length个字符组成验证码
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = r.nextInt(pool.length);
            code.append(pool[randomIndex]);
        }
        return code.toString();
    }

    /**
     * 校验用户输入的验证码是否正确（忽略大小写）
     * @param inputCode 用户输入的验证码
     * @param realCode 真实的验证码
     * @return 是否验证通过
     */
    public static boolean verify(String inputCode, String realCode) {
        if (inputCode == null || realCode == null) {
            return false;
        }
        return inputCode.equalsIgnoreCase(realCode);
    }
}
