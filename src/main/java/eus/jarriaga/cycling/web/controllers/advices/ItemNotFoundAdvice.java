package eus.jarriaga.cycling.web.controllers.advices;

import eus.jarriaga.cycling.business.exceptions.ItemNotFoundException;
import eus.jarriaga.cycling.business.exceptions.RepeatedEmailException;
import eus.jarriaga.cycling.web.controllers.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class ItemNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String itemNotFoundHandler(ItemNotFoundException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(RepeatedEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String repeatedEmailHandler(RepeatedEmailException ex) {
        return ex.getMessage();
    }
}
