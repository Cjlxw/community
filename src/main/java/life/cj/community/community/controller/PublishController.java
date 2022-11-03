package life.cj.community.community.controller;

import life.cj.community.community.dto.QuestionDTO;
import life.cj.community.community.mapper.QuestionMapper;
import life.cj.community.community.mapper.UserMapper;
import life.cj.community.community.model.Question;
import life.cj.community.community.model.Users;
import life.cj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model){
        QuestionDTO question = questionService.getById(id);

        model.addAttribute("title", question.getTitle().trim());
        model.addAttribute("description", question.getDescription().trim());
        model.addAttribute("tag", question.getTag().trim());
        model.addAttribute("id", question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question,
                            @RequestParam(name = "id", required = false) Integer id,
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

        Users user = (Users) request.getSession().getAttribute("user");

        if (user == null || "".equals(user.getAccountId())) {
            model.addAttribute("msg", "用户未登录!");
            return "publish";
        }

        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);

        return "redirect:/";
    }

}
