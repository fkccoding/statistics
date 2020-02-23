package com.moxi.statistics;

import com.moxi.statistics.dao.StatisticsDao;
import com.moxi.statistics.entity.StatisticsEntity;
import com.moxi.statistics.service.ExcelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest
class StatisticsApplicationTests {
    @Autowired
    ExcelService excelService;

    @Autowired
    StatisticsDao statisticsDao;

    @Test
    void contextLoads() {
    }

    @Test
    void test1() {
    }

}
