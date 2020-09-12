package wdk0.com.youdeliao.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import wdk0.com.youdeliao.dto.PaginationDto;
import wdk0.com.youdeliao.mapper.UserMapper;
import wdk0.com.youdeliao.model.User;
import wdk0.com.youdeliao.service.PostService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostService postService;

    
    /** 
    * @Description: 通过action来进行前端页面跳转和标题展示 
    * @Param: [action, model, request, page, size]
    * @return: java.lang.String
    */
    @GetMapping("/profile/{action}")
    public String profile(
            @PathVariable(name = "action")String action,
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "page",defaultValue = "1")Integer page,
            @RequestParam(name = "size",defaultValue = "5")Integer size){



        User user =null;
        Cookie[] cookies = request.getCookies();
//        首页cookie为空的时候不进行操作
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

        if (user == null){
            return "redirect:/login";
        }


        if ("posts".equals(action)){
            model.addAttribute("section","posts");
            model.addAttribute("sectionName","我的提问");
        } else if ("comment".equals(action)){
            model.addAttribute("section","comment");
            model.addAttribute("sectionName","最新回复");
        } else if ("allfocus".equals(action)) {
            model.addAttribute("section", "allfocus");
            model.addAttribute("sectionName", "我关注的问题");
        } else if ("invitepost".equals(action)){
            model.addAttribute("section", "invitepost");
            model.addAttribute("sectionName", "邀请我回复的问题");
        }

        PaginationDto paginationDto = postService.list(user.getId(),page,size);
        model.addAttribute("pagination",paginationDto);
        return "profile";
    }
}
