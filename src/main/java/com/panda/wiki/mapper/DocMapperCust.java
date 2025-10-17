package com.panda.wiki.mapper;

import org.apache.ibatis.annotations.Param;

public interface DocMapperCust {
    public void increaseViewCount(@Param("id") long id) ;
    public void increaseVoteCount(@Param("id") long id) ;

}
