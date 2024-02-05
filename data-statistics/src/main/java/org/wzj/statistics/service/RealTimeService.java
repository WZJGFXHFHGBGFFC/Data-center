package org.wzj.statistics.service;

import org.wzj.statistics.bean.GeneralStatistic;
import java.math.BigDecimal;
import java.util.List;

public interface RealTimeService {
    /**
     * 获取一天的总交易额
     */
    BigDecimal getGMV(String date);

    /**
     * 获取不同省份的交易额
     */
    List<GeneralStatistic> getProvinceStatistics(String date);

    /**
     * 加权关键字排行
     */
    List<GeneralStatistic> getKeywordStatistics(String date);

    /**
     * 统计某天不同类别商品交易额排名
     */
    List<GeneralStatistic> getProductStatisticsGroupByCategory3(String date);

    /**
     * 统计某天不同品牌商品交易额排名
     */
    List<GeneralStatistic> getProductStatsByTrademark(String date);
}
