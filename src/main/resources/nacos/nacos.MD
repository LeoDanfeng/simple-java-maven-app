1. 添加pom依赖
    ```xml
        <dependiencies>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            </dependency>
        
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            </dependency>
        </dependencies>
    ```
2. 配置相关参数 [application配置示例](application-nacos.yml) [bootstrap配置示例](bootstrap.yml)