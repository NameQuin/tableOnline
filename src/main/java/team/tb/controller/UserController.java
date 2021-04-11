package team.tb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.tb.pojo.User;
import team.tb.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(User user){
        User ret = null;
        System.out.println(user.getUsername() + "=====>" + user.getPassword());
        if(user != null && !user.getUsername().isEmpty() && !user.getPassword().isEmpty()){
            ret = userService.findUserByUsername(user);
        }
        if(ret != null){
            return "system/index";
        }
        return "error/error";
    }
}
