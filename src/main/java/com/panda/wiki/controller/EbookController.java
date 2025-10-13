package com.panda.wiki.controller;

import com.panda.wiki.req.EbookReq;
import com.panda.wiki.resp.CommonResp;
import com.panda.wiki.resp.EbookResp;
import com.panda.wiki.resp.PageResp;
import com.panda.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping ("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req) {
        PageResp<EbookResp> list = ebookService.list(req);
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        resp.setContent(list);
        return resp;
    }

}
