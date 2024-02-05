package org.wzj.statistics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wzj.model.common.Result;
import org.wzj.model.common.ResultCodeEnum;
import org.wzj.statistics.bean.Page;
import org.wzj.statistics.bean.TrafficStatistic;
import org.wzj.statistics.service.TrafficService;
import java.util.Map;

@Tag(name = "访问流量统计")
@RestController
@RequestMapping("/statistic/visit")
public class TrafficController {

    @Autowired
    private TrafficService trafficService;

    /**
     * 昨天/7日/30日的浏览记录分析
     */
    @Operation(summary = "昨天/7日/30日的浏览记录分析")
    @GetMapping("getTrafficStats/{page}/{limit}")
    public Result<Page<TrafficStatistic>> getTrafficStats(@PathVariable Integer page, @PathVariable Integer limit, TrafficStatistic trafficStatistic) {

        Page<TrafficStatistic> trafficStatistics = trafficService.getTrafficStatistics(page, limit, trafficStatistic.getDt(), trafficStatistic.getRecentDays());

        return Result.build(ResultCodeEnum.SUCCESS, trafficStatistics);
    }


    /**
     * 用户路径分析
     */
    @Operation(summary = "用户路径分析")
    @GetMapping("getPagePath")
    public Result<Map<String, Object>> getPagePath(TrafficStatistic trafficStatistic) {
        return Result.build(ResultCodeEnum.SUCCESS, trafficService.getPagePathCount(trafficStatistic));
    }
}
