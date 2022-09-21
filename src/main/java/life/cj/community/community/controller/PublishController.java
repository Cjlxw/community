package life.cj.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/21 15:55
 */
@Controller
public class PublishController {

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

}
