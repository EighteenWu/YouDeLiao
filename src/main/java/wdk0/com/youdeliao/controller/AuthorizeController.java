package wdk0.com.youdeliao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wdk0.com.youdeliao.dto.AccessTokenDto;
import wdk0.com.youdeliao.dto.GithubUser;
import wdk0.com.youdeliao.mapper.UserMapper;
import wdk0.com.youdeliao.model.User;
import wdk0.com.youdeliao.provider.GitHubprovider;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.UUID;


@Controller
public class AuthorizeController {

    @Autowired
    private GitHubprovider gitHubprovider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clidentid;

    @Value("${github.client.secret}")
    private String clientseret;

    @Value("${github.clienti.redirect_uri}")
    private String redirecturi;



    @GetMapping("/callback")
//    登录成功后返回首页
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request
                           ){

//        快速创建对象Ctrl+alt+v
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_id(clidentid);
        accessTokenDto.setClient_secret(clientseret);
        accessTokenDto.setRedirect_uri(redirecturi);
        accessTokenDto.setState(state);
        String accessToken = gitHubprovider.getAccessToken(accessTokenDto);
        GithubUser githubUser = gitHubprovider.getUser(accessToken);
//        判断user是否不为空来判定登录装
        if(githubUser!= null){
            //登录成功，写cookie和session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getLogin());
            user.setAccountId((String.valueOf(githubUser.getId())));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }
        else{
            return "redirect:/login";
        }

    }
}
