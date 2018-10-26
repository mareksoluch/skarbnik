package org.solo.skarbnik.domain;

public class PasswordReset {
    private String password;
    private String passwordRepeat;
    private String childName;
    private String childSurname;
    private String email;

    public boolean passwordCorrect() {
        return password != null && !"".equals(password) && password.equals(passwordRepeat);
    }

    public boolean passwordIncorrect() {
        return !passwordCorrect();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public void setChildSurname(String childSurname) {
        this.childSurname = childSurname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
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
}
