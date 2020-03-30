package com.moxi.statistics.service;

import com.moxi.statistics.dao.CallResultDTODao;
import com.moxi.statistics.dao.CallResultDTODaoForAgent;
import com.moxi.statistics.dao.StatisticsDao;
import com.moxi.statistics.entity.CallResultDTO;
import com.moxi.statistics.entity.CallResultDTOForAgent;
import com.moxi.statistics.entity.StatisticsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author https://www.chuckfang.top
 * @date Created on 2019/10/22 16:44
 */
@Service
public class ExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);

    @Autowired
    private CallResultDTODao callResultDTODao;

    @Autowired
    private CallResultDTODaoForAgent callResultDTODaoForAgent;

    @Autowired
    private StatisticsDao statisticsDao;

    private static final String DAY_START_TIME = "00:00:00";
    private static final String DAY_END_TIME = "23:59:59";

    public List<CallResultDTO> getRobotCallResultList(Integer companyId, Integer roleId, LocalDate localDate) {
        long begin = System.currentTimeMillis();
        LocalDateTime start = LocalDateTime.parse(localDate + "T" + DAY_START_TIME);
        LocalDateTime end = LocalDateTime.parse(localDate + "T" + DAY_END_TIME);
        List<CallResultDTO> callResultList = callResultDTODao.selectRobotList(companyId, roleId, start, end);
        if (callResultList.size() == 0) {
            logger.info("company{} role{} 获取记录为空", companyId, roleId);
            callResultList = new ArrayList<>();
            // 添加一个空记录，不能连这一个sheet都没了
            callResultList.add(new CallResultDTO());
        }
        // 把1变成本人，把0变成三方
        transferString(callResultList);
        logger.info("获取company{} role{} 机器人呼叫记录花费时间：{}ms", companyId, roleId, System.currentTimeMillis() - begin);
        return callResultList;
    }

    public List<CallResultDTO> getRobotCallResultListForXiaoMi(Integer companyId, LocalDate localDate) {
        long begin = System.currentTimeMillis();
        LocalDateTime start = LocalDateTime.parse(localDate + "T" + DAY_START_TIME);
        LocalDateTime end = LocalDateTime.parse(localDate + "T" + DAY_END_TIME);
        List<CallResultDTO> callResultList = callResultDTODao.selectRobotListForXiaoMi(companyId, start, end);
        if (callResultList.size() == 0) {
            logger.info("company{}", companyId);
            callResultList = new ArrayList<>();
            // 添加一个空记录，不能连这一个sheet都没了
            callResultList.add(new CallResultDTO());
        }
        transferString(callResultList);
        logger.info("获取company{} 机器人呼叫记录花费时间：{}ms", companyId, System.currentTimeMillis() - begin);
        return callResultList;
    }

    private void transferString(List<CallResultDTO> callResultList) {
        // 把1变成本人，把0变成三方
        for (CallResultDTO callResultDTO : callResultList) {
            if ("1".equals(callResultDTO.getOutboundType())) {
                callResultDTO.setOutboundType("本人");
            } else if ("0".equals(callResultDTO.getOutboundType())) {
                callResultDTO.setOutboundType("三方");
            }
        }
    }

    public List<CallResultDTOForAgent> getAgentCallResultList(Integer companyId, Integer roleId, LocalDate localDate) {
        long begin = System.currentTimeMillis();
        LocalDateTime start = LocalDateTime.parse(localDate + "T" + DAY_START_TIME);
        LocalDateTime end = LocalDateTime.parse(localDate + "T" + DAY_END_TIME);
        List<CallResultDTOForAgent> callResultList = callResultDTODaoForAgent.selectAgentList(companyId, roleId, start, end);
        if (callResultList.size() == 0) {
            logger.info("company{} role{} 获取记录为空", companyId, roleId);
            callResultList = new ArrayList<>();
            // 添加一个空记录，不能连这一个sheet都没了
            callResultList.add(new CallResultDTOForAgent());
        }
        // 把1变成本人，把0变成三方
        for (CallResultDTOForAgent callResultDTO : callResultList) {
            if ("1".equals(callResultDTO.getOutboundType())) {
                callResultDTO.setOutboundType("本人");
            } else if ("0".equals(callResultDTO.getOutboundType())) {
                callResultDTO.setOutboundType("三方");
            }
        }
        logger.info("获取company{} role{} 人工呼叫记录花费时间：{}ms", companyId, roleId, System.currentTimeMillis() - begin);
        return callResultList;
    }

    public List<CallResultDTOForAgent> getAgentCallResultListForXiaoMi(Integer companyId, LocalDate localDate) {
        long begin = System.currentTimeMillis();
        LocalDateTime start = LocalDateTime.parse(localDate + "T" + DAY_START_TIME);
        LocalDateTime end = LocalDateTime.parse(localDate + "T" + DAY_END_TIME);
        List<CallResultDTOForAgent> callResultList = callResultDTODaoForAgent.selectAgentListForXiaoMi(companyId, start, end);
        if (callResultList.size() == 0) {
            logger.info("company{} 获取记录为空", companyId);
            callResultList = new ArrayList<>();
            // 添加一个空记录，不能连这一个sheet都没了
            callResultList.add(new CallResultDTOForAgent());
        }
        // 把1变成本人，把0变成三方
        for (CallResultDTOForAgent callResultDTO : callResultList) {
            if ("1".equals(callResultDTO.getOutboundType())) {
                callResultDTO.setOutboundType("本人");
            } else if ("0".equals(callResultDTO.getOutboundType())) {
                callResultDTO.setOutboundType("三方");
            }
        }
        logger.info("获取company{} 人工呼叫记录花费时间：{}ms", companyId, System.currentTimeMillis() - begin);
        return callResultList;
    }

    public List<StatisticsEntity> getStatistics(Integer companyId, Integer roleId, LocalDate localDate) {
        long begin = System.currentTimeMillis();
        LocalDateTime start = LocalDateTime.parse(localDate + "T" + DAY_START_TIME);
        LocalDateTime end = LocalDateTime.parse(localDate + "T" + DAY_END_TIME);
        List<StatisticsEntity> statistics = statisticsDao.getAgentStatistics(companyId, roleId, start, end);
        StatisticsEntity robotStatistics = statisticsDao.getRobotStatistics(companyId, roleId, start, end);
        robotStatistics.setUserName("机器人");
        StatisticsEntity allStatistics = statisticsDao.getAllStatistics(companyId, roleId, start, end);
        allStatistics.setUserName("总量");
        statistics.add(robotStatistics);
        statistics.add(allStatistics);
        logger.info("获取company{} role{} 统计记录花费时间：{}ms", companyId, roleId, System.currentTimeMillis() - begin);
        return statistics;
    }

    public List<StatisticsEntity> getStatisticsForXiaoMi(Integer companyId, LocalDate localDate) {
        long begin = System.currentTimeMillis();
        LocalDateTime start = LocalDateTime.parse(localDate + "T" + DAY_START_TIME);
        LocalDateTime end = LocalDateTime.parse(localDate + "T" + DAY_END_TIME);
        List<StatisticsEntity> statistics = statisticsDao.getAgentStatisticsForXiaoMi(companyId, start, end);
        StatisticsEntity robotStatistics = statisticsDao.getRobotStatisticsForXiaoMi(companyId, start, end);
        robotStatistics.setUserName("机器人");
        StatisticsEntity allStatistics = statisticsDao.getAllStatisticsForXiaoMi(companyId, start, end);
        allStatistics.setUserName("总量");
        statistics.add(robotStatistics);
        statistics.add(allStatistics);
        logger.info("获取company{} 统计记录花费时间：{}ms", companyId, System.currentTimeMillis() - begin);
        return statistics;
    }
}
