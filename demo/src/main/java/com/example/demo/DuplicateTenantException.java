package com.example.demo;

public class DuplicateTenantException extends RuntimeException {
    public DuplicateTenantException(String s) {
        super(s);
    }
}
