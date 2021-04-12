package team.tb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 中转静态页面
 */
@Controller
@RequestMapping("/pages")
public class IndexController {

    @RequestMapping("/adminPanel.html")
    public String adminPanel(){
        return "adminPanel";
    }

    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }
}
