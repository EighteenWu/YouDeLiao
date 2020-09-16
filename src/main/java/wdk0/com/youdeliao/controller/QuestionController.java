package wdk0.com.youdeliao.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wdk0.com.youdeliao.dto.PostDto;
import wdk0.com.youdeliao.mapper.PostMapper;
import wdk0.com.youdeliao.model.Post;
import wdk0.com.youdeliao.service.PostService;

/**
* @Description: 问题详情页面
* @Param:
* @return:
*/
@Controller
public class QuestionController {

    @Autowired
    private PostService postService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id,
                           Model model){
        PostDto postDto = postService.getById(id);
        model.addAttribute("post",postDto);
        return "question";
    }

}