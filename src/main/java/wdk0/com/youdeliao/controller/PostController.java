package wdk0.com.youdeliao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wdk0.com.youdeliao.mapper.PostMapper;
import wdk0.com.youdeliao.mapper.UserMapper;
import wdk0.com.youdeliao.model.Post;
import wdk0.com.youdeliao.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

//发帖

@Controller
public class PostController {

    @Autowired
    private PostMapper postMapper ;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/posting")
    public String posting() {
        return "posting";
    }

    @PostMapping("/posting")
    public String doPosting(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model) {

        User user = new User();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
//        未登录，直接返回到首页
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "posting";
        }

        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setTag(tag);
        post.setCreator(user.getId());
        post.setGmtCreate(System.currentTimeMillis());
        post.setGmtModified(post.getGmtCreate());
        postMapper.create(post);
        return "posting";
    }
}
