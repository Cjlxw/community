package life.cj.community.community.controller;

import life.cj.community.community.mapper.PublishMapper;
import life.cj.community.community.mapper.UserMapper;
import life.cj.community.community.model.Question;
import life.cj.community.community.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/21 15:55
 */
@Controller
public class PublishController {

    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());

        if (question.getTitle() == null || "".equals(question.getTitle())) {
            model.addAttribute("msg", "标题不能为空!");
            return "publish";
        }
        if (question.getDescription() == null || "".equals(question.getDescription())) {
            model.addAttribute("msg", "问题补充不能为空!");
            return "publish";
        }
        if (question.getTag() == null || "".equals(question.getTag())) {
            model.addAttribute("msg", "标签不能为空!");
            return "publish";
        }

        Cookie[] cookies = request.getCookies();
        Users user = null;
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                break;
            }
        }
        if (user == null || "".equals(user.getAccountId())) {
            model.addAttribute("msg", "用户未登录!");
            return "publish";
        }

        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        publishMapper.create(question);

        return "redirect:/";
    }

}
