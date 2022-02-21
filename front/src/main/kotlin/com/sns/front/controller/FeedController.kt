package com.sns.front.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * User 관련된 페이지 모음
 * @author Hyounglin Jun
 */
@Controller
class FeedController {
    @GetMapping("/feeds")
    fun getFeeds(): String {
        return "pages/feed/feeds"
    }
}
