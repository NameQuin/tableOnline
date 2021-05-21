package team.tb.controller;

import org.apache.ibatis.annotations.ResultMap;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.tb.common.*;
import team.tb.dao.GradeMapper;
import team.tb.dao.UserMapper;
import team.tb.pojo.*;
import team.tb.service.*;
import team.tb.utils.DateUtils;
import team.tb.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 管理员可以做的操作
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    // 本地项目路径
    private static final String BASE_PATH = "E:\\JetBrainJava\\tableOnline\\src\\main\\webapp\\WEB-INF";

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private KeysService keysService;
    @Autowired
    private TableInfoService tableInfoService;


    /**
     * 接口描述：获取表单列表请求，找出管理员自己发布的表单
     * @param request
     * @param page 分页信息
     * @param limit 页面大小
     * @return
     */
    @RequestMapping("/getFormList")
    @ResponseBody
    public Result getFormList(HttpServletRequest request, Integer page, Integer limit){

        // 获取管理员id，利用id查找其发布的表单
        User user = (User) request.getSession().getAttribute("user");
        Integer id = user.getUid();
        // 计算分页信息，获取相应数据条数
        if(page != null || limit != null){
            page = (page - 1)*limit;
        }else{
            page = 0;
            limit = 10;
        }
        List<TableInfo> list = null;
        Integer count = null;
        if(id != null){
            list = tableInfoService.getFormListByAdminId(id, page, limit);
            count = tableInfoService.getFormCount(id);
        }
        if(list != null){
            return Result.succ(0, list, count);
        }
        return Result.fail("查找数据错误");
    }

    /**
     * 根据条件查询表单
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
        Integer id = user.getUid();
        // 计算分页信息，获取相应数据条数
        if(page == null){
            page = 0;
            limit = 10;
        }else{
            page = (page - 1)*limit;
        }
        List<TableInfo> list = tableInfoService.searchForm(id, formTitle, startTime, endTime, page, limit);
        Integer count = tableInfoService.getFormCountOnCondition(id, formTitle, startTime, endTime);
        if(list != null){
            return Result.succ(0, list, count);
        }
        return Result.fail("查找失败");
    }

    /**
     * 得到所有的普通用户，包含所有状态的
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getUserList")
    @ResponseBody
    public Result getUserList(Integer page, Integer limit){
        // 处理分页
        if(page == null){
            page = 0;
            limit = 10;
        }else{
            page = (page - 1)*limit;
        }
        // 不出异常的情况下，返回的列表不会为null
        List<User> list = userService.getUserList(page, limit);
        if(list != null){
            int count = userService.getCount();
            return Result.succ(0, list, count);
        }
        return Result.fail(0, "好像没有数据", null);
    }

    /**
     * 获取所有年级
     * @return
     */
    @RequestMapping("/getGrade")
    @ResponseBody
    public Result getGrade(){
        List<Grade> list = gradeService.getGrade();
        return Result.succ(list);
    }

    /**
     * 获取对应年级的所有班级
     * @param grades
     * @return
     */
    @RequestMapping("/getDepartment")
    @ResponseBody
    public Result getDepartment(Integer[] grades){
        List<Department> list = departmentService.getDepartmentByGrade(grades);
        return Result.succ(list);
    }

    /**
     * 获取对应专业的班级
     * @param majors
     * @return
     */
    @RequestMapping("/getClazz")
    @ResponseBody
    public Result getClazz(Integer[] majors){
        List<Clazz> list = clazzService.getClazzByMajorId(majors);
        return Result.succ(list);
    }

    /**
     * 根据院系id查找专业
     * @param departments
     * @return
     */
    @RequestMapping("/getMajor")
    @ResponseBody
    public Result getMajor(Integer[] departments){
        List<Major> list = majorService.getMajorByDepartmentId(departments);
        return Result.succ(list);
    }

    /**
     * 根据班级查找学生信息
     * @param clazzs
     * @return
     */
    @RequestMapping("/getStudent")
    @ResponseBody
    public Result getStudent(Integer[] clazzs){
        List<User> list = userService.getUserByClass(clazzs);
        return Result.succ(list);
    }
    /**
     * 根据条件查找用户信息
     * @param grade
     * @param department
     * @param major
     * @param clazz
     * @return
     */
    @RequestMapping("/searchUser")
    @ResponseBody
    public Result searchUser(Integer grade, Integer department, Integer major, Integer clazz, String username, Integer page, Integer limit){
        // 处理分页
        page = (page - 1)*limit;
        List<User> list = userService.getUserOnCondition(grade, department, major, clazz, username, page, limit);
        int count = userService.getUserCountOnCondition(grade, department, major, clazz, username);
        return Result.succ(0, list, count);
    }

    /**
     * 获得已存在数据库的字段信息
     * @return
     */
    @RequestMapping("/getAllKeys")
    @ResponseBody
    public Result getAllKeys(){
        List<Keys> list = keysService.getAllKeys();
        logBefore(logger, "获取数据库中键"+list);
        logAfter(logger, null);
        return Result.succ(list);
    }

    /**
     * 获得表单创建信息并创建表单
     * @param data
     * @return
     */
    @PostMapping("/createForm")
    @ResponseBody
    public Result createFormRequest(@RequestBody FormInfo data, HttpServletRequest request){
        logBefore(logger, "接收数据："+data);
        logBefore(logger, "创建表单");
        System.out.println(data);
        // web应用对外发布目录，target下的目录
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
        // 项目的实际文件目录位置
        String basePath = BASE_PATH;
        // 创建者id
        String creatorId = String.valueOf(((User)request.getSession().getAttribute("user")).getUid());
        try{
            // 创建表单并将表单信息存入数据库
            adminService.createForm(data, realPath, basePath, creatorId);
            // 设定定时任务，控制表单的开放与关闭
        }catch (IOException | NoSuchMethodException e){
            e.printStackTrace();
            return Result.fail("创建失败");
        }
        logAfter(logger, null);
        return Result.succ("创建成功");
    }

    /**
     * 跳转表单信息编辑页面，并提前把表单id存入session
     * @param formId
     * @return
     */
    @RequestMapping("/toEditFormInfo")
    @ResponseBody
    public Result toEditFormInfo(String formId, HttpServletRequest request){
        request.getSession().setAttribute("formId", formId.trim());
        return Result.succ((Object)"editFormInfo.html");
    }

    /**
     * 根据id获取表单部分信息，id从session中获取
     * @param request
     * @return
     */
    @RequestMapping("/getFormInfo")
    @ResponseBody
    public Result getFormInfo(HttpServletRequest request){
        // 取出formId
        Integer formId = Integer.valueOf((String)request.getSession().getAttribute("formId"));
        TableInfo tableInfo = tableInfoService.getFormById(formId);
        return Result.succ(tableInfo);
    }

    /**
     * 修改表单信息
     * @param formId
     * @param startTime
     * @param endTime
     * @param formTitle
     * @return
     * @throws ParseException
     */
    @RequestMapping("/changeFormInfo")
    @ResponseBody
    public Result changeFormInfo(Integer formId, String startTime, String endTime, String formTitle, HttpServletRequest request) throws ParseException, NoSuchMethodException, DocumentException, IOException {
        // 获取当前发布的项目路径
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
//        System.out.println("项目发布路径："+realPath);
        adminService.changeFormInfo(formId, formTitle.trim(), DateUtils.str2date(startTime), DateUtils.str2date(endTime), realPath, BASE_PATH);
        return Result.succ("成功");
    }

    /**
     * 根据id删除表单
     * @param formId
     * @return
     */
    @RequestMapping("/deleteForm")
    @ResponseBody
    public Result deleteForm(Integer formId){
        logBefore(logger, "删除表单，id="+formId);
        Integer ret = tableInfoService.deleteForm(formId);
        logAfter(logger, "删除完毕：结果为"+ret);
        if(ret > 0){
            return Result.succ("删除成功");
        }
        return Result.fail("表单不存在");
    }

    /**
     * 将需要显示的表单id存入session
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
        Integer formId = Integer.valueOf((String) request.getSession().getAttribute("viewFormId"));
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
        // 获取表单全部字段信息以及已填写的信息
        Map ret = adminService.getFormAllFields(formId, realPath);
        return Result.succ(ret);
    }

    /**
     * 改变用户封禁状态
     * @param uid
     * @param status
     * @return
     */
    @RequestMapping("/changeUserStatus")
    @ResponseBody
    public Result changeUserStatus(Integer uid, Integer status){
        int ret = adminService.changeUserStatus(uid, status);
        if(ret > 0){
            return Result.succ("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    /**
     * 到达用户信息编辑页面，保存目标用户id
     * @param uid
     * @return
     */
    @RequestMapping("/toEditUserInfo")
    @ResponseBody
    public Result toEditUserInfo(Integer uid, HttpServletRequest request){
        request.getSession().setAttribute("userId", uid);
        return Result.succ("存放成功");
    }

    /**
     * 获得目标用户的所有信息
     * @param request
     * @return
     */
    @RequestMapping("/getUserAllInfo")
    @ResponseBody
    public Result getUserAllInfo(HttpServletRequest request){
        // 获取用户id，查找用户信息
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if(userId == null){
            return Result.fail("查找失败");
        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            List<Keys> list = adminService.getUserAllInfo(userId);
            map.put("keys", list);
            return Result.succ(map);
        }
    }

    /**
     * 管理员修改目标普通用户数据
     * @param changedUserInfo
     * @return
     */
    @RequestMapping("/modifyUserInfo")
    @ResponseBody
    public Result modifyUserInfoByAdmin(@RequestBody KeysWithUid changedUserInfo){
        System.out.println("userId: "+changedUserInfo);
        // 开始更新相关数据
        int ret = adminService.modifyUserInfoByAdmin(changedUserInfo);
        if(ret > 0){
            return Result.succ("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    /**
     * 获取当前管理员自己的所有信息
     * @param request
     * @return
     */
    @RequestMapping("/getAdminAllInfo")
    @ResponseBody
    public Result getAdminAllInfo(HttpServletRequest request){
        // 获取管理员id
        User user = (User) request.getSession().getAttribute("user");
        Integer uid = user.getUid();
        // 查找信息
        List<Keys> list = adminService.getUserAllInfo(uid);
        return Result.succ(list);
    }

    /**
     * 管理员自己修改自己的信息
     * @param adminInfo
     * @return
     */
    @RequestMapping("/modifyAdminInfoBySelf")
    @ResponseBody
    public Result modifyAdminInfoBySelf(@RequestBody List<Keys> adminInfo, HttpServletRequest request){
        // 获取用户id
        User user = (User) request.getSession().getAttribute("user");
        String uid = String.valueOf(user.getUid());
        int ret = adminService.modifyAdminInfoBySelf(adminInfo, uid);
        if(ret > 0){
            return Result.succ("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    /**
     * 管理员修改自己的登录密码
     * @param oldPwd
     * @param newPwd
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/changeAdminPwd")
    @ResponseBody
    public Result changeAdminPwd(String oldPwd, String newPwd, HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        // 获取用户Id
        Integer uid = user.getUid();
        // 根据Id查找密码并进行验证
        int ret = adminService.changeAdminPwdBySelf(uid, oldPwd, newPwd);
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

    /**
     * 管理员重置用户密码为000000Aa@
     * @param uid
     * @return
     */
    @RequestMapping("/resetUserPwd")
    @ResponseBody
    public Result resetUserPwd(Integer uid){
        int ret = adminService.resetUserPwd(uid);
        if(ret > 0){
            return Result.succ("重置成功");
        }else{
            return Result.fail("重置失败");
        }
    }

    @RequestMapping("/jobList")
    @ResponseBody
    public Result jobList(){
//        System.out.println("访问任务列表");
        List<Object> ret = new ArrayList<>();
        int i = 0;
        ConcurrentHashMap<String, ScheduledFuture> taskMap = TimingTask.getTsakMap();
        for (Object o : taskMap.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i++);
            map.put("name", o);
            map.put("finish", taskMap.get(o).isDone());
            map.put("cancel", taskMap.get(o).isCancelled());
            map.put("remain", taskMap.get(o).getDelay(TimeUnit.SECONDS));
            ret.add(map);
        }
//        System.out.println("任务列表："+ret);
        return Result.succ(0, ret,i);
    }
}
