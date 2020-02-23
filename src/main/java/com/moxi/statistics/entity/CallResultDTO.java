package com.moxi.statistics.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author https://www.chuckfang.top
 * @date Created on 2019/10/21 22:14
 */
@TableName("outbound_call_result")
@Data
public class CallResultDTO implements Serializable {
    @TableId
    @ExcelProperty("案件ID")
    private Integer callCaseId;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("欠款金额")
    @ColumnWidth(10)
    private String casePrice;

    @ExcelProperty("拨打时间")
    @ColumnWidth(20)
    private Date callStartTime;

    @ExcelProperty("应答时间")
    @ColumnWidth(20)
    private Date answerTime;

    @ExcelProperty("结束时间")
    @ColumnWidth(20)
    private Date callEndTime;

    @ExcelProperty("手机号")
    @ColumnWidth(15)
    private String callNumber;

    @ExcelProperty("录音地址")
    @ColumnWidth(40)
    private String voicePath;

    @ExcelProperty("本人/三方")
    private String outboundType;
}
