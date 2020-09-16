package wdk0.com.youdeliao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("wdk0.com.youdeliao.mapper")
public class YoudeliaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoudeliaoApplication.class, args);
    }

}
