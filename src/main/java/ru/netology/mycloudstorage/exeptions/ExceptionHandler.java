package ru.netology.mycloudstorage.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class ExceptionHandler {
   // @ExceptionHandler(MyNotFoundException.class)
    public ResponseEntity<String> cnfException(MyNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

 //   @ExceptionHandler(ServletException.class)
 //   public ResponseEntity<String> naException(ServletException ex) {
 //       return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
 //   }
//
 //   @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<String> isException(InternalServerException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
