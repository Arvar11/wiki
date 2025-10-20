package com.panda.wiki.service;

import com.panda.wiki.mapper.EbookSnapshotMapper;
import com.panda.wiki.resp.StatisticResp;
import com.panda.wiki.mapper.EbookMapper;
import com.panda.wiki.mapper.EbookMapperCust;
import com.panda.wiki.resp.StatisticResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookSnapshotService {

    @Autowired
    private EbookMapperCust ebookSnapshotMapper;

    /**
     * 获取统计信息
     */
    public List<StatisticResp> getStatistic() {
        return ebookSnapshotMapper.getStatistic();
    }


}