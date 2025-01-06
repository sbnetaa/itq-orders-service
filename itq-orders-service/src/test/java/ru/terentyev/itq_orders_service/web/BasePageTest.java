package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.tester.WicketTestCase;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.terentyev.itq_orders_service.services.OrderService;

import java.util.Locale;

public class BasePageTest extends WicketTestCase {

    protected OrderService orderService;
    protected BasePage basePage;

    @BeforeEach
    public void setUp() {
        orderService = Mockito.mock(OrderService.class);
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.refresh();
        appContext.getBeanFactory().registerSingleton("orderService", orderService);
        tester = new WicketTester(new MockApplication(){
            @Override
            protected void init() {
                getComponentInstantiationListeners().add(new SpringComponentInjector(this, appContext));
                super.init();
            }
        });
        basePage = new BasePage(){};
        tester.getSession().setLocale(Locale.of("ru", "RU"));
    }
}
