package com.panda.wiki.controller;

import com.panda.wiki.req.CategoryQueryReq;
import com.panda.wiki.req.CategorySaveReq;
import com.panda.wiki.resp.CommonResp;
import com.panda.wiki.resp.CategoryResp;
import com.panda.wiki.resp.PageResp;
import com.panda.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp list(CategoryQueryReq req) {
        PageResp<CategoryResp> list = categoryService.list(req);
        CommonResp<PageResp<CategoryResp>> resp = new CommonResp<>();
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody CategorySaveReq req) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id)
    {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}
