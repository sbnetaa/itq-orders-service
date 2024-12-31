package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ITQWicketApplication extends WebApplication implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init(){
        super.init();

        mount(new MountedMapper("index.html", HomePage.class));
        mount(new MountedMapper("create.html", CreateOrderPage.class));
        mount(new MountedMapper("searchWithSum.html", SearchWithSumPage.class));
        mount(new MountedMapper("searchWithArticle.html", SearchWithArticlePage.class));
        mount(new MountedMapper("findById.html", FindByIdPage.class));

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");

        getDebugSettings().setDevelopmentUtilitiesEnabled(true);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
