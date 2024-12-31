package ru.terentyev.itq_orders_service.config;


import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.protocol.http.WicketServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean<WicketServlet> wicketServlet() {
        ServletRegistrationBean<WicketServlet> servletRegistrationBean = new ServletRegistrationBean<>(new WicketServlet(), "/ui/*"); // /ui/*
        servletRegistrationBean.setName("WicketServlet");
        servletRegistrationBean.addInitParameter("applicationClassName", "ru.terentyev.itq_orders_service.web.ITQWicketApplication");
        servletRegistrationBean.addInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/ui/*"); // *.html
        servletRegistrationBean.setOrder(1);
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> springDispatcherServlet() {
        ServletRegistrationBean<DispatcherServlet> registration = new ServletRegistrationBean<>(dispatcherServlet(), "/api*"); // /api*
        registration.setName("springDispatcherServlet");
        registration.setOrder(2);
        registration.setLoadOnStartup(2);
        return registration;
    }
}