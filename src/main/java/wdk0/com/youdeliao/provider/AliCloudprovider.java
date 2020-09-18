package wdk0.com.youdeliao.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AliCloudprovider {

    @Value("${ali.oss.id}")
    private String accessKeyId;

    @Value("${ali.oss.secret}")
    private String accessKeySecret;

    @Value("${ali.oss.bucketName}")
    private String bucketName;

    @Value("${ali.oss.folder}")
    private String folder;


}
