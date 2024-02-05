package org.wzj.statistics.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MySqlDataSourceConfig.class, ClickHouseDataSourceConfig.class})
public class DataSourceConfig {

}
