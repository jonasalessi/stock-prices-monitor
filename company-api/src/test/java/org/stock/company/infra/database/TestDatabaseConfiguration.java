package org.stock.company.infra.database;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class TestDatabaseConfiguration {

    @Bean
    public ConnectionFactory inMemory() {
       return new H2ConnectionFactory(H2ConnectionConfiguration.builder()
                .inMemory("stock")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                .property(H2ConnectionOption.MODE, "Postgresql")
                .build());
    }
}
