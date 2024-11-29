package io.hoon.redis.exception;

import io.hoon.redis.api.service.product.exception.ProductNotFoundException;
import io.hoon.redis.api.service.stock.exception.StockInsufficientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.CompletionException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CompletionException.class)
    ProblemDetail handleCompletionException(CompletionException e) {
        Throwable cause = e.getCause();
        if (cause instanceof StockInsufficientException) {
            return handleStockInsufficientException((StockInsufficientException) cause);
        }
        return unhandledException(e);
    }

    @ExceptionHandler(StockInsufficientException.class)
    ProblemDetail handleStockInsufficientException(StockInsufficientException e) {
        log.error(e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    
    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handleProductNotFoundException(ProductNotFoundException e) {
        log.error(e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail unhandledException(Exception e) {
        log.error(e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다.");
    }
}