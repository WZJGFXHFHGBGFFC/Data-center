package org.wzj.statistics.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzj.model.util.Result;
import org.wzj.statistics.bean.Page;
import org.wzj.statistics.bean.TrafficStatistic;
import org.wzj.statistics.service.StatisticService;

import java.util.Map;

@RestController
@RequestMapping("/statistic/visit")
public class TrafficController {
    @GetMapping("getTrafficStats/{page}/{limit}")
    public Result<Page<TrafficStatistic>> getTrafficStats(@PathVariable Integer page, @PathVariable Integer limit, TrafficStatistic trafficStatistic) {
        var trafficStatistics = StatisticService.getTrafficStatistics(page, limit, trafficStatistic.getDt(), trafficStatistic.getRecentDays());
        return Result.of(200, "success", trafficStatistics);
    }

    /**
     * 用户路径分析
     */
    @GetMapping("getPagePath")
    public Result<Map<String, Object>> getPagePath(TrafficStatistic trafficStatistic) {
        return Result.of(200, "success", StatisticService.getPagePathCount(trafficStatistic));
    }
}
