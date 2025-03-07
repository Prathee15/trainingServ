package com.acf.trainingserv.config;

import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseUrlConverter {
    public static void getJdbcUrl() {
        String databaseUrl = "postgres://u9dfaged1r4smd:pd08c2552a0d7d8abe47ee93b545bbf234355feb5304a440dd6dde978d3fdac7e@cbec45869p4jbu.cluster-czrs8kj4isg7.us-east-1.rds.amazonaws.com:5432/d43hdoj2c1loup";//System.getenv("DATABASE_URL");
        if (databaseUrl != null) {
            try {
                URI dbUri = new URI(databaseUrl);
                String[] userInfo = dbUri.getUserInfo().split(":");
                String username = userInfo[0];
                String password = userInfo[1];
                String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

                System.setProperty("spring.datasource.url", jdbcUrl);
                System.setProperty("spring.datasource.username", username);
                System.setProperty("spring.datasource.password", password);
            } catch (URISyntaxException e) {
                throw new RuntimeException("Invalid DATABASE_URL format", e);
            }
        }
    }
}
