package com.autobon.platform.schedule;

import com.autobon.order.entity.Bill;
import com.autobon.order.service.BillService;
import com.autobon.order.service.ConstructionService;
import com.autobon.technician.entity.Technician;
import com.autobon.technician.service.TechnicianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 技师每月帐单任务计划
 * Created by dave on 16/3/11.
 */
@Component
public class BillTask {
    private static final Logger log = LoggerFactory.getLogger(BillTask.class);

    @Autowired TechnicianService technicianService;
    @Autowired ConstructionService constructionService;
    @Autowired BillService billService;

    // 每月1号凌晨2点执行月度帐单结算
    @Async
    @Scheduled(cron = "0 0 2 1 * ?")
    public void calculateBills() {
        log.info("月账清算任务开始");
        Date from = Date.from(LocalDate.now().minusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        int totalPages = 0;
        int pageNo = 1;
        long billCount = 0;
        do {
            Page<Technician> page = technicianService.findActivedFrom(from, pageNo++, 20);
            totalPages = page.getTotalPages();
            billCount = page.getTotalElements();
            // 创建月度帐单,查询起止日期间内的施工单
            for (Technician t : page.getContent()) {
                float pay = constructionService.sumPayment(t.getId(), from, to);
                Bill bill = new Bill(t.getId(), to);
                bill.setSum(pay);
                bill.setCount(constructionService.settlePayment(t.getId(), from, to));
                billService.save(bill);
            }
        } while (pageNo < totalPages);
        log.info("月账清算任务完成, 共" + billCount + "条");
    }
}
