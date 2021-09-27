package com.swordintent.wx.mp.dao.bot;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.swordintent.wx.mp.helper.ExecutorHelper;
import com.swordintent.wx.mp.mapper.ChatInfoMapper;
import com.swordintent.wx.mp.pojo.bot.ChatInfoDo;
import com.swordintent.wx.mp.pojo.bot.ContextMode;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 机器人聊天记录器
 *
 * @author liuhe
 */
@AllArgsConstructor
@Component
public class ContextModeDao {

    protected static Logger logger = LoggerFactory.getLogger(ContextModeDao.class);

    private static Cache<String, String> CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES).maximumSize(100000).build();

    public ContextMode getContextMode(String user) {
        String ifPresent = CACHE.getIfPresent(user);
        if(Objects.isNull(ifPresent)){
            return ContextMode.DEFAULT;
        }
        ContextMode mode = ContextMode.getMode(ifPresent);
        if(Objects.isNull(mode)){
            return ContextMode.DEFAULT;
        }
        return mode;
    }

    public void setContextMode(String user, ContextMode contextMode){
        CACHE.put(user, contextMode.getCmd());
    }
}
