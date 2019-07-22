package eus.jarriaga.cycling.web.controllers.advices;

import eus.jarriaga.cycling.business.exceptions.ProjectNotDeletedException;
import eus.jarriaga.cycling.web.controllers.WebController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {WebController.class})
public class ProjectNotDeletedExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(ProjectNotDeletedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String ProjectPermissionExceptionAdvice(ProjectNotDeletedException ex) {
        return ex.getMessage();
    }


}
