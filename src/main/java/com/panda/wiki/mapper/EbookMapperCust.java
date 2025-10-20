package com.panda.wiki.mapper;

import com.panda.wiki.resp.StatisticResp;

import java.util.List;

public interface EbookMapperCust {

    public  void genSnapShot();


    List<StatisticResp> getStatistic();
}
