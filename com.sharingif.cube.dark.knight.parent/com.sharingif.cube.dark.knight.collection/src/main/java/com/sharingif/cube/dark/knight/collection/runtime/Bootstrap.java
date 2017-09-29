package com.sharingif.cube.dark.knight.collection.runtime;

import com.sharingif.cube.context.annotation.ExtendedAnnotationBeanNameGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 启动类
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午3:25
 */
@EnableAutoConfiguration(exclude={WebMvcAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@ComponentScan(
        basePackages = "com.sharingif.cube.spring.boot,com.sharingif.cube.dark.knight.collection"
        ,nameGenerator = ExtendedAnnotationBeanNameGenerator.class
        ,useDefaultFilters= false
        ,includeFilters = {
        @ComponentScan.Filter(type= FilterType.ANNOTATION,value=Component.class)
})
public class Bootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Bootstrap.class)
                .initializers(new ApplicationContextInitializer<GenericApplicationContext>() {
                    @Override
                    public void initialize(GenericApplicationContext applicationContext) {
                        applicationContext.setAllowBeanDefinitionOverriding(false);
                    }
                }).run(args);

    }

}
