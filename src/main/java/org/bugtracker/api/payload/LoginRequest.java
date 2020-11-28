package org.bugtracker.api.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String handle;

    @NotBlank
    private String password;

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}