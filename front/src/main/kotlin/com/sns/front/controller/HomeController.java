package com.sns.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Hyounglin Jun
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    String indexPage(Model model){
        return "home";
    }
}
