package learn.field_agent.controllers;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import learn.field_agent.domain.Result;
import learn.field_agent.domain.ResultType;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // DataAccessException is the super class of many Spring database exceptions
    // including BadSqlGrammarException.
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleException(DataAccessException ex) {
        Result<ErrorResponse> result = new Result<>();
        result.addMessage("Data not found", ResultType.INVALID);
        return ErrorResponse.build(result);
    }

    @ExceptionHandler(MismatchedInputException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity<Object> handleException(MismatchedInputException ex) {
        Result<ErrorResponse> result = new Result<>();
        result.addMessage("Incorrect JSON formatting", ResultType.INVALID);
        return ErrorResponse.build(result);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleException(IllegalArgumentException ex) {
        Result<ErrorResponse> result = new Result<>();
        result.addMessage("Illegal Argument", ResultType.INVALID);
        return ErrorResponse.build(result);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException ex) {
        Result<ErrorResponse> result = new Result<>();
        result.addMessage("Incorrect HTTP Request", ResultType.INVALID);
        return ErrorResponse.build(result);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<Object> handleException(HttpClientErrorException.NotFound ex) {
        Result<ErrorResponse> result = new Result<>();
        result.addMessage("URL not found", ResultType.INVALID);
        return ErrorResponse.build(result);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleException(HttpMessageNotReadableException ex) {
        Result<ErrorResponse> result = new Result<>();
        result.addMessage("Input not parsed", ResultType.INVALID);
        return ErrorResponse.build(result);
    }


    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ResponseEntity<Object> handleException(Exception ex) {
        Result<ErrorResponse> result = new Result<>();
        result.addMessage("Sorry. Something went wrong on our end", ResultType.INVALID);
        return ErrorResponse.build(result);
    }
}