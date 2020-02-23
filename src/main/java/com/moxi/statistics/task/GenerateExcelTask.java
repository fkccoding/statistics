package com.moxi.statistics.task;

import com.moxi.statistics.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author https://www.chuckfang.top
 * @date Created on 2019/10/22 10:54
 */
@Component
public class GenerateExcelTask {
    @Autowired
    ExcelService excelService;

//    @Scheduled(cron = "0 0 22 * * ?")
    public void getStatistics() throws ParseException {
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        LocalDateTime start = LocalDateTime.parse(localDate + " " + "00:00:00");
        LocalDateTime end = LocalDateTime.parse(localDate + " " + "23:59:59");
    }
}
