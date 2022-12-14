package life.cj.community.community.interceptor;

import life.cj.community.community.mapper.UserMapper;
import life.cj.community.community.mapper.UsersMapper;
import life.cj.community.community.model.Users;
import life.cj.community.community.model.UsersExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/10/7 14:38
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String token = "";
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    UsersExample usersExample = new UsersExample();
                    usersExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<Users> user = usersMapper.selectByExample(usersExample);
                    if (user.size() != 0) {
                        request.getSession().setAttribute("user", user.get(0));
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
