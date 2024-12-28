package ru.terentyev.itq_orders_service.web.validators;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.terentyev.itq_orders_service.repositories.ProductRepository;

public class ArticleValidator implements IValidator<Integer> {

    @SpringBean
    private ProductRepository productRepository;

    public ArticleValidator(){
        Injector.get().inject(this);
    }

    @Override
    public void validate(IValidatable<Integer> iValidatable) {
        try {
            productRepository.findByArticle(iValidatable.getValue());
        } catch (EmptyResultDataAccessException e) {
            ValidationError error = new ValidationError(this);
            error.addKey("productNotExists");
            error.setVariable("missingArticle", iValidatable.getValue());
            iValidatable.error(error);
        }
    }
}
