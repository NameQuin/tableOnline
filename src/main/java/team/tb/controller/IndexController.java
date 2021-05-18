package team.tb.controller;

import cn.dsna.util.images.ValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 中转静态页面，保证经过springmvc的模板视图解析器
 */
@Controller
@RequestMapping("/pages")
public class IndexController {

    @RequestMapping("/admin/adminPanel.html")
    public String adminPanel(){
        return "admin/adminPanel";
    }

    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }

    @RequestMapping("/user/userPanel.html")
    public String userPanel(){
        return "user/userPanel";
    }

    @RequestMapping("/root/rootPanel.html")
    public String rootPanel(){
        return "root/rootPanel";
    }

    @RequestMapping("/admin/formList.html")
    public String formListInAdmin(){
        return "admin/formList";
    }

    @RequestMapping("/admin/addForm.html")
    public String addFormInAdmin(){
        return "admin/addForm";
    }

    @RequestMapping("/admin/userList.html")
    public String userListInAdmin(){
        return "admin/userList";
    }

    @RequestMapping("/admin/editFormInfo.html")
    public String editFormInfo(){
        return "admin/editFormInfo";
    }

    @RequestMapping("/admin/viewForm.html")
    public String viewForm(){
        return "admin/viewForm";
    }

    @RequestMapping("/user/formList.html")
    public String formList(){
        return "user/formList";
    }

    @RequestMapping("/user/viewForm.html")
    public String viewForm2(){
        return "user/viewForm";
    }

    @RequestMapping("/root/userList.html")
    public String userList2(){
        return "root/userList";
    }

    @RequestMapping("/root/adminList.html")
    public String adminList(){
        return "root/adminList";
    }

    @RequestMapping("/root/addForm.html")
    public String addFormInRoot(){
        return "root/addForm";
    }

    @RequestMapping("/root/editFormInfo.html")
    public String editFormInfo2(){
        return "root/editFormInfo";
    }

    @RequestMapping("/root/viewForm.html")
    public String viewForm3(){
        return "root/viewForm";
    }

    @RequestMapping("/root/formList.html")
    public String formListInRoot(){
        return "root/formList";
    }

    @RequestMapping("/root/deletedFormList.html")
    public String deletedFormList(){
        return "root/deletedFormList";
    }
    /**
     * 获取验证码
     * @param request
     * @param response
     */
    @RequestMapping("/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response){
        //生成验证码对象, lineCount是干扰条数
        ValidateCode validateCode = new ValidateCode(120, 40, 4, 50);
        //在后台设置session存放正确的验证码
        request.getSession().setAttribute("code", validateCode.getCode());
        try{
            //将验证码图片以二进制方式返回给前端
            validateCode.write(response.getOutputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/admin/jobList.html")
    public String jobList(){
        return "admin/jobList";
    }
}
