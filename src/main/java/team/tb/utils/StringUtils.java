package team.tb.utils;

/**
 * 一些关于String操作的工具方法
 */
public class StringUtils {

    public static boolean isEmpty(CharSequence str){
        return str == null || str.length() == 0;
    }

    public static boolean isEmptyArray(CharSequence[] strs){
        return strs == null || strs.length == 0;
    }
}
