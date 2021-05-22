package team.tb.controller;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.tb.common.FormInfo;
import team.tb.common.KeysWithUid;
import team.tb.common.Result;
import team.tb.pojo.*;
import team.tb.service.RootService;
import team.tb.utils.DateUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/root")
@Controller
public class RootController extends BaseController {

    // 本地项目路径
    private static final String BASE_PATH = "E:\\JetBrainJava\\tableOnline\\src\\main\\webapp\\WEB-INF";

    @Autowired
    private RootService rootService;

    /**
     * 获取所有普通用户信息
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
        List<User> list = rootService.getUserList(page, limit);
        if(list != null){
            int count = rootService.getUserCount();
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
        List<Grade> list = rootService.getGrade();
        return Result.succ(list);
    }

    /**
     * 获取对应年级的所有班级
     * @param gradeId
     * @return
     */
    @RequestMapping("/getDepartment")
    @ResponseBody
    public Result getDepartment(Integer[] gradeId){
        List<Department> list = rootService.getDepartmentByGrade(gradeId);
        return Result.succ(list);
    }

    /**
     * 根据院系id查找专业
     * @param departmentId
     * @return
     */
    @RequestMapping("/getMajor")
    @ResponseBody
    public Result getMajor(Integer[] departmentId){
        List<Major> list = rootService.getMajorByDepartmentId(departmentId);
        return Result.succ(list);
    }

    /**
     * 获取对应专业的班级
     * @param majorId
     * @return
     */
    @RequestMapping("/getClazz")
    @ResponseBody
    public Result getClazz(Integer[] majorId){
        List<Clazz> list = rootService.getClazzByMajorId(majorId);
        return Result.succ(list);
    }

    /**
     * 根据班级查找学生信息
     * @param clazzId
     * @return
     */
    @RequestMapping("/getStudent")
    @ResponseBody
    public Result getStudent(Integer[] clazzId){
        List<User> list = rootService.getUserByClass(clazzId);
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
        if(page == null){
            page = 0;
            limit = 10;
        }else{
            page = (page - 1)*limit;
        }
        List<User> list = rootService.getUserOnCondition(grade, department, major, clazz, username, page, limit);
        int count = rootService.getUserCountOnCondition(grade, department, major, clazz, username);
        return Result.succ(0, list, count);
    }

    /**
     * 获取所有的管理员数据
     * @return
     */
    @RequestMapping("/getAdminList")
    @ResponseBody
    public Result getAdminList(Integer page, Integer limit){
        // 处理分页
        if(page == null){
            page = 0;
            limit = 10;
        }else{
            page = (page - 1)*limit;
        }
        List<User> list = rootService.getAdminList(page, limit);
        int count = rootService.getAdminCount();
        return Result.succ(0, list, count);
    }

    /**
     * 根据名字模糊搜索管理员
     * @param username
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/searchAdmin")
    @ResponseBody
    public Result searchAdmin(String username, Integer page, Integer limit){
        if(page == null){
            page = 0;
            limit = 10;
        }else{
            page = (page - 1)*limit;
        }
        List<User> list = rootService.searchAdmin(username, page, limit);
        int count = rootService.getSearchAdminCount(username);
        return Result.succ(0,list, count);
    }

    /**
     * 获取表单列表请求，找出所有发布的表单
     * @param page 分页信息
     * @param limit 页面大小
     * @return
     */
    @RequestMapping("/getFormList")
    @ResponseBody
    public Result getFormList(Integer page, Integer limit){
        // 计算分页信息，获取相应数据条数
        if(page != null || limit != null){
            page = (page - 1)*limit;
        }else{
            page = 0;
            limit = 10;
        }
        List<TableInfo> list = null;
        Integer count = null;
        list = rootService.getFormList(page, limit);
        count = rootService.getFormCount();
        if(list != null){
            return Result.succ(0, list, count);
        }
        return Result.fail("查找数据错误");
    }

    /**
     * 根据条件查询表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/searchForm")
    @ResponseBody
    public Result searchForm(String formTitle, String startTime, String endTime, Integer page, Integer limit){
        // 计算分页信息，获取相应数据条数
        if(page == null){
            page = 0;
            limit = 10;
        }else{
            page = (page - 1)*limit;
        }
        List<TableInfo> list = rootService.searchForm(formTitle, startTime, endTime, page, limit);
        Integer count = rootService.getFormCountOnCondition(formTitle, startTime, endTime);
        if(list != null){
            return Result.succ(0, list, count);
        }
        return Result.fail("查找失败");
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
        TableInfo tableInfo = rootService.getFormById(formId);
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
        rootService.changeFormInfo(formId, formTitle.trim(), DateUtils.str2date(startTime), DateUtils.str2date(endTime), realPath, BASE_PATH);
        return Result.succ("成功");
    }

    /**
     * 根据id删除表单，并不真正清除磁盘上的文件
     * @param formId
     * @return
     */
    @RequestMapping("/deleteForm")
    @ResponseBody
    public Result deleteForm(Integer formId){
        logBefore(logger, "删除表单，id="+formId);
        Integer ret = rootService.deleteForm(formId);
        logAfter(logger, "删除完毕：结果为"+ret);
        if(ret > 0){
            return Result.succ("删除成功");
        }
        return Result.fail("表单不存在");
    }

    /**
     * 将需要显示的表单id存入session，以便跳转到详情页可以直接获取
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
     * 查看被删除表单信息
     * @param formId
     * @param request
     * @return
     */
    @RequestMapping("/toDeleteFormDetail")
    @ResponseBody
    public Result toDeleteFormDetail(String formId, HttpServletRequest request){
        request.getSession().setAttribute("viewFormId", formId.trim());
        request.getSession().setAttribute("formStatus", 0);
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
        HttpSession session = request.getSession();
        Integer formId = Integer.valueOf((String) session.getAttribute("viewFormId"));
        Integer formStatus = (Integer) session.getAttribute("formStatus"); // 获取表单状态，确定搜索哪种类型的表单
        if(formStatus == null) {
            formStatus = 1;
        }
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
        session.removeAttribute("formStatus");
        // 获取表单全部字段信息以及已填写的信息
        Map ret = rootService.getFormAllFields(formId, realPath, formStatus);
        return Result.succ(ret);
    }

    /**
     * 获得已存在数据库的字段信息
     * @return
     */
    @RequestMapping("/getAllKeys")
    @ResponseBody
    public Result getAllKeys(){
        List<Keys> list = rootService.getAllKeys();
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
            rootService.createForm(data, realPath, basePath, creatorId);
            // 设定定时任务，控制表单的开放与关闭
        }catch (IOException | NoSuchMethodException e){
            e.printStackTrace();
            return Result.fail("创建失败");
        }
        logAfter(logger, null);
        return Result.succ("创建成功");
    }

    /**
     * 普通用户封禁与解封
     * @param uid
     * @param status
     * @return
     */
    @RequestMapping("/changeUserStatus")
    @ResponseBody
    public Result changeUserStatus(Integer uid, Integer status){
        int ret = rootService.changeUserStatus(uid, status);
        if(ret > 0){
            return Result.succ("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    /**
     * 管理员封禁与解封
     * @param uid
     * @param status
     * @return
     */
    @RequestMapping("/changeAdminStatus")
    @ResponseBody
    public Result changeAdminStatus(Integer uid, Integer status){
        int ret = rootService.changeAdminStatus(uid, status);
        if(ret > 0){
            return Result.succ("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    /**
     * 提升与收回管理员权限
     * @param uid
     * @param level
     * @return
     */
    @RequestMapping("/changeUserLevel")
    @ResponseBody
    public Result changeUserLevel(Integer uid, Integer level){
        if(uid == null || level == null){
            return Result.fail("修改失败");
        }
        int ret = rootService.changeUserLevel(uid, level);
        if(ret > 0){
            return Result.succ("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    /**
     * 获得被标记删除的表单
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getDeleteFormList")
    @ResponseBody
    public Result getDeleteFormList(Integer page, Integer limit){
        if(page == null){
            page = 0;
            limit = 10;
        }else{
            page = (page - 1)*limit;
        }
        List<TableInfo> list = rootService.getDeleteForm(page, limit);
        int count = rootService.getDeleteFormCount();
        return Result.succ(0, list, count);
    }

    /**
     * 按条件搜索被标记删除的表单
     * @param formTitle
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/searchDeleteForm")
    @ResponseBody
    public Result searchDeleteForm(String formTitle, String startTime, String endTime, Integer page, Integer limit){
        if(page == null){
            page = 0;
            limit = 10;
        }else{
            page = (page - 1) * limit;
        }
        List<TableInfo> list = rootService.searchDeleteForm(formTitle, startTime, endTime, page, limit);
        int count = rootService.searchDeleteFormCount(formTitle, startTime, endTime);
        return Result.succ(0, list, count);
    }

    /**
     * 恢复被删除的表单
     * @param formId
     * @return
     */
    @RequestMapping("/resetDeleteForm")
    @ResponseBody
    public Result resetDeleteForm(Integer formId){
        if(formId == null){
            return Result.fail("参数为空");
        }else{
            int ret = rootService.resetDeleteForm(formId);
            if(ret > 0){
                return Result.succ("修改成功");
            }else{
                return Result.fail("修改失败");
            }
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
     * 获得用户的所有信息
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
            List<Keys> list = rootService.getUserAllInfo(userId);
            map.put("keys", list);
            return Result.succ(map);
        }
    }

    /**
     * root管理员修改目标普通用户数据
     * @param changedUserInfo
     * @return
     */
    @RequestMapping("/modifyUserInfo")
    @ResponseBody
    public Result modifyUserInfoByRoot(@RequestBody KeysWithUid changedUserInfo){
        System.out.println("userId: "+changedUserInfo);
        // 开始更新相关数据
        int ret = rootService.modifyUserInfoByRoot(changedUserInfo);
        if(ret > 0){
            return Result.succ("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    /**
     * 获取当前root管理员自己的所有信息
     * @param request
     * @return
     */
    @RequestMapping("/getRootAllInfo")
    @ResponseBody
    public Result getRootAllInfo(HttpServletRequest request){
        // 获取管理员id
        User user = (User) request.getSession().getAttribute("user");
        Integer uid = user.getUid();
        // 查找信息
        List<Keys> list = rootService.getUserAllInfo(uid);
        return Result.succ(list);
    }

    /**
     * 修改root用户自身的信息
     * @return
     */
    @RequestMapping("/modifyRootInfoBySelf")
    @ResponseBody
    public Result modifyRootInfoBySelf(@RequestBody List<Keys> rootInfo, HttpServletRequest request){
        // 获取用户id
        User user = (User) request.getSession().getAttribute("user");
        String uid = String.valueOf(user.getUid());
        int ret = rootService.modifyRootInfoBySelf(rootInfo, uid);
        if(ret > 0){
            return Result.succ("修改成功");
        }else{
            return Result.fail("修改失败");
        }
    }

    /**
     * root管理员修改自己的登录密码
     * @param oldPwd
     * @param newPwd
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/changeRootPwd")
    @ResponseBody
    public Result changeRootPwd(String oldPwd, String newPwd, HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        // 获取用户Id
        Integer uid = user.getUid();
        // 根据Id查找密码并进行验证
        int ret = rootService.changeRootPwdBySelf(uid, oldPwd, newPwd);
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
     * root管理员重置用户密码为000000Aa@
     * @param uid
     * @return
     */
    @RequestMapping("/resetUserPwd")
    @ResponseBody
    public Result resetUserPwd(Integer uid){
        int ret = rootService.resetUserPwd(uid);
        if(ret > 0){
            return Result.succ("重置成功");
        }else{
            return Result.fail("重置失败");
        }
    }
}
