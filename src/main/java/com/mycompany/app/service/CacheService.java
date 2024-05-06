package com.mycompany.app.service;

import com.mycompany.app.common.JpaUtil;
import com.mycompany.app.dao.mysql.MysqlArticleRepository;
import com.mycompany.app.dao.mysql.MysqlCachedDataRepository;
import com.mycompany.app.dao.mysql.MysqlCommentRepository;
import com.mycompany.app.dao.mysql.MysqlUserRepository;
import com.mycompany.app.entity.CachedData;
import com.mycompany.app.entity.share.Article;
import com.mycompany.app.entity.share.Comment;
import com.mycompany.app.entity.share.dto.ArticleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CacheService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MysqlCachedDataRepository mysqlCachedDataRepository;

    @Resource
    private MysqlUserRepository mysqlUserRepository;

    @Resource
    private MysqlArticleRepository mysqlArticleRepository;

    @Resource
    private MysqlCommentRepository mysqlCommentRepository;

    @Value("${data.size}")
    private int dataSize;

    @Transactional
    public CachedData getCachedData(Long id) {
        log.info("从数据库获取 id = {}", id);
        CachedData cachedData = null;
        Optional<CachedData> optional = mysqlCachedDataRepository.findById(id);
        if (optional.isPresent()) {
            cachedData = optional.get();
            log.info("ClassType: {}", cachedData.getClass());
        }
        return cachedData;
    }

    @Transactional
    public CachedData updateCachedData(CachedData cachedData) {
        Optional<CachedData> query = mysqlCachedDataRepository.findById(cachedData.getId());
        if (query.isPresent()) {
            log.info("original: {}", query.get());
            JpaUtil.copyNotNullProperties(cachedData, query.get());
        }
        CachedData toSave = query.orElse(cachedData);
        CachedData save = mysqlCachedDataRepository.save(toSave);
        log.info("updated: {}", save);
        return save;
    }

    @Transactional
    public CachedData springCachedData(Long id) {
        return mysqlCachedDataRepository.findById(id).orElse(null);
    }


    @Transactional
    public Article springArticle(Long id) {

        Optional<Article> article = mysqlArticleRepository.findById(id);
        if (article.isPresent()) {
            ArticleDto dto = new ArticleDto(article.get());
            List<Comment> commentList = mysqlCommentRepository.findByArticleId(dto.getId());
            dto.setCommentList(commentList);
            return dto;
        }
        return null;
    }
}
