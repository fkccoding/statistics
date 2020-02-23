package com.moxi.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moxi.statistics.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author https://www.chuckfang.top
 * @date Created on 2020/2/10 15:13
 */
@Mapper
@Repository
public interface CompanyDao extends BaseMapper<Company> {
    List<Company> getCompanyList();
}
