package com.mycompany.app.dao.mysql;

import com.mycompany.app.entity.mysql.ReadWriteSplit;
import org.springframework.data.repository.CrudRepository;

public interface ReadWriteSplitRepository extends CrudRepository<ReadWriteSplit, Long> {
}
