package com.moxi.statistics.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.moxi.statistics.dao.StatisticsDao;
import com.moxi.statistics.entity.CallResultDTO;
import com.moxi.statistics.entity.CallResultDTOForAgent;
import com.moxi.statistics.entity.StatisticsEntity;
import com.moxi.statistics.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;


/**
 * @author https://www.chuckfang.top
 * @date Created on 2019/10/22 10:48
 */
@Controller
public class ExcelController {
    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    ExcelService excelService;

    @Autowired
    StatisticsDao statisticsDao;

    private WriteSheet a = EasyExcel.writerSheet(0, "A组").build();
    private WriteSheet b = EasyExcel.writerSheet(1, "B组").build();
    private WriteSheet c = EasyExcel.writerSheet(2, "C组").build();
    private WriteSheet d = EasyExcel.writerSheet(3, "D组").build();
    private WriteSheet e = EasyExcel.writerSheet(4, "强绑定").build();

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            4,
            10,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200),
            new ThreadPoolExecutor.AbortPolicy()
    );

    @GetMapping("/getAI")
    public void downloadForAi(@RequestParam(defaultValue = "29") Integer companyId, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        if (companyId == 87) {
            exportToExcelForXiaoMi(companyId, localDate, "robot", response);
        } else {
            exportToExcel(companyId, localDate, "robot", response);
        }
    }

    @GetMapping("/getAgent")
    public void downloadForAgent(@RequestParam(defaultValue = "29") Integer companyId, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        if (companyId == 87) {
            exportToExcelForXiaoMi(companyId, localDate, "agent", response);
        } else {
            exportToExcel(companyId, localDate, "agent", response);
        }
    }

    @GetMapping("/getStatistics")
    public void downloadStatistics(@RequestParam(defaultValue = "29") Integer companyId, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        if (companyId == 87) {
            exportToExcelForXiaoMi(companyId, localDate, "statistics", response);
        } else {
            exportToExcel(companyId, localDate, "statistics", response);
        }
    }

    @GetMapping("/getAI/{year}/{month}/{day}")
    public void downloadForAi(@RequestParam(defaultValue = "29") Integer companyId, @PathVariable String year,
                         @PathVariable String month, @PathVariable String day, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        LocalDate localDate = LocalDate.parse(year + "-" + month + "-" + day);
        if (companyId == 87) {
            exportToExcelForXiaoMi(companyId, localDate, "robot", response);
        } else {
            exportToExcel(companyId, localDate, "robot", response);
        }
    }

    @GetMapping("/getAgent/{year}/{month}/{day}")
    public void downloadForAgent(@RequestParam(defaultValue = "29") Integer companyId, @PathVariable String year,
                          @PathVariable String month, @PathVariable String day, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        LocalDate localDate = LocalDate.parse(year + "-" + month + "-" + day);
        if (companyId == 87) {
            exportToExcelForXiaoMi(companyId, localDate, "agent", response);
        } else {
            exportToExcel(companyId, localDate, "agent", response);
        }

    }

    @GetMapping("/getStatistics/{year}/{month}/{day}")
    public void downloadStatistics(@RequestParam(defaultValue = "29") Integer companyId, @PathVariable String year,
                          @PathVariable String month, @PathVariable String day, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        LocalDate localDate = LocalDate.parse(year + "-" + month + "-" + day);
        if (companyId == 87) {
            exportToExcelForXiaoMi(companyId, localDate, "statistics", response);
        }else {
            exportToExcel(companyId, localDate, "statistics", response);
        }
    }

    private void setResponse(HttpServletResponse response, LocalDate localDate, String fileNameType) throws UnsupportedEncodingException {
        String fileName = URLEncoder.encode(localDate.toString() + fileNameType, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
    }

    private void exportToExcel(Integer companyId, LocalDate localDate, String type, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        switch (type) {
            case "robot":
                setResponse(response, localDate, "机器人呼叫");
                Future<List<CallResultDTO>> future50 = threadPoolExecutor.submit(() -> excelService.getRobotCallResultList(companyId, 50, localDate));
                Future<List<CallResultDTO>> future60 = threadPoolExecutor.submit(() -> excelService.getRobotCallResultList(companyId, 60, localDate));
                Future<List<CallResultDTO>> future70 = threadPoolExecutor.submit(() -> excelService.getRobotCallResultList(companyId, 70, localDate));
                Future<List<CallResultDTO>> future80 = threadPoolExecutor.submit(() -> excelService.getRobotCallResultList(companyId, 80, localDate));
                Future<List<CallResultDTO>> futureOne = threadPoolExecutor.submit(() -> excelService.getRobotCallResultListForXiaoMi(companyId, localDate));
                long begin = System.currentTimeMillis();
                ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), CallResultDTO.class).build();
                excelWriter.write(future50.get(), a)
                        .write(future60.get(), b)
                        .write(future70.get(), c)
                        .write(future80.get(), d)
                        .write(futureOne.get(), e)
                        .finish();
                logger.info("转换成Excel花费时间：{}ms", System.currentTimeMillis() - begin);
                break;
            case "agent":
                setResponse(response, localDate, "人工呼叫");
                Future<List<CallResultDTOForAgent>> agentsCallResult50 = threadPoolExecutor.submit(() -> excelService.getAgentCallResultList(companyId, 50, localDate));
                Future<List<CallResultDTOForAgent>> agentsCallResult60 = threadPoolExecutor.submit(() -> excelService.getAgentCallResultList(companyId, 60, localDate));
                Future<List<CallResultDTOForAgent>> agentsCallResult70 = threadPoolExecutor.submit(() -> excelService.getAgentCallResultList(companyId, 70, localDate));
                Future<List<CallResultDTOForAgent>> agentsCallResult80 = threadPoolExecutor.submit(() -> excelService.getAgentCallResultList(companyId, 80, localDate));
                Future<List<CallResultDTOForAgent>> agentsCallResultOne = threadPoolExecutor.submit(() -> excelService.getAgentCallResultListForXiaoMi(companyId, localDate));
                long begin1 = System.currentTimeMillis();
                ExcelWriter excelWriter1 = EasyExcel.write(response.getOutputStream(), CallResultDTOForAgent.class).build();
                excelWriter1.write(agentsCallResult50.get(), a)
                        .write(agentsCallResult60.get(), b)
                        .write(agentsCallResult70.get(), c)
                        .write(agentsCallResult80.get(), d)
                        .write(agentsCallResultOne.get(), e)
                        .finish();
                logger.info("转换成Excel花费时间：{}ms", System.currentTimeMillis() - begin1);
                break;
            case "statistics":
                setResponse(response, localDate, "呼叫统计");
                Future<List<StatisticsEntity>> statistics50 = threadPoolExecutor.submit(() -> excelService.getStatistics(companyId, 50, localDate));
                Future<List<StatisticsEntity>> statistics60 = threadPoolExecutor.submit(() -> excelService.getStatistics(companyId, 60, localDate));
                Future<List<StatisticsEntity>> statistics70 = threadPoolExecutor.submit(() -> excelService.getStatistics(companyId, 70, localDate));
                Future<List<StatisticsEntity>> statistics80 = threadPoolExecutor.submit(() -> excelService.getStatistics(companyId, 80, localDate));
                Future<List<StatisticsEntity>> statisticsOne = threadPoolExecutor.submit(() -> excelService.getStatisticsForXiaoMi(companyId, localDate));
                long begin2 = System.currentTimeMillis();
                ExcelWriter excelWriter2 = EasyExcel.write(response.getOutputStream(), StatisticsEntity.class).build();
                excelWriter2.write(statistics50.get(), a)
                        .write(statistics60.get(), b)
                        .write(statistics70.get(), c)
                        .write(statistics80.get(), d)
                        .write(statisticsOne.get(), e)
                        .finish();
                logger.info("转换成Excel花费时间：{}ms", System.currentTimeMillis() - begin2);
                break;
            default:
                break;
        }
    }


    private void exportToExcelForXiaoMi(Integer companyId, LocalDate localDate, String type, HttpServletResponse response) throws IOException {
        switch (type) {
            case "robot":
                setResponse(response, localDate, "机器人呼叫");
                List<CallResultDTO> callResultList = excelService.getRobotCallResultListForXiaoMi(companyId, localDate);
                long begin = System.currentTimeMillis();
                ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), CallResultDTO.class).build();
                excelWriter.write(callResultList, e)
                        .finish();
                logger.info("转换成Excel花费时间：{}ms", System.currentTimeMillis() - begin);
                break;
            case "agent":
                setResponse(response, localDate, "人工呼叫");
                List<CallResultDTOForAgent> agentsCallResult = excelService.getAgentCallResultListForXiaoMi(companyId, localDate);
                long begin1 = System.currentTimeMillis();
                ExcelWriter excelWriter1 = EasyExcel.write(response.getOutputStream(), CallResultDTOForAgent.class).build();
                excelWriter1.write(agentsCallResult, e)
                        .finish();
                logger.info("转换成Excel花费时间：{}ms", System.currentTimeMillis() - begin1);
                break;
            case "statistics":
                setResponse(response, localDate, "呼叫统计");
                List<StatisticsEntity> statistics = excelService.getStatisticsForXiaoMi(companyId, localDate);
                long begin2 = System.currentTimeMillis();
                ExcelWriter excelWriter2 = EasyExcel.write(response.getOutputStream(), StatisticsEntity.class).build();
                excelWriter2.write(statistics, e)
                        .finish();
                logger.info("转换成Excel花费时间：{}ms", System.currentTimeMillis() - begin2);
                break;
            default:
                break;
        }
    }
}