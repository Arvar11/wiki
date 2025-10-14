package com.panda.wiki.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class PageReq {
    @NotNull(message="【页码】不能为空")
    private int page;

    @NotNull(message = "每页条数不能为空")
    @Max(value = 100,message = "每页条数不超过100")
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
