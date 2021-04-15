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
}
