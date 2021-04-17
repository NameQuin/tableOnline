package team.tb.interceptor;

import com.auth0.jwt.JWT;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import team.tb.pojo.User;
import team.tb.utils.JWTUtils;
import team.tb.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * 普通用户请求拦截器，保证普通用户的操作需要登录
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("进入root拦截器");
        String[] url = httpServletRequest.getRequestURI().split("/");
        System.out.println("================>"+Arrays.toString(url));

        // 查找session中是否有登录
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("user") != null){
            System.out.println("找到session，放行");
            if(((User) session.getAttribute("user")).getUlevel().equals(0)){
                System.out.println("有权限，放行");
                return true;
            }else{
                System.out.println("没有权限，拦截");
                return false;
            }
        } else {
            // session中没有，需要先判断cookie中是否有token
            Cookie[] cookies = httpServletRequest.getCookies();
            String token = null;
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    break;
                }
            }
            if(StringUtils.isEmpty(token)){
                // 地址重定向
                System.out.println("=========>"+"找不到token，重定向");
                httpServletResponse.sendRedirect("/tableOnline/user/toLogin");
                return false;
            }
            try{
                JWTUtils.verify(token);
                System.out.println("cookie中存在有效token，放行");
                // cookie中存在有效token，将其信息加入session，达到免登陆效果
                User user = new User();
                user.setUid(Integer.parseInt(JWTUtils.getClaim(token, "id")));
                user.setUsername(JWTUtils.getClaim(token, "username"));
                user.setUlevel(Integer.parseInt(JWTUtils.getClaim(token, "userLevel")));
                httpServletRequest.getSession().setAttribute("user", user);
                // 验证在cookie中的权限信息，判断是否有操作权限
                if(user.getUlevel().equals(0)){
                    System.out.println("有权限，放行");
                    return true;
                }else{
                    System.out.println("权限不够，拦截");
                    return false;
                }
            }catch (Exception e){
                System.out.println("==================>token校验失败，重定向");
                // 删除cookie，防止浏览器一直自动登录失败
                Cookie cookie = new Cookie("token", "0");
                cookie.setMaxAge(0);
                httpServletResponse.addCookie(cookie);
                httpServletResponse.sendRedirect("/tableOnline/user/toLogin");
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
