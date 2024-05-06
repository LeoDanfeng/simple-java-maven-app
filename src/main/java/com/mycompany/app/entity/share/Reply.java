package com.mycompany.app.entity.share;

import com.mycompany.app.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_reply")
public class Reply extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    private Long articleId;
    private Long commentId;
    private String replyTo;
    private String replyFrom;
    private String content;
    private Integer likeCount;
}
