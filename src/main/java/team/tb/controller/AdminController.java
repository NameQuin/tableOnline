package team.tb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.tb.common.Result;
import team.tb.dao.GradeMapper;
import team.tb.pojo.*;
import team.tb.service.*;
import team.tb.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

/**
 * 管理员可以做的操作
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
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


    /**
     * 接口描述：获取表单列表请求，找出管理员自己发布的表单
     * @param request
     * @param page 分页信息
     * @param limit 页面大小
     * @return
     */
    @RequestMapping("/formList")
    @ResponseBody
    public Result getFormList(HttpServletRequest request, Integer page, Integer limit){
        // 获取管理员id，利用id查找其发布的表单
        User user = (User) request.getSession().getAttribute("user");
        Integer id = user.getUid();
        // 计算分页信息，获取相应数据条数
        page = (page - 1)*limit;
        List<TableInfo> list = null;
        Integer count = null;
        if(id != null){
            list = adminService.getFormListByAdminId(id, page, limit);
            count = adminService.getFormCount(id);
        }
        System.out.println("请求数据===========>"+list);
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
        // 传入的字符串参数只会为空，不会为null
        User user = (User) request.getSession().getAttribute("user");
        Integer id = user.getUid();
        // 计算分页信息，获取相应数据条数
        page = (page - 1)*limit;
        List<TableInfo> list = adminService.searchForm(id, formTitle, startTime, endTime, page, limit);
        Integer count = adminService.getFormCountOnCondition(id, formTitle, startTime, endTime);
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
        page = (page - 1)*limit;
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
     * @param gradeId
     * @return
     */
    @RequestMapping("/getDepartment")
    @ResponseBody
    public Result getDepartment(Integer gradeId){
        List<Department> list = departmentService.getDepartmentByGrade(gradeId);
        return Result.succ(list);
    }

    /**
     * 获取对应专业的班级
     * @param majorId
     * @return
     */
    @RequestMapping("/getClazz")
    @ResponseBody
    public Result getClazz(Integer majorId){
        if(majorId != null){
            List<Clazz> list = clazzService.getClazzByDepartmentId(majorId);
            return Result.succ(list);
        }else{
            return Result.fail("参数为空");
        }
    }

    /**
     * 根据院系id查找专业
     * @param departmentId
     * @return
     */
    @RequestMapping("/getMajor")
    @ResponseBody
    public Result getMajor(Integer departmentId){
        if(departmentId != null){
            List<Major> list = majorService.getMajorByDepartmentId(departmentId);
            return Result.succ(list);
        }else{
            return Result.fail("参数为空");
        }
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
}
