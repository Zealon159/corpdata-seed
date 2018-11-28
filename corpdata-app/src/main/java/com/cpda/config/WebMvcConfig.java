package com.cpda.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther: Zealon
 * @Date: 2018-08-28 10:37
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    /*//上传附件物理文件夹目录
    @Value("${web.upload.file_path}")
    private String uploadFolder;

    //上传附件web访问目录
    @Value("${web.upload.access_path}")
    private String uploadAccessPath;

    //注册web文件资源访问地址
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadAccessPath).addResourceLocations("file:" + uploadFolder);
    }*/

}
