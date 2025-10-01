package com.work.service.config;

import com.work.service.util.CurrentUserIdArgumentResolver;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("{file.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射上传目录为静态资源路径
        registry.addResourceHandler("/avatars/**")
                .addResourceLocations("file:" + uploadDir + "avatars/");
    }

    @Resource
    private CurrentUserIdArgumentResolver currentUserIdArgumentResolver;


    //将自定义的CurrentUserIdArgumentResolver参数解析器添加到Spring MVC的参数解析器列表中，使其能够处理控制器方法中的特定参数类型。
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers)
    {
        resolvers.add(currentUserIdArgumentResolver);
    }

}
