package com.disquesea.disqueseaapi.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Jwt jwt = new Jwt();

    @Data
    public static class Jwt {
        private String issuer = "disqueSeaApplication";

        private String headerPrefix = "Bearer ";

        private String privateKey;

        private Date expiration = new Date(System.currentTimeMillis() + 600000);
    }

}
