package com.mycompany.app.common;

import lombok.Data;
import me.ahoo.cosid.snowflake.MillisecondSnowflakeId;
import me.ahoo.cosid.snowflake.SnowflakeId;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

public class SnowFlakeIdGenerator implements IdentifierGenerator
{

    private static SnowflakeId snowflake;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return snowflake.generate();
    }

    @Data
    @Component
    @ConfigurationProperties(prefix = "snowflake")
    public static class SnowFlakeConfigurationProperties implements InitializingBean {
        private int machineBit = 6;
        private int sequenceBit = 16;
        private int machineId = 1;

        @Override
        public void afterPropertiesSet() throws Exception {
            final long epoch = 1577203200000L;
            snowflake = new MillisecondSnowflakeId(epoch, MillisecondSnowflakeId.DEFAULT_TIMESTAMP_BIT, machineBit, sequenceBit, machineId);
        }
    }
}
