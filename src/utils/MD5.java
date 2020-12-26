package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5 {
    private static String savedPwd = "pmq7VoTEWWLYh1ZPCDRujQ==";// 存储的密文

    public static void main(String[] args) {
        // 将明文密码admin123456加密
        System.out.println(md5Encryption("1"));
//        System.out.println(md5Encryption("2"));
//        System.out.println(md5Encryption("3"));
//
//        // 测试登录
//        System.out.println(loginByRightPwd("admin123456"));
    }

    public static boolean loginByRightPwd(String pwd) {
        if (savedPwd.equals(md5Encryption(pwd))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算md5的工具方法
     *
     * @param password
     * @return 加密后的密码
     */
    private static String md5Encryption(String password) {
        try {
           // MessageDigest md = MessageDigest.getInstance("MD5");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
         //   MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 通过md5计算摘要,返回一个字节数组
            byte[] bytes = md.digest(password.getBytes("UTF-8"));

            // 再将字节数编码为用a-z A-Z 0-9 / *一共64个字符表示的要存储到数据库的字符串，所以又叫BASE64编码算法
            String str = Base64.getEncoder().encodeToString(bytes);
            return str;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;// 发生异常返回空
    }
}