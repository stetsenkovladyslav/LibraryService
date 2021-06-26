package com.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, XADataSourceAutoConfiguration.class})
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(
        securedEnabled = true
)
public class LibraryApplication {
/*
    private static final String PROPERTY_DB_HOST  = "DB_HOST";
    private static final String PROPERTY_DB_NAME  = "DB_NAME";
    private static final String PROPERTY_DB_USER  = "DB_USER";
    private static final String PROPERTY_DB_PASSWORD  = "DB_PASSWORD";

    private static void setEnvironmentVariablesDefaults(){
        String activeProfile = System.getProperty("spring.profiles.active");
        if("dev".equals(activeProfile)){
            Properties properties = System.getProperties();
            if(!properties.containsKey(PROPERTY_DB_HOST))
                properties.setProperty(PROPERTY_DB_HOST, "jdbc:postgresql://localhost:5432/library_db");
            if(!properties.containsKey(PROPERTY_DB_NAME))
                properties.setProperty(PROPERTY_DB_NAME, "library_db");
            if(!properties.containsKey(PROPERTY_DB_USER))
                properties.setProperty(PROPERTY_DB_USER, "postgres");
            if(!properties.containsKey(PROPERTY_DB_PASSWORD))
                properties.setProperty(PROPERTY_DB_PASSWORD, "postgres");

        }
    }*/

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}
