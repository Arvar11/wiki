package com.panda.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.panda.wiki.domain.Category;
import com.panda.wiki.domain.CategoryExample;
import com.panda.wiki.mapper.CategoryMapper;
import com.panda.wiki.req.CategoryQueryReq;
import com.panda.wiki.req.CategorySaveReq;
import com.panda.wiki.resp.CategoryResp;
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
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    private SnowFlake snowFlake;  // 注入雪花算法

    public PageResp<CategoryResp> list(CategoryQueryReq req) {
// 创建一个电子书查询条件对象（用于构建SQL查询条件）
        CategoryExample categoryExample = new CategoryExample();

// 创建查询条件构造器（Criteria是MyBatis生成的查询条件构建器）
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

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
// categoryMapper.selectByExample() 会根据上面设置的条件生成SQL并执行
        List<Category> categorylist = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categorylist);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());


//        List<CategoryResp> respList =new ArrayList<>();
//        for(Category category:categorylist)
//        {
//            CategoryResp categoryResp = new CategoryResp();
//            BeanUtils.copyProperties(category,categoryResp);
//            respList.add(categoryResp);
//        }
        List<CategoryResp> respList= CopyUtil.copyList(categorylist,CategoryResp.class);
        PageResp<CategoryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return  pageResp;
    }

    public void save(CategorySaveReq req) {
        Category category=CopyUtil.copy(req, Category.class);
        if (req.getId() == null) {
            // 新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
