package com.sharingif.cube.dark.knight.analysis.app.runtime;

import com.sharingif.cube.context.annotation.ExtendedAnnotationBeanNameGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Bootstrap
 * 2017/5/21 下午3:32
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@EnableAutoConfiguration
@ComponentScan(
        basePackages = "com.sharingif.cube.dark.knight.analysis.*.controller"
        ,nameGenerator = ExtendedAnnotationBeanNameGenerator.class
        ,useDefaultFilters= false
        ,includeFilters = {
            @Filter(type=FilterType.ANNOTATION,value=Controller.class)
        })
public class Bootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(ApplicationContext.class)
                .child(Bootstrap.class)
                .initializers(new ApplicationContextInitializer<GenericApplicationContext>() {
                    @Override
                    public void initialize(GenericApplicationContext applicationContext) {
                        applicationContext.setAllowBeanDefinitionOverriding(false);
                    }
                }).run(args);
    }

    @EnableAutoConfiguration
    @ComponentScan(
            basePackages = "com.sharingif.cube.dark.knight.analysis.*.dao,com.sharingif.cube.dark.knight.analysis.*.service,com.sharingif.cube.dark.knight.analysis.app.autoconfigure,com.sharingif.cube.spring.boot"
            ,nameGenerator = ExtendedAnnotationBeanNameGenerator.class
            ,useDefaultFilters= false
            ,includeFilters={
                @Filter(type= FilterType.ANNOTATION,value=Repository.class)
                ,@Filter(type=FilterType.ANNOTATION,value=Service.class)
                ,@Filter(type=FilterType.ANNOTATION,value=Component.class)
            })
    protected static class ApplicationContext {
        public static void main(String[] args) {
            new SpringApplicationBuilder(ApplicationContext.class)
                    .initializers(new ApplicationContextInitializer<GenericApplicationContext>() {
                        @Override
                        public void initialize(GenericApplicationContext applicationContext) {
                            applicationContext.setAllowBeanDefinitionOverriding(false);
                        }
                    }).run(args);
        }
    }

}
