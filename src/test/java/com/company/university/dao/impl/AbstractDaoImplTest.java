package com.company.university.dao.impl;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractDaoImplTest {

    protected DataSource dataSource;

    @BeforeEach
    void setup() {
        dataSource = new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8").ignoreFailedDrops(true).addScripts("tables.sql", "data.sql").build();
    }
}
