package com.example.secure.Modules;

public class PasswordModel {
    public String hint, username, password, id;

    public PasswordModel() {}

    public PasswordModel(String id, String username, String password) {
        this.hint = "None";
        this.id = id;
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

    public PasswordModel(String id, String hint, String username, String password) {
        this.hint = hint;
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return this.id;
    }
}
