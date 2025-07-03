package com.mycompany.app.common;

import me.ahoo.cosid.snowflake.MillisecondSnowflakeId;
import me.ahoo.cosid.snowflake.SnowflakeId;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowFlakeIdGenerator implements IdentifierGenerator {
    private static final SnowflakeId snowflake = new MillisecondSnowflakeId(1);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return snowflake.generate();
    }
}
