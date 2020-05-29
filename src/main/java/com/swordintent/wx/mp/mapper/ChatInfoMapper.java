package com.swordintent.wx.mp.mapper;

import com.swordintent.wx.mp.pojo.bot.ChatInfoDo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatInfoMapper {

    @Insert("insert into chat_info (openid, req_content, resp_content, extral, user_context) values (#{openid},#{reqContent},#{respContent},#{extral},#{userContext})")
    void insert(ChatInfoDo chatInfoDo);
}
