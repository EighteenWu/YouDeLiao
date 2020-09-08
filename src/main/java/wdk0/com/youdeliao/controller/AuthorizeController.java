package wdk0.com.youdeliao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wdk0.com.youdeliao.dto.AccessTokenDto;
import wdk0.com.youdeliao.dto.GithubUser;
import wdk0.com.youdeliao.provider.GitHubprovider;


@Controller
public class AuthorizeController {

    @Autowired
    private GitHubprovider gitHubprovider;

    @GetMapping("/callback")
//    登录成功后返回首页
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){

//        快速创建对象Ctrl+alt+v
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_id("06803cdbd4fdac1a0136");
        accessTokenDto.setClient_secret("3a110212fdf5caa1cc9980a576ab4f6103cdf2e2");
        accessTokenDto.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDto.setState(state);
        String accessToken = gitHubprovider.getAccessToken(accessTokenDto);
        GithubUser user = gitHubprovider.getUser(accessToken);
        System.out.println(user.getLogin());
        return "index";
    }


}
