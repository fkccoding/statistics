package com.moxi.statistics.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author https://www.chuckfang.top
 * @date Created on 2019/10/23 20:30
 */
@Data
public class StatisticsEntity {
    @ExcelProperty("姓名")
    @ColumnWidth(20)
    String userName;

    @ExcelProperty("拨打量")
    Integer callNumber;

    @ExcelProperty("接通量")
    Integer connectNumber;

    @ExcelProperty("通话时长")
    Integer callDuration;
}
