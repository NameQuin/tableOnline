package team.tb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result login(User user, HttpServletRequest request, HttpServletResponse response){
        User ret = null;
        if(!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())){
            // 将传递进来的密码进行加密
            user.setPassword(MD5Utils.encryption(user.getUsername(), user.getPassword()));
            ret = userService.findUserByUsernameAndPwd(user);
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
//            return "system/index";
            return Result.succ("登陆成功");
        }
//        return "error/error";
        return Result.fail("登陆失败");
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // 移除session中的信息，将cookie的信息也清除
        request.getSession().invalidate();
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "login";
    }
}
