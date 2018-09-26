package org.solo.login;

import org.springframework.data.domain.Persistable;

public class Users implements Persistable<String> {

    private final String username;
    private final String childName;
    private final String childSurname;

    public Users(String username, String childName, String childSurname) {
        this.username = username;
        this.childName = childName;
        this.childSurname = childSurname;
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

    @Override
    public String getId() {
        return username;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
