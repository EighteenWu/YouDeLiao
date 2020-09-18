package wdk0.com.youdeliao.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import wdk0.com.youdeliao.exception.CustomizeException;



@ControllerAdvice
public class CustomizeErrorHandle {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex, Model model) {
       if (ex instanceof CustomizeException){
           model.addAttribute("message",ex.getMessage());
       }else {
           model.addAttribute("message","服务器罢工了，稍后再访问试试");
       }
        return new ModelAndView("error");
    }
}
