package wdk0.com.youdeliao.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdk0.com.youdeliao.dto.PostDto;
import wdk0.com.youdeliao.mapper.PostMapper;
import wdk0.com.youdeliao.mapper.UserMapper;
import wdk0.com.youdeliao.model.Post;
import wdk0.com.youdeliao.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;


    public List<PostDto> list(Integer page, Integer size) {
//        定义分页
       Integer offset = size*(page-1);
        List<Post> posts = postMapper.list(offset,size);
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : posts) {
           User user =  userMapper.findById(post.getCreator());
           PostDto postDto  = new PostDto();
//           copyProperties 将原来的对象通过迅速拷贝到另外一个对象
            BeanUtils.copyProperties(post,postDto);
            postDto.setUser(user);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }
}
