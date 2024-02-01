package org.wzj.statistics.service;

public class DATABASE {
    public static String MYSQL_URL = "jdbc:mysql://192.168.10.140:3306/gmall_report?useSSL=false";
    public static String MYSQL_USERNAME = "root";
    public static String MYSQL_PASSWORD = "123456";
    public static String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static String CLICKHOUSE_URL = "jdbc:clickhouse://192.168.10.140:8123/default";
    public static String CLICKHOUSE_DRIVER = "com.clickhouse.jdbc.ClickHouseDriver";
}
