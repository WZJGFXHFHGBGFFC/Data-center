package org.wzj.statistics.service.impl;

import jakarta.annotation.PostConstruct;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wzj.statistics.bean.GeneralStatistic;
import org.wzj.statistics.mapper.RealTimeMapper;
import org.wzj.statistics.mapper.TrafficMapper;
import org.wzj.statistics.service.RealTimeService;
import java.math.BigDecimal;
import java.util.*;

@Service
public class RealTimeServiceImpl implements RealTimeService {

    @Autowired
    @Qualifier("clickhouseSqlSessionTemplate")
    private SqlSessionTemplate clickhouseSqlSessionTemplate;

    private RealTimeMapper realTimeMapper;

    //@PostConstruct 注解标记的方法会在对象实例化和依赖注入完成之后立即执行。
    @PostConstruct
    public void init() {
        realTimeMapper = clickhouseSqlSessionTemplate.getMapper(RealTimeMapper.class);
    }



    /**
     * 获取一天的总交易额
     */
    @Override
    public BigDecimal getGMV(String date) {
        return realTimeMapper.getGMV(date);
    }

    /**
     * 获取不同省份的交易额
     */
    @Override
    public  List<GeneralStatistic> getProvinceStatistics(String date) {
        return realTimeMapper.getProvinceStatistics(date);
    }

    /**
     * 加权关键字排行
     */
    @Override
    public  List<GeneralStatistic> getKeywordStatistics(String date) {
        return realTimeMapper.getKeywordStatistics(date);
    }

    /**
     * 统计某天不同类别商品交易额排名
     */
    @Override
    public List<GeneralStatistic> getProductStatisticsGroupByCategory3(String date) {
        return realTimeMapper.getProductStatisticsGroupByCategory3(date);
    }

    /**
     * 统计某天不同品牌商品交易额排名
     */
    @Override
    public List<GeneralStatistic> getProductStatsByTrademark(String date) {
        return realTimeMapper.getProductStatsByTrademark(date);
    }
}
