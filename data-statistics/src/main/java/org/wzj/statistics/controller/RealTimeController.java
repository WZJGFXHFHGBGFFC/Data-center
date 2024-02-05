package org.wzj.statistics.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wzj.model.common.Result;
import org.wzj.model.common.ResultCodeEnum;
import org.wzj.statistics.bean.GeneralStatistic;
import org.wzj.statistics.service.RealTimeService;
import java.math.BigDecimal;
import java.util.List;

@Tag(name = "实时统计模块")
@RestController
@RequestMapping("/statistic/realtime")
public class RealTimeController {

    @Autowired
    private RealTimeService realTimeService;

    /**
     * 获取一天的总交易额
     */
    @Operation(summary = "获取一天的总交易额")
    @GetMapping("gmv")
    public Result<BigDecimal> getGMV(@RequestParam String date) {
        return Result.build(ResultCodeEnum.SUCCESS, realTimeService.getGMV(date));
    }

    /**
     * 获取不同省份的交易额
     */
    @Operation(summary = "获取不同省份的交易额")
    @GetMapping("province")
    public Result<List<GeneralStatistic>> getProvinceStatistic(@RequestParam String date) {
        return Result.build(ResultCodeEnum.SUCCESS, realTimeService.getProvinceStatistics(date));
    }

    /**
     * 加权关键字排行
     */
    @Operation(summary = "加权关键字排行")
    @GetMapping("keyword")
    public Result<List<GeneralStatistic>> getKeywordStatistic(@RequestParam String date) {
        return Result.build(ResultCodeEnum.SUCCESS, realTimeService.getKeywordStatistics(date));
    }

    /**
     * 统计某天不同类别商品交易额排名
     */
    @Operation(summary = "统计某天不同类别商品交易额排名")
    @GetMapping("category3")
    public Result<List<GeneralStatistic>> getProductStatsGroupByCategory3(@RequestParam String date) {
        return Result.build(ResultCodeEnum.SUCCESS, realTimeService.getProductStatisticsGroupByCategory3(date));
    }

    /**
     * 统计某天不同品牌商品交易额排名
     */
    @Operation(summary = "统计某天不同品牌商品交易额排名")
    @GetMapping("trademark")
    public Result<List<GeneralStatistic>> getProductStatsByTrademark(@RequestParam String date) {
        return Result.build(ResultCodeEnum.SUCCESS, realTimeService.getProductStatsByTrademark(date));
    }
}
