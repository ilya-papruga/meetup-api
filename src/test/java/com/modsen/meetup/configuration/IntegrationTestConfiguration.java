package com.modsen.meetup.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@TestConfiguration
public class IntegrationTestConfiguration {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initDB() throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(con, new ClassPathResource("/create-meetup-before.sql"));
        }
    }
}
