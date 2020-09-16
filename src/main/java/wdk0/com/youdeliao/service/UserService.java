package wdk0.com.youdeliao.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdk0.com.youdeliao.mapper.UserMapper;
import wdk0.com.youdeliao.model.User;
import wdk0.com.youdeliao.model.UserExample;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;

    public void createUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size() == 0){
//            创建新用户
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
//            更新用户session
            User dbUser = userList.get(0);
            User updateUser = new User();
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,userExample);
        }
    }
}
