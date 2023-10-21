package org.exemplo.multitenancy.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("multi-datasource")
public class MultipleDatasourceProperties {

    private Map<Object, Object> datasources = new HashMap<>();

    public Map<Object, Object> getDatasources() {
        return datasources;
    }

    public void setDatasources(Map<String, Map<String, String>> datasources) {
        datasources.forEach((key, value) -> this.datasources.put(key, convert(value)));
    }

    private DataSource convert(Map<String, String> source) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(source.get("url"))
                .driverClassName(source.get("driverClassName"))
                .username(source.get("username"))
                .password(source.get("password"))
                .build();
    }
}
