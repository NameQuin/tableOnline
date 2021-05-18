package team.tb.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Utils {

    public static String encryption(String salt, String obj){
            //使用shiro框架的md5加密，使用用户名与密码一起散列1000次，防止密码被解密
            //第一个参数是加密对象，第二个是盐值，第三个参数是散列次数
            Md5Hash md5Hash = new Md5Hash(obj, salt, 1000);
            return md5Hash.toString();
    }

    public static String encryption(Object obj){
        Md5Hash md5Hash = new Md5Hash(obj);
        return md5Hash.toString();
    }


    public static void main(String[] args) {
        String en = encryption("root", "root");
        System.out.println(en);
    }
}
