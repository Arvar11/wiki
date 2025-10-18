package com.panda.wiki.job;

import com.panda.wiki.service.DocService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DocJob {

    @Resource
    private DocService docService;

    /*
    每三十秒更新电子书信息
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron()  {
        docService.updateEbookInfo();
    }
}
