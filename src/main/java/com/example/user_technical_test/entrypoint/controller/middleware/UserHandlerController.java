package com.example.user_technical_test.entrypoint.controller.middleware;

import com.example.user_technical_test.aplication.exceptions.UserAlreadyRegisteredException;
import com.example.user_technical_test.aplication.exceptions.UserNotFoundException;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorConsultByEmailException;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorConsultByIdException;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorSavingUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserHandlerController {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<MessageErrorHandler> userAlreadyRegisteredHandler(UserAlreadyRegisteredException exception) {
        MessageErrorHandler messageErrorHandler = MessageErrorHandler.builder().status(HttpStatus.BAD_REQUEST).message(exception.getMessage()).build();
        return ResponseEntity.status(messageErrorHandler.status()).body(messageErrorHandler);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageErrorHandler> userNotFoundExceptionHandler(UserNotFoundException exception) {
        MessageErrorHandler messageErrorHandler = MessageErrorHandler.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();
        return ResponseEntity.status(messageErrorHandler.status()).body(messageErrorHandler);
    }

    @ExceptionHandler(ErrorConsultByEmailException.class)
    public ResponseEntity<MessageErrorHandler> errorConsultByEmailHandler(ErrorConsultByEmailException exception) {
        MessageErrorHandler messageErrorHandler = MessageErrorHandler.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(exception.getMessage()).build();
        return ResponseEntity.status(messageErrorHandler.status()).body(messageErrorHandler);
    }

    @ExceptionHandler(ErrorConsultByIdException.class)
    public ResponseEntity<MessageErrorHandler> errorConsultByIdHandler(ErrorConsultByIdException exception) {
        MessageErrorHandler messageErrorHandler = MessageErrorHandler.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(exception.getMessage()).build();
        return ResponseEntity.status(messageErrorHandler.status()).body(messageErrorHandler);
    }

    @ExceptionHandler(ErrorSavingUserException.class)
    public ResponseEntity<MessageErrorHandler> errorSavingUserException(ErrorSavingUserException exception) {
        MessageErrorHandler messageErrorHandler = MessageErrorHandler.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(exception.getMessage()).build();
        return ResponseEntity.status(messageErrorHandler.status()).body(messageErrorHandler);
    }
}
