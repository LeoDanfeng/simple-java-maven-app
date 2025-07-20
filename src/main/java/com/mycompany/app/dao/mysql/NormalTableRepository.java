package com.mycompany.app.dao.mysql;

import com.mycompany.app.entity.mysql.NormalTable;
import org.springframework.data.repository.CrudRepository;

public interface NormalTableRepository extends CrudRepository<NormalTable, Long> {
}
