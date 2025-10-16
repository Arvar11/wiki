package com.panda.wiki.controller;

import com.panda.wiki.req.DocQueryReq;
import com.panda.wiki.req.DocSaveReq;
import com.panda.wiki.resp.DocResp;
import com.panda.wiki.resp.CommonResp;
import com.panda.wiki.resp.PageResp;
import com.panda.wiki.service.DocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Slf4j
@RestController
@RequestMapping ("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/list")
    public CommonResp list(DocQueryReq req) {
        log.info("req:{}",req);
        PageResp<DocResp> list = docService.list(req);
        CommonResp<PageResp<DocResp>> resp = new CommonResp<>();
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody DocSaveReq req) {
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id)
    {
        CommonResp resp = new CommonResp<>();
        docService.delete(id);
        return resp;
    }

    @GetMapping ("/all")
    public CommonResp all( )
    {
        CommonResp<List<DocResp>> resp = new CommonResp<>();
        List<DocResp> list = docService.all( );
        resp.setContent(list);
        return resp;
    }
}
