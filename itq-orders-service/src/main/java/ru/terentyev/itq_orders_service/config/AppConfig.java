package ru.terentyev.itq_orders_service.config;


import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.protocol.http.WicketServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ServletRegistrationBean<WicketServlet> wicketServlet() {
        ServletRegistrationBean<WicketServlet> servletRegistrationBean = new ServletRegistrationBean<>(new WicketServlet(), "/*");
        servletRegistrationBean.setName("WicketServlet");
        servletRegistrationBean.addInitParameter("applicationClassName", "ru.terentyev.itq_orders_service.web.ITQWicketApplication");
        servletRegistrationBean.addInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
        return servletRegistrationBean;
    }

//    @Bean
//    public FilterRegistrationBean<WicketFilter> wicketFilter() {
//        FilterRegistrationBean<WicketFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new WicketFilter(new ITQWicketApplication()));
//        registration.addUrlPatterns("/*");
//        registration.setName("WicketFilter");
//        registration.setOrder(1);
//        return registration;
//    }
}