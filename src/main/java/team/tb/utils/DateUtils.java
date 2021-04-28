package team.tb.utils;

import com.sun.istack.internal.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间类型与字符串类型进行转换的常用方法
 */
public class DateUtils {
    private static SimpleDateFormat sdf;
    // HH是24小时制
    private static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static {
        sdf = new SimpleDateFormat(DEFAULT_FORMAT);
    }

    /**
     * 日期转字符串，使用默认格式
     * @param date
     * @return
     */
    public static String date2str(@NotNull Date date){
        return sdf.format(date);
    }

    /**
     * 根据给定格式将日期转换为字符串
     * @param date
     * @param format
     * @return
     */
    public static String date2str(@NotNull Date date, @NotNull String format){
        // 将转换格式更换为给定的格式
        sdf.applyPattern(format);
        String ret = sdf.format(date);
        // 将格式转换为默认的格式
        sdf.applyPattern(DEFAULT_FORMAT);
        return ret;
    }

    /**
     * 将字符串转为日期，使用默认的格式
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date str2date(@NotNull String str) throws ParseException {
        return sdf.parse(str);
    }

    /**
     * 按照给定格式解析日期为字符串
     * @param str
     * @param format
     * @return
     */
    public static Date str2date(@NotNull String str, @NotNull String format) throws ParseException {
        sdf.applyPattern(format);
        Date date = sdf.parse(str);
        sdf.applyPattern(DEFAULT_FORMAT);
        return date;
    }
}
