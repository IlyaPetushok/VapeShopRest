package project.vapeshop.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handle(NotFoundException notFoundException){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseError error=new ResponseError(HttpStatus.NOT_FOUND,notFoundException.getMessage());
        return new ResponseEntity<>(error.toString(),httpHeaders,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(UnAuthorizationException unAuthorizationException){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseError error=new ResponseError(HttpStatus.FORBIDDEN,unAuthorizationException.getMessage());
        return new ResponseEntity<>(error.toString(),httpHeaders,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(AccessDeniedException accessDeniedException){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseError error=new ResponseError(HttpStatus.LOCKED,accessDeniedException.getMessage());
        return new ResponseEntity<>(error.toString(),httpHeaders,HttpStatus.LOCKED);
    }
}
