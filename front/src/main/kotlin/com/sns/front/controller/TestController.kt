package com.sns.front.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * 테스트용 컨트롤러
 * @author Hyounglin Jun
 */
@Controller
class TestController {

    @GetMapping("/test")
    fun test():
        String {
        return "pages/test"
    }
}
