package com.sns.front.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 * User 관련된 페이지 모음
 * @author Hyounglin Jun
 */
@Controller
class ArticleController {
    @GetMapping("/my-articles")
    fun getFeeds(): String {
        return "pages/feed/my-articles"
    }

    @GetMapping("/write-article")
    fun writeFeed(): String {
        return "pages/feed/write-article"
    }

    @GetMapping("/modify-articles/{articleId}")
    fun modifyFeed(
        @PathVariable articleId: Int,
        model: Model,
    ): String {
        model.addAttribute("articleId", articleId)
        return "pages/feed/modify-article"
    }
}
