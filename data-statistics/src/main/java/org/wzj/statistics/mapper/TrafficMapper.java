package org.wzj.statistics.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.wzj.statistics.bean.PagePath;
import org.wzj.statistics.bean.TrafficStatistic;
import java.util.List;

@Mapper
public interface TrafficMapper {

    //昨天/7日/30日的浏览记录分析
    List<TrafficStatistic> getTrafficStatistics(Integer page, Integer limit, String dt, Integer recentDays);

    //用户路径分析
    List<PagePath> getPagePathCount(TrafficStatistic trafficStatistic);
}
