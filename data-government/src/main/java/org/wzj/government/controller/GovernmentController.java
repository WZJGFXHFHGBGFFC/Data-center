package org.wzj.government.controller;


import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzj.government.bean.GovernmentDetail;
import org.wzj.government.service.TableLineageService;
import org.wzj.model.common.Result;
import org.wzj.model.common.ResultCodeEnum;
import org.wzj.scheduler.bean.MonitorDetail;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/government")
public class GovernmentController {
    /**
     * 计算血缘关系
     */
    @GetMapping("lineage")
    public String getLineage() {
        // 更新血缘图
        var edges = TableLineageService.generateEdges();
        TableLineageService.sinkToNeo4j(edges);

        // 从neo4j中查询所有边以及顶点，并绘成`Mermaid`字符串
        var driver = GraphDatabase.driver("bolt://192.168.10.140:7687", AuthTokens.basic("neo4j", "123456"));
        var session = driver.session();
        var graphString = session.executeRead(tx -> {
            var graph = new StringBuilder("graph LR\n");
            var result = tx.run("MATCH (n)-[r]->(m) RETURN n,r,m");
            for (var record : result.list()) {
                graph
                        .append("    ")
                        .append(record.get("n").get("tableName"))
                        .append(" --> ")
                        .append(record.get("m").get("tableName"))
                        .append("\n");
            }
            return graph.toString().replaceAll("\"", "");
        });
        session.close();
        driver.close();
        return graphString;
    }

    /**
     * 获取hive元数据质量监控数据
     */
    @GetMapping("hiveMetadataScore")
    public Result<List<GovernmentDetail>> getHiveMetadataScore() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.10.140:3306/data_center?useSSL=false",
                    "root",
                    "123456"
            );

            var selectStatement = connection.prepareStatement(
                    "SELECT * FROM hive_metadata_monitor"
            );

            var resultSet = selectStatement.executeQuery();
            var result = new ArrayList<GovernmentDetail>();
            while (resultSet.next()) {
                var detail = new GovernmentDetail();
                detail.setDatabaseName(resultSet.getString("database_name"));
                detail.setTableName(resultSet.getString("table_name"));
                detail.setFieldsNumber(resultSet.getInt("fields_number"));
                detail.setHasBusinessOwner(resultSet.getBoolean("has_business_owner"));
                detail.setHasOutputLastSevenDay(resultSet.getBoolean("has_output_last_seven_days"));
                detail.setMissingCommentFieldsNumber(resultSet.getInt("missing_comment_fields_number"));
                detail.setHasTechnicalOwner(resultSet.getBoolean("has_technical_owner"));
                detail.setHasTableComment(resultSet.getBoolean("has_table_comment"));
                result.add(detail);
            }

            connection.close();
            return Result.build(ResultCodeEnum.SUCCESS, result);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return Result.build(ResultCodeEnum.SUCCESS, new ArrayList<>());
    }

    /**
     * 获取MySQL数据质量
     */
    @GetMapping("mysqlDataMonitor")
    public Result<List<MonitorDetail>> getMySQLDataMonitor() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.10.140:3306/data_center?useSSL=false",
                    "root",
                    "123456"
            );

            var selectStatement = connection.prepareStatement(
                    "SELECT * FROM mysql_data_monitor"
            );

            var resultSet = selectStatement.executeQuery();
            var result = new ArrayList<MonitorDetail>();
            while (resultSet.next()) {
                var detail = new MonitorDetail();
                detail.setDatabaseName(resultSet.getString("database_name"));
                detail.setTableName(resultSet.getString("table_name"));
                detail.setFieldName(resultSet.getString("field_name"));
                detail.setFieldNullRate(resultSet.getDouble("field_null_rate"));
                result.add(detail);
            }

            connection.close();
            return Result.build(ResultCodeEnum.SUCCESS, result);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return Result.build(ResultCodeEnum.SUCCESS, new ArrayList<>());
    }
}
