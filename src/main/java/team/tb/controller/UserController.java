package team.tb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.tb.common.Result;
import team.tb.pojo.User;
import team.tb.service.UserService;
import team.tb.utils.JWTUtils;
import team.tb.utils.MD5Utils;
import team.tb.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request, HttpServletResponse response){
        User ret = null;
        if(!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())){
            // 将传递进来的密码进行加密
            user.setPassword(MD5Utils.encryption(user.getUsername(), user.getPassword()));
            ret = userService.findUserByUsernameAndPwd(user);
            request.setAttribute("gender", "男 女");
            request.setAttribute("ugender", "nan nv");
        }
        if(ret != null){
            // 将对象存入session中
            request.getSession().setAttribute("user", ret);
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(ret.getUid()));
            map.put("username", ret.getUsername());
            // 生成token
            String token = JWTUtils.getToken(map);
            Cookie cookie = new Cookie("token", token);
            // 设置cookie存活时间为一周
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            return "system/index";
        }
        return "error/error";
    }
}
