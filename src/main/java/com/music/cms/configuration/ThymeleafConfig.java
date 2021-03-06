package com.music.cms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix( "/WEB-INF/templates/" );
        templateResolver.setSuffix( ".html" );
        templateResolver.setTemplateMode( "HTML5" );

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {

//        Set<IDialect> dialects = new LinkedHashSet<IDialect>();
//        //dialects.add(new TilesDialect());
//        dialects.add(new SpringSecurityDialect());
//        //dialects.add(new ConditionalCommentsDialect());


        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver( templateResolver() );
        templateEngine.addDialect(new SpringSecurityDialect());

        return templateEngine;
    }

//    @Bean
//    public SpringSecurityDialect springSecurityDialect() {
//        return new SpringSecurityDialect();
//    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine( templateEngine() );
        viewResolver.setOrder( 1 );

        return viewResolver;
    }

}
