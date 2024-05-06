package com.mycompany.app;

import com.mycompany.app.entity.CachedData;
import com.mycompany.app.entity.share.Article;
import com.mycompany.app.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@SpringBootApplication
@RestController
@EnableCaching
@EnableJpaAuditing
public class StarterApplication {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private CacheService cacheService;

    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }


    @GetMapping("permitAll")
    public String permitAll() {
        App.main(new String[]{});
        return "PermitAll";
    }

    @GetMapping("authenticated")
    public String authenticated() {
        return "authenticated";
    }

    @GetMapping("withRole/normal")
    public String withRoleNormal() {
        return "withRole: normal";
    }

    @GetMapping("withRole/admin")
    public String withRoleAdmin() {
        return "withRole: admin";
    }

    @GetMapping("/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "该用户未登录";
        }
        Boolean deleted = stringRedisTemplate.delete(authentication.getName());
        log.info("用户（{}）已退出登录; deleted = {}", authentication.getName(), deleted);
        return "你已成功退出登录";
    }

    @GetMapping("/admin/forceQuit/{accountName}")
    public String forceQuit(@PathVariable String accountName) {
        Boolean deleted = stringRedisTemplate.delete(accountName);
        log.info("已强制用户（{}）退出登录; deleted = {}", accountName, deleted);
        return "已经删除" + accountName + "登录凭证";
    }

    @PostMapping("/hello")
    public String hello() {
        return "hello";
    }

    //    @Cacheable(value = "cached_data", key = "#id")
    @GetMapping("/cache/{id}")
    public CachedData getCachedData(@PathVariable Long id) {
        return cacheService.getCachedData(id);
    }

    @PostMapping("/cache/update")
    public CachedData updateCachedData(@RequestBody CachedData cachedData) {
        return cacheService.updateCachedData(cachedData);
    }

    @Cacheable(value = "cached_data", key = "#id")
    @GetMapping("/cache/springCache/{id}")
    public CachedData springCache(@PathVariable Long id) {
        return cacheService.springCachedData(id);
    }

    @Cacheable(value = "article",key = "#id")
    @GetMapping("/cache/article/{id}")
    public Article springArticle(@PathVariable Long id) {
        Article article = cacheService.springArticle(id);
        log.info("Article Class : {}", article.getClass().getSimpleName());
        log.info("Article: {}", article);
        return article;
    }


}
