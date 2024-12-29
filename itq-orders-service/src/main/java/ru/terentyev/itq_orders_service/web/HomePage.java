package ru.terentyev.itq_orders_service.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

public class HomePage extends WebPage {

    public HomePage(){
        Link<Void> createOrderLink = new Link<>("createOrderLink") {
            @Override
            public void onClick() {
                setResponsePage(CreateOrderPage.class);
            }
        };
        Link<Void> searchWithSumLink = new Link<>("searchWithSumLink") {
            @Override
            public void onClick() {
                setResponsePage(SearchWithSumPage.class);
            }
        };
        Link<Void> searchWithArticleLink = new Link<>("searchWithArticleLink") {
            @Override
            public void onClick() {
                setResponsePage(SearchWithArticlePage.class);
            }
        };
        Link<Void> findByIdLink = new Link<>("findByIdLink") {
            @Override
            public void onClick() {
                setResponsePage(FindByIdPage.class);
            }
        };
        add(createOrderLink);
        add(searchWithSumLink);
        add(searchWithArticleLink);
        add(findByIdLink);
    }
}
