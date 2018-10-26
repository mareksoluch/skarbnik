package org.solo.skarbnik.domain;

import org.springframework.data.domain.Persistable;

public class Users implements Persistable<String> {

    private final String username;
    private final String childName;
    private final String childSurname;
    private final String email;
    private String password;

    public Users(String username, String childName, String childSurname, String email, String password) {
        this.username = username;
        this.childName = childName;
        this.childSurname = childSurname;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getChildName() {
        return childName;
    }

    public String getChildSurname() {
        return childSurname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getId() {
        return username;
    }

    @Override
    public boolean isNew() {
        return username == null;
    }
}
