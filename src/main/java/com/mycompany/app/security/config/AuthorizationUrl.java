package com.mycompany.app.security.config;

/**
 * @Author: 罗丹枫
 * @Description: 请求路径权限设置
 * @CreatedAt: 2024/7/11 22:19
 */

public enum AuthorizationUrl {

    ANONYMOUS(null, new String[]{
            "/hi/**",
            "/permitAll",
            "/login",
            "/logout",
            "/cache/**",
            "/trim/**",
            "/conf/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/**"
    }),

    NORMAL("normal", new String[]{
            "/withRole/normal"
    }),

    ADMIN("admin", new String[]{
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