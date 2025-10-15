package com.panda.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.panda.wiki.domain.User;
import com.panda.wiki.domain.UserExample;
import com.panda.wiki.exception.BusinessException;
import com.panda.wiki.exception.BusinessExceptionCode;
import com.panda.wiki.mapper.UserMapper;
import com.panda.wiki.req.UserQueryReq;
import com.panda.wiki.req.UserSaveReq;
import com.panda.wiki.resp.UserResp;
import com.panda.wiki.resp.PageResp;
import com.panda.wiki.util.CopyUtil;
import com.panda.wiki.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private SnowFlake snowFlake;  // 注入雪花算法

    public PageResp<UserResp> list(UserQueryReq req) {
        log.info(req.toString());
// 创建一个电子书查询条件对象（用于构建SQL查询条件）
        UserExample userExample = new UserExample();

// 创建查询条件构造器（Criteria是MyBatis生成的查询条件构建器）
        UserExample.Criteria criteria = userExample.createCriteria();

// 要根据名称模糊查询
// !req.getName().trim().isEmpty() 的含义："去除首尾空白后的姓名不为空"
// 这个条件判断确保：
// 1. 请求中的名称字段不为null
// 2. 名称去掉前后空格后不是空字符串
// 3. 避免用户只输入空格时进行无意义的搜索
        if (req.getLoginName() != null ) {
            // 添加名称模糊查询条件：name LIKE '%关键词%'
            // 例如：如果用户搜索"Java"，SQL条件就是 name LIKE '%Java%'
            criteria.andLoginNameEqualTo(req.getLoginName());
        }



        PageHelper.startPage(req.getPage(), req.getSize()); // 查询第1页，每页3条数据
// 执行查询并返回结果列表
// userMapper.selectByExample() 会根据上面设置的条件生成SQL并执行
        List<User> userlist = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userlist);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());


//        List<UserResp> respList =new ArrayList<>();
//        for(User user:userlist)
//        {
//            UserResp userResp = new UserResp();
//            BeanUtils.copyProperties(user,userResp);
//            respList.add(userResp);
//        }
        List<UserResp> respList= CopyUtil.copyList(userlist,UserResp.class);
        PageResp<UserResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return  pageResp;
    }

    public void save(UserSaveReq req) {
        User user=CopyUtil.copy(req, User.class);
        if (req.getId() == null) {
            User userDB=selectByLoginName(user.getLoginName());
            if(ObjectUtils.isEmpty(userDB)){
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            }else {
                //登录名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            //因为我们要求不可修改登录名
            // 更新
            //将用户的登录名更新为null，同时也会更新user对象中其他非null的字段
            user.setLoginName( null);
            user.setPassword( null);
            userMapper.updateByPrimaryKeySelective(user);
            //updateByPrimaryKeySelective方法会根据主键更新对象，但只更新非null的字段
        }
    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String loginName){
        UserExample userExample = new UserExample();

        // 创建查询条件构造器（Criteria是MyBatis生成的查询条件构建器）
        UserExample.Criteria criteria = userExample.createCriteria();


            // 例如：如果用户搜索"Java"，SQL条件就是 name LIKE '%Java%'
            criteria.andLoginNameEqualTo(loginName);

        List<User> userList =  userMapper.selectByExample(userExample);
        if(userList.isEmpty())
        {
            return null;
        }else{
            return userList.get(0);
        }

    }
}
