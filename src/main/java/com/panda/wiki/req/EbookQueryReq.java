package com.panda.wiki.req;

public class EbookQueryReq extends PageReq{
    private Long id;

    private String name;

    public Long getCategory2_id() {
        return category2_id;
    }

    public void setCategory2_id(Long category2_id) {
        this.category2_id = category2_id;
    }

    @Override
    public String toString() {
        return "EbookQueryReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category2=" + category2_id +
                '}';
    }

    private Long category2_id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}