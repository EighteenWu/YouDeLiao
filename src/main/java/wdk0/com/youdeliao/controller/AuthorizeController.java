package wdk0.com.youdeliao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wdk0.com.youdeliao.dto.AccessTokenDto;
import wdk0.com.youdeliao.dto.GithubUser;
import wdk0.com.youdeliao.model.User;
import wdk0.com.youdeliao.provider.GitHubprovider;
import wdk0.com.youdeliao.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {

    @Autowired
    private GitHubprovider gitHubprovider;

    @Autowired
    private UserService userService;

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
                           HttpServletResponse response
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

//        判断user是否不为空来判定登录
        if(githubUser != null && githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getLogin());
            user.setAccountId((String.valueOf(githubUser.getId())));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createUpdate(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }
        else{
            return "redirect:/login";
        }

    }
//    登处功能
    @GetMapping("/logout")
    public  String logout(HttpServletRequest request,
                         HttpServletResponse response){
//        建立一个新的空cookie，将其类型设置为立即清除，再添加进去
        request.getSession().removeAttribute("user");
       Cookie cookie = new Cookie("token","null");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
