package com.panda.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.panda.wiki.domain.Ebook;
import com.panda.wiki.domain.EbookExample;
import com.panda.wiki.mapper.EbookMapper;
import com.panda.wiki.req.EbookReq;
import com.panda.wiki.resp.EbookResp;
import com.panda.wiki.resp.PageResp;
import com.panda.wiki.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;  // 确保这个导入存在

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.springframework.boot.Banner.Mode.LOG;

@Slf4j
@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public PageResp<EbookResp> list(EbookReq req) {
// 创建一个电子书查询条件对象（用于构建SQL查询条件）
        EbookExample ebookExample = new EbookExample();

// 创建查询条件构造器（Criteria是MyBatis生成的查询条件构建器）
        EbookExample.Criteria criteria = ebookExample.createCriteria();

// 要根据名称模糊查询
// !req.getName().trim().isEmpty() 的含义："去除首尾空白后的姓名不为空"
// 这个条件判断确保：
// 1. 请求中的名称字段不为null
// 2. 名称去掉前后空格后不是空字符串
// 3. 避免用户只输入空格时进行无意义的搜索
        if (req.getName() != null && !req.getName().trim().isEmpty()) {
            // 添加名称模糊查询条件：name LIKE '%关键词%'
            // 例如：如果用户搜索"Java"，SQL条件就是 name LIKE '%Java%'
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize()); // 查询第1页，每页3条数据
// 执行查询并返回结果列表
// ebookMapper.selectByExample() 会根据上面设置的条件生成SQL并执行
        List<Ebook> ebooklist = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooklist);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());


//        List<EbookResp> respList =new ArrayList<>();
//        for(Ebook ebook:ebooklist)
//        {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);
//            respList.add(ebookResp);
//        }
        List<EbookResp> respList= CopyUtil.copyList(ebooklist,EbookResp.class);
        PageResp<EbookResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return  pageResp;
    }
}
