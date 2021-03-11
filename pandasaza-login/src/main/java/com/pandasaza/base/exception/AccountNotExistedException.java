package com.pandasaza.base.exception;

public class AccountNotExistedException extends RuntimeException{

    public AccountNotExistedException(String email) {
        super("Email is not registered: " + email);
    }
}
