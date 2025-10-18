package com.panda.wiki.job;

import com.panda.wiki.service.DocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class DocJob {

    @Resource
    private DocService docService;

    /*
    每三十秒更新电子书信息
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron()  {
        log.info("电子书信息更新开始");
        long start=System.currentTimeMillis();
        docService.updateEbookInfo();
        log.info("电子书更新结束，耗时:{}毫秒",System.currentTimeMillis()-start);
    }
}
