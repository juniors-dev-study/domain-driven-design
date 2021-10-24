package com.sns.front.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * @author Hyounglin Jun
 */
@Controller
class HomeController {

    @GetMapping("/")
    fun home(): String {
        return "home/home"
    }

}
