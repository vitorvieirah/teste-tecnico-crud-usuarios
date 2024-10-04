package com.example.user_technical_test.entrypoint.controller.middleware;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record MessageErrorHandler(HttpStatus status, String message) {}
