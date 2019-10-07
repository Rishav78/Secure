package com.example.secure.Modules;

public class PasswordModel {
    public String hint, username, password;

    public PasswordModel() {}

    public PasswordModel(String username, String password) {
        this.hint = "None";
        this.username = username;
        this.password = password;
    }

    public String getHint() {
        return this.hint;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public PasswordModel(String hint, String username, String password) {
        this.hint = hint;
        this.username = username;
        this.password = password;
    }
}
