package wdk0.com.youdeliao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wdk0.com.youdeliao.dto.PostDto;
import wdk0.com.youdeliao.model.Post;
import wdk0.com.youdeliao.model.User;
import wdk0.com.youdeliao.service.PostService;

import javax.servlet.http.HttpServletRequest;

//发帖

@Controller
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping("/posting")
    public String posting() {
        return "posting";
    }

    @PostMapping("/posting")
    public String doPosting(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false)Integer id,
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
        User user = (User) request.getSession().getAttribute("user");
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
        post.setId(id);
        postService.createOrupdate(post);
        return "posting";
    }
    /**
     * @Description: 问题详情编辑页面
     * @Param: [id, model]
     * @return: java.lang.String
     */
    @GetMapping("/post/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model){
        PostDto post = postService.getById(id);
        model.addAttribute("title",post.getTitle());
        model.addAttribute("description",post.getDescription());
        model.addAttribute("tag",post.getTag());
        model.addAttribute("id",post.getId());
        return "posting";
    }

//    删除功能
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id")Integer id){
        PostDto post = postService.getById(id);
        postService.delete(post);
        return "redirect:/profile/posts";
    }
}
