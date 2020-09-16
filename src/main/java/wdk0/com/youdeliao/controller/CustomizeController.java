package wdk0.com.youdeliao.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller("/error")
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }
}
