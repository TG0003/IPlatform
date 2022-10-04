package com.myapp.commcode2api.vo.user;

import java.math.BigDecimal;

public class AdminAddOrUpdateUserVO {
    private String username;
    private String password;
    private String secret;
    private BigDecimal balance;
    private String valid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "AddOrUpdateUserVO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", secret='" + secret + '\'' +
                ", balance=" + balance +
                ", valid='" + valid + '\'' +
                '}';
    }
}
