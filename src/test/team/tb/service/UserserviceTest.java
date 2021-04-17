package team.tb.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import team.tb.pojo.User;

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
    public void test02(){

    }



}
