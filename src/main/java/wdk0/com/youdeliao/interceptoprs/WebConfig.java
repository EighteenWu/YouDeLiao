package wdk0.com.youdeliao.interceptoprs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//筛选器配置
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SeesionIntercrptor seesionIntercrptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(seesionIntercrptor).addPathPatterns("/**");
    }
}