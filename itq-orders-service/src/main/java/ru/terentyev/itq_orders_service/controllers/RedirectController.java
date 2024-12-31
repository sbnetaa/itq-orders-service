package ru.terentyev.itq_orders_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class RedirectController extends AbstractController{

    @GetMapping
    public RedirectView redirectToIndex() {
        return new RedirectView("/ui/index.html");
    }
}
