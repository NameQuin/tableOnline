package team.tb.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import team.tb.pojo.User;
import team.tb.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        Random random  = new Random();
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            list.add("test"+random.nextInt(50));
        }
        System.out.println(list);
        System.out.println(list.subList(0, 10));
    }

    @Test
    public void test03(){
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            list.add(""+i);
        }
        Integer[] ret = (Integer[]) list.stream().map(Integer::parseInt).toArray(Integer[]::new);
        System.out.println(Arrays.toString(ret));
    }

}
