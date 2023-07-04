package com.cgm.life.embedded.postgres.resource;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

public class PostgresDatabaseTestResource implements QuarkusTestResourceLifecycleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresDatabaseTestResource.class);
    private EmbeddedPostgres postgres;

    @Override
    public Map<String, String> start() {
        final String userName = System.getProperty("user.name");
        if ("root".equals(userName)) {
            throw new IllegalStateException("Cannot run embedded Postgres when running as user: " + userName);
        }
        try {
            postgres = EmbeddedPostgres.builder().start();
        } catch (IOException e) {
            throw new RuntimeException("Could not start embedded Postgres", e);
        }
        Map<String, String> props = new HashMap<>();
        props.put("quarkus.datasource.jdbc.url", postgres.getJdbcUrl("postgres", "postgres"));
        props.put("quarkus.datasource.username", "postgres");
        props.put("quarkus.datasource.password", "");
        props.put("quarkus.datasource.driver", Driver.class.getName());

        return props;
    }

    @Override
    public void stop() {
        if (postgres != null) {
            try {
                postgres.close();
            } catch (IOException e) {
                LOGGER.warn("Could not stop embedded Postgres", e);
            }
            postgres = null;
        }
    }
}