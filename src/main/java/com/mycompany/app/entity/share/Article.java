package com.mycompany.app.entity.share;

import com.mycompany.app.common.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_article")
public class Article extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    private String title;
    private String author;
    private String content;
    private Integer likeCount;
    private Integer collectCount;
    private Integer commentCount;

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", likeCount=" + likeCount +
                ", collectCount=" + collectCount +
                ", commentCount=" + commentCount +
                '}';
    }
}
