package com.panda.wiki.req;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DocSaveReq {
    private Long id;

    @NotNull(message = "电子书不能为空")
    private Long ebookId;

    @NotNull(message = "父文档不能为空")
    private Long parent;

    @NotNull(message = "文档名不能为空")
    private String name;

    @NotNull(message = "排序不能为空")
    private Integer sort;

    public @NotNull(message = "内容不能为空") String getContent() {
        return content;
    }

    public void setContent(@NotNull(message = "内容不能为空") String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DocSaveReq{" +
                "id=" + id +
                ", ebookId=" + ebookId +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", content='" + content + '\'' +
                ", viewCount=" + viewCount +
                ", voteCount=" + voteCount +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", deleted=" + deleted +
                '}';
    }

    @NotNull(message = "内容不能为空")
    private String content;

    private Integer viewCount;

    private Integer voteCount;

    private Date createdTime;

    private Date updatedTime;

    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEbookId() {
        return ebookId;
    }

    public void setEbookId(Long ebookId) {
        this.ebookId = ebookId;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}