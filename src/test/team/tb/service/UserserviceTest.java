package team.tb.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import team.tb.pojo.User;
import team.tb.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserserviceTest {
    @Autowired
    private UserService userService;

    @Test
    public void test01(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        User ret = null;
        if(user != null){
             ret = userService.findUserByUsernameAndPwd(user);
        }
        System.out.println(ret);
    }

    @Test
    public void test02() throws ParseException {
        String start = "2021-04-17 00:00:00";
        String end = "2021-05-12 10:10:08";
        Date date1 = DateUtils.str2date(start);
        Date date2 = DateUtils.str2date(end);
        System.out.println(date1);
        System.out.println(date2);
        System.out.println(DateUtils.date2str(date1));
        System.out.println(DateUtils.date2str(date2));
        System.out.println(DateUtils.date2str(date1, "yyyy MM dd"));
        System.out.println(DateUtils.date2str(date2, "yyyy年MM月dd日"));
        System.out.println(DateUtils.date2str(date1));
        System.out.println(DateUtils.date2str(date2));
    }



}
