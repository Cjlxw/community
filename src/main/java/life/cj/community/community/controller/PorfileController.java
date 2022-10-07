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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/23 17:09
 */
@Controller
public class PorfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    private String profile(@PathVariable(name = "action") String action,
                           Model model,
                           HttpServletRequest request,
                           Page page,
                           @RequestParam(value = "pageNum", required = false) Integer pageNum) {
//        Cookie[] cookies = request.getCookies();
//        String token = "";
//        if (cookies != null && cookies.length != 0) {
//            for (Cookie cookie : cookies) {
//                if ("token".equals(cookie.getName())) {
//                    token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);
//                    }
//                    break;
//                }
//            }
//        }

        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if (page.getPageSize() == null) {
            page.setPageNum(pageNum == null ? 1 : page.getPageNum());
            page.setPageSize(page.getPageSize() == null ? 5 : page.getPageSize());
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        PaginationDTO pagination = questionService.list(user.getId(), page);
        model.addAttribute("pagination", pagination);

        return "profile";
    }
}
