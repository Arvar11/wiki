package com.panda.wiki.job;

import com.panda.wiki.service.DocService;
import com.panda.wiki.service.EbookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class EbookSnapShotJob {
    @Resource
    private EbookService  ebookService;

    /*
    每三十秒更新电子书信息
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron()  {
        log.info("电子书快照更新开始");
        long start=System.currentTimeMillis();
        ebookService.genSnapShot();
        log.info("电子书快照结束，耗时:{}毫秒",System.currentTimeMillis()-start);
    }
}
