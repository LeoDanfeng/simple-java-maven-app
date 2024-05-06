package com.mycompany.app.entity.share.dto;

import com.mycompany.app.entity.share.Article;
import com.mycompany.app.entity.share.Comment;
import com.mycompany.app.entity.share.Reply;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class ReplyDto extends Reply{

    private static final Long serialVersionUID = 1L;

    private Article article;

    private Comment comment;

    public ReplyDto(Reply reply) {
        BeanUtils.copyProperties(reply,this);
    }
}
