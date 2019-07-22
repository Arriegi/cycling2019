package eus.jarriaga.cycling.web.controllers.advices;

import eus.jarriaga.cycling.business.exceptions.ProjectPermissionException;
import eus.jarriaga.cycling.web.controllers.WebController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {WebController.class})
public class ProjectPermissionExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ProjectPermissionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String OperationPermissionExceptionAdvice(ProjectPermissionException ex) {
        return ex.getMessage();
    }

}
