package life.cj.community.community.controller;

import life.cj.community.community.dto.PaginationDTO;
import life.cj.community.community.mapper.UserMapper;
import life.cj.community.community.model.Page;
import life.cj.community.community.model.Users;
import life.cj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/19 16:27
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        Page page,
                        @RequestParam(value = "pageNum",required = false) Integer pageNum){
        if (page.getPageSize() == null) {
            page.setPageNum(pageNum != null ? pageNum : 1);
            page.setPageSize(5);
        }

        PaginationDTO pagination = questionService.list(page);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
