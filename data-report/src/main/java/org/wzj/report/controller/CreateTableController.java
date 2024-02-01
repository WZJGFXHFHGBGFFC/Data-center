package org.wzj.report.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzj.report.bean.SqlQuery;
import org.wzj.report.service.CreateTableService;

@RestController
@RequestMapping("/report")
public class CreateTableController {
    @PostMapping("createClickHouseTable")
    public String createClickHouseTable(@RequestBody SqlQuery sqlQuery) throws Exception {
        return CreateTableService.createClickHouseTable(sqlQuery.getSql());
    }

    @PostMapping("createMySQLTable")
    public String createMySQLTable(@RequestBody SqlQuery sqlQuery) throws Exception {
        return CreateTableService.createMySQLTable(sqlQuery.getSql());
    }
}
