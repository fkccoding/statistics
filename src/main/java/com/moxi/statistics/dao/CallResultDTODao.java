package com.moxi.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moxi.statistics.entity.CallResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author https://www.chuckfang.top
 * @date Created on 2019/10/22 10:48
 */

@Mapper
@Repository
public interface CallResultDTODao extends BaseMapper<CallResultDTO> {

    /**
     * @param companyId 公司id
     * @param start 开始时间
     * @param end 结束时间
     * @return 结果列表
     */
    List<CallResultDTO> selectRobotList(@Param("companyId") Integer companyId, @Param("roleId") Integer roleId,
                                        @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    /**
     * @param companyId 公司id
     * @param start 开始时间
     * @param end 结束时间
     * @return 结果列表
     */
    List<CallResultDTO> selectRobotListForXiaoMi(@Param("companyId") Integer companyId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
