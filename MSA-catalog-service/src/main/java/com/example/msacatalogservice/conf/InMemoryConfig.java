package com.example.msacatalogservice.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class InMemoryConfig {
    private final String SAMPLE_DATA = "classpath:sample_data_h2.sql";

    private final DataSource datasource;

    private final WebApplicationContext webApplicationContext;

    public InMemoryConfig(DataSource datasource, WebApplicationContext webApplicationContext) {
        this.datasource = datasource;
        this.webApplicationContext = webApplicationContext;
    }

    @PostConstruct
    public void loadIfInMemory() throws Exception {
        Resource resource = webApplicationContext.getResource(SAMPLE_DATA);
        ScriptUtils.executeSqlScript(datasource.getConnection(), resource);
    }
}
