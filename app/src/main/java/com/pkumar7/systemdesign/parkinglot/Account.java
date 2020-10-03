package com.pkumar7.systemdesign.parkinglot;

public abstract class Account {
    private String username;
    private String password;
    private AccountStatus status;
    private Person person;

    public abstract boolean resetPassword();
}
