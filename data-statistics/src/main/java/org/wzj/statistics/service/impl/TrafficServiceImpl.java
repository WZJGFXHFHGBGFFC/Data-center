package org.wzj.statistics.service.impl;

import jakarta.annotation.PostConstruct;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wzj.statistics.bean.Page;
import org.wzj.statistics.bean.PagePath;
import org.wzj.statistics.bean.TrafficStatistic;
import org.wzj.statistics.mapper.TrafficMapper;
import org.wzj.statistics.service.TrafficService;

import java.util.*;

@Service
public class TrafficServiceImpl implements TrafficService {

    @Autowired
    @Qualifier("mysqlSqlSessionTemplate")
    private SqlSessionTemplate mysqlSqlSessionTemplate;

    private TrafficMapper trafficMapper;

    @PostConstruct
    public void init() {
        trafficMapper = mysqlSqlSessionTemplate.getMapper(TrafficMapper.class);
    }

    /**
     * 昨天/7日/30日的浏览记录分析
     */
    @Override
    public Page<TrafficStatistic> getTrafficStatistics(Integer page, Integer limit, String dt, Integer recentDays) {

        //查询
        List<TrafficStatistic> trafficStatistics = trafficMapper.getTrafficStatistics(page-1, limit, dt, recentDays);


        //封装
        Page<TrafficStatistic> trafficStatisticPage = new Page<>(trafficStatistics.size(), limit, page, trafficStatistics);
        return trafficStatisticPage;
    }

    /**
     * 用户路径分析
     */
    @Override
    public Map<String, Object> getPagePathCount(TrafficStatistic trafficStatistic) {

        // 从数据库通过TrafficMapper接口查询页面路径的统计数据
        List<PagePath> pagePaths = trafficMapper.getPagePathCount(trafficStatistic);

        // 使用LinkedHashSet来存储节点名称，保证元素唯一且保持插入顺序
        var nodeSet = new LinkedHashSet<String>();
        // 准备一个列表来存放节点数据，每个节点是一个包含名称的Map
        List<Map<String, String>> nodeMapList = new ArrayList<>();

        // 遍历查询到的页面路径统计数据
        for (var pagePath : pagePaths) {
            // 将页面路径的来源和目标添加到节点集合中
            nodeSet.add(pagePath.getSource());
            nodeSet.add(pagePath.getTarget());
        }

        // 遍历节点集合
        for (var nodeName : nodeSet) {
            // 为每个节点创建一个Map，并将其名称作为"name"键的值，然后添加到节点数据列表中
            nodeMapList.add(Map.of("name", nodeName));
        }

        // 准备一个HashMap来作为最终返回的结果集
        HashMap<String, Object> rsMap = new HashMap<>();
        // 将节点数据列表存放在结果集中，键名为"nodeData"
        rsMap.put("nodeData", nodeMapList);
        // 将页面路径统计数据直接存放在结果集中，键名为"linksData"
        rsMap.put("linksData", pagePaths);

        // 返回结果集
        return rsMap;

    }
}
