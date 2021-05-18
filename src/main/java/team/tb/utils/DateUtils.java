package team.tb.utils;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间类型与字符串类型进行转换的常用方法
 */
public class DateUtils {
    private static DateTimeFormatter dtf;
    // HH是24小时制
    private static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static {
        dtf = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
    }

    /**
     * 日期转字符串，使用默认格式
     * @param date
     * @return
     */
    public static String date2str(@NotNull LocalDateTime date){
        return dtf.format(date);
    }


    /**
     * 将字符串转为日期，使用默认的格式
     * @param str
     * @return
     * @throws ParseException
     */
    public static LocalDateTime str2date(@NotNull String str) throws ParseException {
        return LocalDateTime.parse(str, dtf);
    }

}
