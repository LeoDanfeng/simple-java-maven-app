package com.mycompany.app.security.config;

public enum AuthorizationUrl {

    ANONYMOUS(null, new String[]{
            "/permitAll","/login","/logout","/cache/**"
    }),

    NORMAL("NORMAL", new String[]{
            "/withRole/normal"
    }),

    ADMIN("ADMIN", new String[]{
            "/withRole/admin",
            "/admin/**"
    });

    private final String role;
    private final String[] authorities;

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