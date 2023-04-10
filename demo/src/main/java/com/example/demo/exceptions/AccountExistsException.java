package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Account already exists")
public class AccountExistsException extends RuntimeException {
}
