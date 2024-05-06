package com.mycompany.app.dao.mysql;

import com.mycompany.app.entity.share.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlArticleRepository extends JpaRepository<Article,Long> {
}
