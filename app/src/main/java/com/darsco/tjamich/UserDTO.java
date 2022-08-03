package com.darsco.tjamich;

public class UserDTO {

    private int userId;

    private String userName;

    private String password;

    private String email;

    private String Data;

    private String Token;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) { this.userName = userName; }

    public String getData() {
        return Data;
    }

    public void setData(String Data) { this.Data = Data;}

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) { this.Token = Token; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}