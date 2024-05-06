package com.mycompany.app.entity.share;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.app.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_comment")
public class Comment extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    private Long articleId;

    private String content;

    private String commenter;

    private Integer likeCount;
}
