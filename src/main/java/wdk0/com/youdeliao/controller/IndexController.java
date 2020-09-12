package wdk0.com.youdeliao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wdk0.com.youdeliao.dto.GithubUser;
import wdk0.com.youdeliao.dto.PaginationDto;
import wdk0.com.youdeliao.dto.PostDto;
import wdk0.com.youdeliao.mapper.PostMapper;
import wdk0.com.youdeliao.mapper.UserMapper;
import wdk0.com.youdeliao.model.Post;
import wdk0.com.youdeliao.model.User;
import wdk0.com.youdeliao.service.PostService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size){

        Cookie[] cookies = request.getCookies();
//        首页cookie为空的时候不进行操作
        if (cookies !=null && cookies.length !=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        PaginationDto pagination = postService.list(page,size);
        model.addAttribute("pagination",pagination);

        return "index";
    }
}
