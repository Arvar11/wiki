package com.panda.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.panda.wiki.domain.Doc;
import com.panda.wiki.domain.DocExample;
import com.panda.wiki.mapper.DocMapper;
import com.panda.wiki.req.DocQueryReq;
import com.panda.wiki.req.DocSaveReq;
import com.panda.wiki.resp.DocResp;
import com.panda.wiki.resp.PageResp;
import com.panda.wiki.util.CopyUtil;
import com.panda.wiki.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class DocService {

    @Resource
    private DocMapper docMapper;

    @Autowired
    private SnowFlake snowFlake;  // 注入雪花算法

    public PageResp<DocResp> list(DocQueryReq req) {
// 创建一个电子书查询条件对象（用于构建SQL查询条件）
        DocExample docExample = new DocExample();

        docExample.setOrderByClause("sort asc");
// 创建查询条件构造器（Criteria是MyBatis生成的查询条件构建器）
        DocExample.Criteria criteria = docExample.createCriteria();

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
// docMapper.selectByExample() 会根据上面设置的条件生成SQL并执行
        List<Doc> doclist = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(doclist);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());


//        List<DocResp> respList =new ArrayList<>();
//        for(Doc doc:doclist)
//        {
//            DocResp docResp = new DocResp();
//            BeanUtils.copyProperties(doc,docResp);
//            respList.add(docResp);
//        }
        List<DocResp> respList= CopyUtil.copyList(doclist,DocResp.class);
        PageResp<DocResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return  pageResp;
    }

    public List<DocResp> all() {
         // 创建一个电子书查询条件对象（用于构建SQL查询条件）
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> doclist = docMapper.selectByExample(docExample);


        List<DocResp> list= CopyUtil.copyList(doclist,DocResp.class);
        return  list;
    }

    public void save(DocSaveReq req) {
        Doc doc=CopyUtil.copy(req, Doc.class);
        if (req.getId() == null) {
            // 新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        } else {
            // 更新
            docMapper.updateByPrimaryKeySelective(doc);
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }
}
