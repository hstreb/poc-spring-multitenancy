package org.exemplo.multitenancy.config;

import com.zaxxer.hikari.HikariDataSource;
import org.exemplo.multitenancy.datasource.MultipleDatasourceProperties;
import org.exemplo.multitenancy.datasource.MultitenantDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.GenericApplicationContext;

//@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource")
    public DataSourceProperties appDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.hikari")
    public HikariDataSource appDataSource(DataSourceProperties appDataSource) {
        return appDataSource.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public MultipleDatasourceProperties dataSourceTenant() {
        return new MultipleDatasourceProperties();
    }

    @Bean
    public MultitenantDataSource dataSource(MultipleDatasourceProperties datasourceProperties, GenericApplicationContext context) {
//        datasourceProperties.getDatasources().forEach((key, value) -> context.registerBean(DatabaseConfig.class, value, key));
        var multitenantDataSource = new MultitenantDataSource();
        multitenantDataSource.setTargetDataSources(datasourceProperties.getDatasources());
        return multitenantDataSource;
    }
}
