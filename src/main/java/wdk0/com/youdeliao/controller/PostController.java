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
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest request,
            Model model) {
//        判断标题，内容，标签是否未空，空的话，页面给告警信息
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "posting";
        }
        if (description == null || description ==""){
            model.addAttribute("error","帖子内容不能为空");
            return "posting";
        }
        if (tag == null ||tag == ""){
            model.addAttribute("error","帖子标签不能为空");
            return "posting";
        }

        User user = new User();
        Cookie[] cookies = request.getCookies();
        if (cookies !=null && cookies.length !=0) {
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
        }
//        未登录，直接返回到首页
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "redirect:/login";
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
