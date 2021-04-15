package team.tb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.tb.common.Result;
import team.tb.pojo.TableInfo;
import team.tb.pojo.User;
import team.tb.service.AdminService;
import team.tb.service.UserService;
import team.tb.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
}
