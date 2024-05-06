package com.mycompany.app.entity.share.dto;

import com.mycompany.app.entity.share.Article;
import com.mycompany.app.entity.share.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class ArticleDto extends Article {

    private static final Long serialVersionUID = 1L;

    private List<Comment> commentList;

    public ArticleDto(Article article) {
        BeanUtils.copyProperties(article, this);
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", content='" + getContent() + '\'' +
                ", likeCount=" + getLikeCount() +
                ", collectCount=" + getCollectCount() +
                ", commentCount=" + getCommentCount() +
                ", commentList=" + commentList +
                '}';
    }
}
