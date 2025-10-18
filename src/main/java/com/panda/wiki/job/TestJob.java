//package com.panda.wiki.job;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * 定时任务测试类
// * 演示两种定时任务执行方式
// */
//@Component
//public class TestJob {
//
//    private static final Logger LOG = LoggerFactory.getLogger(TestJob.class);
//
//    /**
//     * fixedRate方式 - 固定频率执行
//     * 特点：无论任务执行时间长短，都会按固定间隔执行
//     * 当前配置：每1秒执行一次，但任务本身执行需要2秒
//     * 结果：任务会重叠执行（上一次没执行完，下一次又开始）
//     */
//    @Scheduled(fixedRate = 1000)
//    public void simple() throws InterruptedException {
//        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
//        String dateString = formatter.format(new Date());
//        Thread.sleep(2000);  // 模拟耗时操作（2秒）
//        LOG.info("fixedRate任务执行：{}", dateString);
//    }
//
//    /**
//     * cron表达式方式 - 按cron规则执行
//     * 特点：等待上一次执行完成，才会开始下一次执行
//     * 当前配置：每2秒执行一次
//     * 结果：即使任务执行时间超过2秒，也不会重叠执行
//     */
//    @Scheduled(cron = "*/2 * * * * ?")
//    public void cron() throws InterruptedException {
//        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
//        String dateString = formatter.format(new Date());
//        // Thread.sleep(1500);  // 注释掉的模拟耗时操作
//        LOG.info("cron任务执行：{}", dateString);
//    }
//}