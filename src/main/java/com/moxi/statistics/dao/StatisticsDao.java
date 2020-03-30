package com.moxi.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moxi.statistics.entity.StatisticsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author https://www.chuckfang.top
 * @date Created on 2019/10/23 20:33
 */
@Mapper
@Repository
public interface StatisticsDao extends BaseMapper<StatisticsEntity> {

    List<StatisticsEntity> getAgentStatistics(@Param("companyId") Integer companyId, @Param("roleId") Integer roleId,
                                              @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    StatisticsEntity getRobotStatistics(@Param("companyId") Integer companyId, @Param("roleId") Integer roleId,
                                        @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    StatisticsEntity getAllStatistics(@Param("companyId") Integer companyId, @Param("roleId") Integer roleId,
                                      @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<StatisticsEntity> getAgentStatisticsForXiaoMi(Integer companyId, LocalDateTime start, LocalDateTime end);

    StatisticsEntity getRobotStatisticsForXiaoMi(Integer companyId, LocalDateTime start, LocalDateTime end);

    StatisticsEntity getAllStatisticsForXiaoMi(Integer companyId, LocalDateTime start, LocalDateTime end);
}
