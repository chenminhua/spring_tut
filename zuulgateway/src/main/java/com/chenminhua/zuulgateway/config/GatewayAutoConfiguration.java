package com.chenminhua.zuulgateway.config;

import com.chenminhua.zuulgateway.filters.AuthFilter;
import com.chenminhua.zuulgateway.filters.OkHttpHostRoutingFilter;
import com.chenminhua.zuulgateway.filters.SignatureFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GatewayAutoConfiguration {

    @Bean
    AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    SignatureFilter signatureFilter() {
        return new SignatureFilter();
    }

    @Bean
    OkHttpHostRoutingFilter hostRoutingFilter() {
        return new OkHttpHostRoutingFilter();
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        CorsFilter filter = new CorsFilter(source);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean(filter);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
