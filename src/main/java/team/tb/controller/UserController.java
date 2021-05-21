package team.tb.controller;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.OncePerRequestFilter;
import team.tb.common.FormInfo;
import team.tb.common.Result;
import team.tb.pojo.Keys;
import team.tb.pojo.TableInfo;
import team.tb.pojo.User;
import team.tb.pojo.UserInfo;
import team.tb.service.UserService;
import team.tb.utils.JWTUtils;
import team.tb.utils.MD5Utils;
import team.tb.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    // 本地项目路径
    private static final String BASE_PATH = "E:\\JetBrainJava\\tableOnline\\src\\main\\webapp\\WEB-INF";
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
                if(ret.getUstatus() == 0){
                    return Result.fail(402, "已封禁", null);
                }
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
                // 设置cookie路径
                cookie.setPath("/");
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

    /**
     * 登出操作
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // 移除session中的信息，将cookie的认证信息清除
        request.getSession().invalidate();
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "login";
    }

    /**
     * 得到当前用户能填写的表单
     * @param page
     * @param limit
     * @param request
     * @return
     */
    @RequestMapping("/getFormList")
    @ResponseBody
    public Result getFormList(Integer page, Integer limit, HttpServletRequest request){
        // 获取当前用户id
        User user = (User) request.getSession().getAttribute("user");
        String uid = String.valueOf(user.getUid());
        if(StringUtils.isEmpty(uid)){
            return Result.fail("参数为空");
        }
        if(page != null){
            page = (page-1)*limit;
        }else{
            page = 0;
            limit = 10;
        }
        // 获得所有的表单数据
        Map<String, Object> ret =  userService.getAllFormCurUser(uid, page, limit);
        return Result.succ(0, ret.getOrDefault("list", null), (Integer) ret.getOrDefault("count", 0));
    }

    /**
     * 根据条件查询出当前普通用户所能填写的表单
     * @param request
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/searchForm")
    @ResponseBody
    public Result searchForm(HttpServletRequest request, String formTitle, String startTime, String endTime, Integer page, Integer limit){
        System.out.println("开始时间======>" + startTime);
        System.out.println("结束时间======>" + endTime);
        // 传入的字符串参数只要传递了参数，不管有没有值，只会为空，不会为null
        User user = (User) request.getSession().getAttribute("user");
        String id = String.valueOf(user.getUid());
        // 计算分页信息，获取相应数据条数
        if(page == null){
            page = 0;
            limit = 10;
        }else{
            page = (page - 1)*limit;
        }
        Map<String, Object> ret = userService.searchForm(id, formTitle, startTime, endTime, page, limit);
        return Result.succ(0, ret.getOrDefault("list", null), (Integer) ret.getOrDefault("count", 0));
    }

    /**
     * 查看表单之前先将表单Id存入Session
     * @param formId
     * @param request
     * @return
     */
    @RequestMapping("/toFormDetail")
    @ResponseBody
    public Result toFormDetail(String formId, HttpServletRequest request){
        request.getSession().setAttribute("viewFormId", formId.trim());
        return Result.succ("存放成功");
    }

    /**
     * 获取到表单所有的字段信息
     * @param request
     * @return
     */
    @RequestMapping("/getFormAllInfo")
    @ResponseBody
    public Result getFormAllInfo(HttpServletRequest request) throws DocumentException {
        User user = (User) request.getSession().getAttribute("user");
        // 获取当前普通用户的id
        String id = String.valueOf(user.getUid());
        Integer formId = Integer.valueOf((String) request.getSession().getAttribute("viewFormId"));
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
        // 获取表单全部字段信息以及已填写的信息
        Map ret = userService.getFormAllFields(formId, realPath, id);
        return Result.succ(ret);
    }

    /**
     * 提交填写的表单
     * @param data
     * @param request
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    @RequestMapping("/submitForm")
    @ResponseBody
    public Result submitForm(@RequestBody FormInfo data, HttpServletRequest request) throws DocumentException, IOException {
//        System.out.println("提交数据："+data);
        // 文件存放路径前缀
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
        // 获取用户id
        String uid = String.valueOf(((User) request.getSession().getAttribute("user")).getUid ());
        int ret = userService.updateUserInfoInForm(realPath, BASE_PATH, data, uid);
        if(ret == 1){
            return Result.succ("成功");
        }else{
            return Result.fail("失败");
        }
    }

    /**
     * 获得当前普通用户所有字段数据
     * @param
     * @return
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Result getUserInfo(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        Integer uid = user.getUid();
        List<Keys> list = userService.getUserAllInfo(uid);
        return Result.succ(list);
    }

    /**
     * 更新用户修改后的个人信息
     * @param data 用户修改后的所有数据信息
     * @return
     */
    @RequestMapping("/modifyUserInfo")
    @ResponseBody
    public Result modifyUserInfo(@RequestBody List<Keys> data, HttpServletRequest request){
//        System.out.println(data);
        // 获取用户id
        User user  = (User) request.getSession().getAttribute("user");
        Integer uid = user.getUid();
        int ret = userService.modifyUserInfo(data, uid);
        if(ret > 0){
            return Result.succ("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    /**
     * 用户自己修改密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping("/changeUserPwd")
    @ResponseBody
    public Result changeUserPwdBySelf(String oldPwd, String newPwd, HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        // 获取用户Id
        Integer uid = user.getUid();
        // 根据Id查找密码并进行验证
        int ret = userService.changeUserPwdBySelf(uid, oldPwd, newPwd);
        if(ret > 0){ // 密码修改成功
            // 清除session和cookie，跳转到登录界面
            Cookie cookie = new Cookie("token", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            request.getSession().invalidate();
            return Result.succ("修改成功");
        }else if(ret == 0){
            return Result.fail( "原密码错误");
        }else{
            return Result.fail(401, "修改失败", null);
        }
    }
}
