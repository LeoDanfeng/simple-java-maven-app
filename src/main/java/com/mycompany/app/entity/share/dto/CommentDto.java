package com.mycompany.app.entity.share.dto;

import com.mycompany.app.entity.share.Article;
import com.mycompany.app.entity.share.Comment;
import com.mycompany.app.entity.share.Reply;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class CommentDto extends Comment {

    private static final Long serialVersionUID = 1L;

    private Article article;

    private List<Reply> replyList;

    public CommentDto(Comment comment) {
        BeanUtils.copyProperties(comment, this);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                ", content=" + getContent() +
                ", commenter=" + getCommenter() +
                ", likeCount=" + getLikeCount() +
                ", replyList=" + replyList +
                '}';
    }
}
