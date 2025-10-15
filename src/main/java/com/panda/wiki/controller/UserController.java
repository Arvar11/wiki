package com.panda.wiki.controller;

import com.panda.wiki.req.UserLoginReq;
import com.panda.wiki.req.UserQueryReq;
import com.panda.wiki.req.UserResetPasswordReq;
import com.panda.wiki.req.UserSaveReq;
import com.panda.wiki.resp.CommonResp;
import com.panda.wiki.resp.UserLoginResp;
import com.panda.wiki.resp.UserResp;
import com.panda.wiki.resp.PageResp;
import com.panda.wiki.service.UserService;
import com.panda.wiki.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping ("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SnowFlake snowFlake;

    @GetMapping("/list")
    public CommonResp list( @Valid  UserQueryReq req) {
        log.info(req.toString());
        PageResp<UserResp> list = userService.list(req);
        CommonResp<PageResp<UserResp>> resp = new CommonResp<>();
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id)
    {
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

    @PostMapping("/reset-password")
    public CommonResp resetPassword(@Valid @RequestBody UserResetPasswordReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.resetPassword(req);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp<UserLoginResp> login(@RequestBody@Valid UserLoginReq userLoginReq){
        userLoginReq.setPassword(DigestUtils.md5DigestAsHex(userLoginReq.getPassword().getBytes()));
        UserLoginResp userLoginResp=userService.login(userLoginReq);
        Long token=snowFlake.nextId();
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token, userLoginResp, 3600, TimeUnit.SECONDS);
        CommonResp resp = new CommonResp<>();
        resp.setContent(userLoginResp);
        return resp;
    }
}
