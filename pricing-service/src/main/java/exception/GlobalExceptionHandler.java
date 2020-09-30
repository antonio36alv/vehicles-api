package exception;

import org.hibernate.QueryException;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackageClasses = RepositoryRestExceptionHandler.class)
public class GlobalExceptionHandler {

    @ExceptionHandler({QueryException.class})
    public ResponseEntity<Map<String, String>> noSuchIdException(QueryException e) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("message", "Bad Request, issue with Id in query.");
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }

}
