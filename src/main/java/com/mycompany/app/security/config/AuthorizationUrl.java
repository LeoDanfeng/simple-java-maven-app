package com.mycompany.app.security.config;

public enum AuthorizationUrl {

    ANONYMOUS(null, new String[] {
            "/permitAll"
    }),

    NORMAL("NORMAL", new String[] {}),

    ADMIN("ADMIN", new String[] {});

    private String role;
    private String[] authorities;

    AuthorizationUrl(String role, String[] authorities) {
        this.role = role;
        this.authorities = authorities;
    }

    public String getRole() {
        return role;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}