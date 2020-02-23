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

   /* public void getRobot(Integer companyId, LocalDateTime start, LocalDateTime end) {
        long begin = System.currentTimeMillis();

        List<CallResultDTO> callResultList50 = getRobotCallResultList(companyId, 50, start, end);
        List<CallResultDTO> callResultList60 = getRobotCallResultList(companyId, 60, start, end);
        List<CallResultDTO> callResultList70 = getRobotCallResultList(companyId, 70, start, end);
        List<CallResultDTO> callResultList80 = getRobotCallResultList(companyId, 80, start, end);

        File file = new File(LocalDateTime.now() + ".xlsx");
//        ExcelExportUtil.exportToFile(file.getPath(), callResultList50, callResultList60, callResultList70, callResultList80);

        ExcelWriter excelWriter = EasyExcel.write(file, CallResultDTO.class).build();
        excelWriter.write(callResultList50, a)
                .write(callResultList60, b)
                .write(callResultList70, c)
                .write(callResultList80, d)
                .finish();
        long after = System.currentTimeMillis();
        logger.info("已经生成Excel文件，花费时间：{}ms", after - begin);

        *//*Optional<FileServer.UploadResult> upload = FileServer.upload(file.getAbsolutePath());
        upload.map(FileServer.UploadResult::getUrl).ifPresent(url -> {
            // 更新内存中的Excel地址
            Constants.excelPath = url;
            try {
                //删除文件和文件夹
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*//*
    }
*/
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
        for (CallResultDTO callResultDTO : callResultList) {
            if ("1".equals(callResultDTO.getOutboundType())) {
                callResultDTO.setOutboundType("本人");
            } else if ("0".equals(callResultDTO.getOutboundType())) {
                callResultDTO.setOutboundType("三方");
            }
        }
        logger.info("获取company{} role{} 机器人呼叫记录花费时间：{}ms", companyId, roleId, System.currentTimeMillis() - begin);
        return callResultList;
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
}
