package com.panda.wiki.controller;

import com.panda.wiki.req.EbookQueryReq;
import com.panda.wiki.req.EbookSaveReq;
import com.panda.wiki.resp.CommonResp;
import com.panda.wiki.resp.EbookResp;
import com.panda.wiki.resp.PageResp;
import com.panda.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookQueryReq req) {
        PageResp<EbookResp> list = ebookService.list(req);
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }
}
