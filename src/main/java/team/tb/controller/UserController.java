package team.tb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 用户登录，传入用户名和密码，验证码
     * 请求方式：POST
     * @param user
     * @param code
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(User user, String code, HttpServletRequest request, HttpServletResponse response){
        // 拿出在session中的验证码
        String codeInSession = (String) request.getSession().getAttribute("code");
        // 验证码相等
        if(!StringUtils.isEmpty(code) && code.equalsIgnoreCase(codeInSession)){
            User ret = null;
            if(!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())){
                // 将传递进来的密码进行加密
                user.setPassword(MD5Utils.encryption(user.getUsername(), user.getPassword()));
                ret = userService.findUserByUsernameAndPwd(user);
            }
            if(ret != null){
                // 将对象存入session中
                request.getSession().setAttribute("user", ret);
                // 将用户id，用户名，用户等级生成token
                // 等级信息是为了在拦截时确定该用户是否有该操作的权限
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(ret.getUid()));
                map.put("username", ret.getUsername());
                map.put("userLevel", String.valueOf(ret.getUlevel()));
                // 生成token
                String token = JWTUtils.getToken(map);
                Cookie cookie = new Cookie("token", token);
                // 设置cookie存活时间为一周
                cookie.setMaxAge(60*60*24*7);
                response.addCookie(cookie);
                // 依据登陆者身份跳转页面
                String path = request.getContextPath() + "/pages";
                if(ret.getUlevel() == 0){
                    path += "/user/userPanel.html";
                }else if(ret.getUlevel() == 1){
                    path += "/admin/adminPanel.html";
                }else{
                    path += "/root/rootPanel.html";
                }
                return Result.succ("登陆成功 " + path);
            }
            return Result.fail("账号或密码错误");
        }
        return Result.fail(401, "验证码错误", null);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // 移除session中的信息，将cookie的认证信息清除
        request.getSession().invalidate();
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "login";
    }
}
