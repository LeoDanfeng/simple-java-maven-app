# simple-java-maven-app

## 部署环境准备

1. **环境变量配置** <br>

- shardingSphere
    - MASTER_MYSQL
    - SLAVE_MYSQL
    - SHARDING_MYSQL_DB
    - SHARDING_MYSQL_USER
    - SHARDING_MYSQL_PASSWORD
- redis
    - REDIS_HOST
    - REDIS_PORT
    - REDIS_DB
- mysql
    - MYSQL_HOST
    - MYSQL_PORT
    - MYSQL_USER
    - MYSQL_PASSWORD
    - MYSQL_DB

# MYSQL REPLICATION
1. master-mysql
```mysql
SHOW MASTER STATUS;
CREATE USER 'replica_user'@'%' IDENTIFIED BY 'replica_password';
GRANT REPLICATION_SLAVE ON *.* TO 'admin'@'%';
CREATE USER 'admin'@'%' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON `simple-java-maven-app`.* TO 'admin'@'%';
FLUSH PRIVILEGES;
```
```mysql
SHOW SLAVE STATUS;
CHANGE MASTER TO
    MASTER_HOST ='mysql-master',
    MASTER_USER ='replica_user',
    MASTER_PASSWORD ='replica_password',
    MASTER_LOG_FILE ='mysql-bin.000003',
    MASTER_LOG_POS =157,
    MASTER_SSL =0,
    GET_MASTER_PUBLIC_KEY =1;
START SLAVE;
STOP SLAVE;
```