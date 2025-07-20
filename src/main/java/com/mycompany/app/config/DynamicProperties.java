package com.mycompany.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class DynamicProperties {

    @Value("${exampleKey:null}")
    private String exampleKey;

    public String getExampleKey() {
        return exampleKey;
    }

    public void setExampleKey(String exampleKey) {
        this.exampleKey = exampleKey;
    }
}
