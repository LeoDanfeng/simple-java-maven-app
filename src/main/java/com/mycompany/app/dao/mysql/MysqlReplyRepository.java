package com.mycompany.app.dao.mysql;

import com.mycompany.app.entity.share.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlReplyRepository extends JpaRepository<Reply, Long> {
}
