package team.tb.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import team.tb.utils.JWTUtils;
import team.tb.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * 拦截请求，确保登录才可以访问，除了登录请求除外
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("进入拦截器");
        String[] url = httpServletRequest.getRequestURI().split("/");
        System.out.println("================>"+Arrays.toString(url));

        // 查找session中是否有登录
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("user") != null){
            System.out.println("找到session，放行");
            return true;
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
        // 默认拦截
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
