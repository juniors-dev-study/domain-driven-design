package com.sns.front.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * User 관련된 페이지 모음
 * @author Hyounglin Jun
 */
@Controller
class FeedController {
    @GetMapping("/my-feeds")
    fun getFeeds(): String {
        return "pages/feed/my-feeds"
    }

    @GetMapping("/write-feed")
    fun writeFeed(): String {
        return "pages/feed/write-feed"
    }

    @GetMapping("/modify-feed")
    fun modifyFeed(): String {
        return "pages/feed/modify-feed"
    }
}
