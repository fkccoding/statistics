package com.moxi.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moxi.statistics.entity.CallResultDTOForAgent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author https://www.chuckfang.top
 * @date Created on 2019/10/22 10:48
 */

@Mapper
@Repository
public interface CallResultDTODaoForAgent extends BaseMapper<CallResultDTOForAgent> {
    /**
     * @param companyId 公司id
     * @param start 开始时间
     * @param end 结束时间
     * @return 结果列表
     */
    List<CallResultDTOForAgent> selectAgentList(@Param("companyId") Integer companyId, @Param("roleId") Integer roleId,
                                                @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<CallResultDTOForAgent> selectAgentListForXiaoMi(Integer companyId, LocalDateTime start, LocalDateTime end);
}
