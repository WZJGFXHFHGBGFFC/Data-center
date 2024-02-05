package org.wzj.statistics.service;

import org.wzj.statistics.bean.Page;
import org.wzj.statistics.bean.TrafficStatistic;
import java.util.Map;

public interface TrafficService {

    /**
     * 昨天/7日/30日的浏览记录分析
     */
    Page<TrafficStatistic> getTrafficStatistics(Integer page, Integer limit, String dt, Integer recentDays);

    /**
     * 用户路径分析
     */
    Map<String, Object> getPagePathCount(TrafficStatistic trafficStatistic);
}
